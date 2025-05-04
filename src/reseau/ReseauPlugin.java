package reseau;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import classes.Place;
import classes.Transition;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ConnectionException;
import interfaces.ReseauCI;
import interfaces.ReseauPlaceCommuneCI;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import interfaces.ReseauI;

@RequiredInterfaces(required = { ReseauPlaceCommuneCI.class})
public class ReseauPlugin<P>
extends 	AbstractPlugin
implements ReseauI<P>{
	private static final long serialVersionUID = 1L;

	protected ReseauInboundPortForPlugin<P>	rip;

	protected Map<String, P> 		places;
	protected Map<String, Transition> 	transitions;
	protected String 					uri;
	
	private ReseauPlaceCommuneEndpoint endPointClient;
	private ReseauEndpoint endPointServer;
	private Transition oldTransition; 

	public				ReseauPlugin(String uri) throws Exception
	{
		super();
		this.uri = uri;
	}
	
	public				ReseauPlugin(String uri,
			ReseauEndpoint endPointServer,
			ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		super();
		this.uri = uri;

		this.endPointServer = endPointServer;
		this.endPointClient = endPointClient;
	}


	@Override
	public void			installOn(ComponentI owner) throws Exception
	{
		super.installOn(owner);
		

		// Add the interface
		//this.addOfferedInterface(ReseauCI.class);
		//this.addRequiredInterface(ReseauCI.class);

		this.addRequiredInterface(ReseauPlaceCommuneCI.class);
	}

	@Override
	public void			initialise() throws Exception
	{
		super.initialise();
		endPointServer.initialiseServerSide(this.getOwner());
		
		if(!this.endPointClient.clientSideInitialised()) {
			try {
				this.endPointClient.initialiseClientSide(this.getOwner());
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}

		this.places = new HashMap<>();
		this.transitions = new HashMap<>();

		// Create the inbound port
		this.rip = new ReseauInboundPortForPlugin<P>(this.getOwner(),
													this.getPluginURI());
		this.rip.publishPort();
	}


	@Override
	public void			finalise() throws Exception
	{
		this.places.clear();
		this.places = null;
		this.transitions.clear();
		this.transitions = null;
		super.finalise();
	}


	@Override
	public void			uninstall() throws Exception
	{
		this.rip.unpublishPort();
		this.rip.destroyPort();					// optional
		this.removeOfferedInterface(ReseauCI.class);
	}


	@Override
	public String getUri() throws Exception {
		return this.uri;
	}


	@Override
	public Collection<P> getPlaces() {
        return places.values();
    }



	@Override
	public Collection<Transition> getTransitions() throws Exception {
		return transitions.values();
	}


	@Override
	public void addPlace(P place) throws Exception {
		this.places.put(((Place) place).getUri(), place);		
	}


	@Override
	public void addTransition(Transition transition) throws Exception {
		this.transitions.put(transition.getUri(), transition);
		
	}

	@Override
	public Set<Transition> update() throws Exception {
	    Set<Transition> transitionsPossibles = new HashSet<>();

	    for (Transition transition : this.transitions.values()) {
	        boolean appendTrans = (oldTransition != transition);

	        for (String place : transition.getPlacesEntrees().keySet()) {
	            
	            if (((Place) this.places.get(place)).getNbJeton() == 0) {
	            	appendTrans = false;
	            }
	        }
	        if (appendTrans && transition.isActivable())
	            transitionsPossibles.add(transition);
	    }

	    return transitionsPossibles;
	}


	@Override
	public void showReseau() throws Exception {
		StringBuilder sb = new StringBuilder();
        sb.append("Réseau ").append(uri).append("(");

        Iterator<P> it = places.values().iterator();
        while (it.hasNext()) {
            P p = it.next();
            sb.append(((Place) p).getNbJeton());
            if (it.hasNext()) sb.append(", ");
        }

        sb.append(")");
        System.out.println(sb.toString());		
	}

	@Override
	public void randomTransition() throws Exception {
		Random random = new Random();
        while(true) {
        	StringBuilder sb = new StringBuilder();
            Set<Transition> transitionsPossibles = update();
            sb.append("Transitions possible : ");
            String message = new String();
            
            for (Transition t : transitionsPossibles) {
            	message = String.format("%s,", t.getUri());
            	sb.append(message);
            }
            
            sb.append("\n");

            if (transitionsPossibles.isEmpty()) {
            	sb.append("Aucune transition possible.\n");
                try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                //System.out.println(sb.toString());
            } else {
            	List<Transition> listeTransitions = new ArrayList<>(transitionsPossibles);

            	Transition transitionChoisie = listeTransitions.get(random.nextInt(listeTransitions.size()));

                message = String.format("Transition choisie : %s\n", transitionChoisie.getUri());
                sb.append(message).append("\n");
                //System.out.println(sb.toString());

                activeTransition(transitionChoisie);
                showReseau();
            }
        }
	}

	@Override
	public void manualTransition(Scanner scanner) throws Exception {
		Set<Transition> transitionsPossibles = update();
        if (transitionsPossibles.isEmpty()) {
            System.out.println("Aucune transition possible.");
            return;
        }

        System.out.println("Transitions possibles :");
        List<Transition> listeTransitions = new ArrayList<>(transitionsPossibles);

        for (int i = 0; i < listeTransitions.size(); i++)
        	System.out.printf("%s,\n", listeTransitions.get(i).getUri());
        System.out.print("Choisissez une transition : ");
        int choix = scanner.nextInt();

        if (choix < 1 || choix > listeTransitions.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Transition transitionChoisie = listeTransitions.get(choix - 1);
        activeTransition(transitionChoisie);
        showReseau();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void activeTransition(Transition tr) throws Exception {

		if(tr.isActivable()) {
			
    		boolean notSkip = true;
    		//boolean hasPlaceCommune = false;
    		
    		for (String placeCommune : tr.getPlacesCommuneEntrees().keySet()) {
    			notSkip = endPointClient.getClientSideReference().tryAcquireJeton(placeCommune);
    			if (!notSkip) {
    				break;
    			}
	        }
    		
    		if(notSkip) {
    			//System.out.println("Not Skip - " + tr.getUri());
				for (String placeCommune : tr.getPlacesCommuneEntrees().keySet()) {
					//hasPlaceCommune = true;
					endPointClient.getClientSideReference().retrieveJeton(placeCommune);
					endPointClient.getClientSideReference().releaseAvailability();
					endPointClient.getClientSideReference().releaseJeton(placeCommune);
		        }
				
		        for (String place : tr.getPlacesEntrees().keySet())
		        	((Place) places.get(place)).retrieveJeton();
		        
		        for (String place : tr.getPlacesSorties()) {
		        	((Place) places.get(place)).addJeton();}
		        
		        for (String placeCommune : tr.getPlacesCommuneSorties()) {
					//hasPlaceCommune = true;
		        	endPointClient.getClientSideReference().addJeton(placeCommune);
		        	endPointClient.getClientSideReference().releaseAvailability();
	        	}
		        
		        
		        tr.getActivableFunction().apply(tr.getUri());
		        oldTransition = tr;
		        
		        /*if(hasPlaceCommune) {
		        	System.out.println("----------------------------hasPlaceCommune");
			        endPointClient.getClientSideReference().acquireUpdate();
		        }*/
    		} else {
    			//System.out.println("La transition a été prise par un autre thread " + Thread.currentThread().getName());
    		}
    	}
	}

	@Override
	public void linkEntreePlaceCommuneTransition(String transition, String placeCommune, int seuil, String updatingAvailability,
			String updatingJetons) throws Exception {
		this.transitions.get(transition).addPlaceCommuneEntree(placeCommune, seuil, updatingAvailability, updatingJetons);
	}

	@Override
	public void linkSortiePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
			String updatingJetons) throws Exception {
		this.transitions.get(transition).addPlaceCommuneSortie(placeCommune, updatingAvailability);
	}

	@Override
	public void updateTransitionsActivable(String uri, ArrayList<String> transSorties, int nbJeton)
			throws Exception {
		for(String t : transSorties) {
			if(this.transitions.containsKey(t)) {
				this.transitions.get(t).updateIsActivable(uri, nbJeton);
			}
		}
	}
	
}
