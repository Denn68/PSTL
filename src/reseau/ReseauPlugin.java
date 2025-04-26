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
import java.util.concurrent.atomic.AtomicBoolean;

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
	
	//private String semaphorePluginAjoutInboundPortURI;
	//private String semaphorePluginRetraitInboundPortURI;
	private ReseauPlaceCommuneEndpoint endPointClient;
	private ReseauEndpoint endPointServer;

	public				ReseauPlugin(String uri) throws Exception
	{
		super();
		this.uri = uri;
	}
	
	public				ReseauPlugin(String uri,
			//String semaphorePluginAjoutInboundPortURI,
			//String semaphorePluginRetraitInboundPortURI,
			ReseauEndpoint endPointServer,
			ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		super();
		this.uri = uri;
		//this.semaphorePluginAjoutInboundPortURI = semaphorePluginAjoutInboundPortURI;
		//this.semaphorePluginRetraitInboundPortURI = semaphorePluginRetraitInboundPortURI;

		//endPointServer.initialiseServerSide(this.getOwner());
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


	@SuppressWarnings("rawtypes")
	@Override
	public Set<Transition> update() throws Exception {
	    Set<Transition> transitionsPossibles = new HashSet<>();

	    for (Transition transition : this.transitions.values()) {
	        AtomicBoolean appendTrans = new AtomicBoolean(true);

	        for (String place : transition.getPlacesEntrees()) {
	            if (transition.getUri().equals("t8")) {
	                System.out.println("ETAT T8 " + transition.isActivable());
	            }

	            this.places.forEach((key, value) -> {
	                if (key.equals(place)) {
	                    if (((Place) value).getNbJeton() == 0) {
	                        appendTrans.set(false);
	                    }
	                }
	            });
	        }

	        if (appendTrans.get() && transition.isActivable())
	            transitionsPossibles.add(transition);
	    }

	    return transitionsPossibles;
	}



	@SuppressWarnings("rawtypes")
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
            	sb.append("Aucune transition possible.");
                try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            } else {
            	List<Transition> listeTransitions = new ArrayList<>(transitionsPossibles);

            	Transition transitionChoisie = listeTransitions.get(random.nextInt(listeTransitions.size()));

                message = String.format("Transition choisie : %s\n", transitionChoisie.getUri());
                sb.append(message);

                activeTransition(transitionChoisie);
            }
            
            sb.append("\n");
            
            System.out.println(sb.toString());
            
            showReseau();
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

	@Override
	public void activeTransition(Transition tr) throws Exception {
		if(tr.isActivable()) {
    		boolean skip = false;
    		for (String placeCommune : tr.getPlacesCommuneEntrees()) {
				// skip = this.updatingJetons.get(placeCommune).tryAcquire();
    			// => tryAcquire dans le composant Semaphore updatingJeton avec l'URI placeCommune
    			// transition -> uriPlacesCommunes -> ReseauPlaceCommune-> Semaphore correspondante
	        }
    		if(!skip) {
    			System.out.printf("Not Skip - %s\n", uri);
				for (String placeCommune : tr.getPlacesCommuneEntrees()) {
					// placeCommune.retrieveJeton(uri); => retrieveJeton dans le composant ReseauPlaceCommune
					
					// this.updatingAvailability.get(placeCommune).release();
					// => release dans le composant Semaphore updatingAvailability avec l'URI placeCommune
	    			// transition -> uriPlacesCommunes -> ReseauPlaceCommune-> Semaphore correspondante
					
					// this.updatingJetons.get(placeCommune).release();
	    			// => release dans le composant Semaphore updatingJeton avec l'URI placeCommune
	    			// transition -> uriPlacesCommunes -> ReseauPlaceCommune-> Semaphore correspondante
		        }
				
		        for (String place : tr.getPlacesEntrees())
		        	((Place) places.get(place)).retrieveJeton();
		        
		        for (String place : tr.getPlacesSorties())
		        	((Place) places.get(place)).addJeton();
		        
		        for (String placeCommune : tr.getPlacesCommuneSorties()) {
		        	// placeCommune.addJeton(); => addJeton dans le composant ReseauPlaceCommune
		        	// this.updatingAvailability.get(placeCommune).release();
					// => release dans le composant Semaphore updatingAvailability avec l'URI placeCommune
	    			// transition -> uriPlacesCommunes -> ReseauPlaceCommune-> Semaphore correspondante
	        	}
		        
		        
		        tr.getActivableFunction().apply(null);
    		} else {
    			System.out.println("La transition a été prise par un autre thread");
    		}
    	}
	}

	@Override
	public void linkEntreePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
			String updatingJetons) throws Exception {
		this.transitions.get(transition).addPlaceCommuneEntree(placeCommune, updatingAvailability, updatingJetons);
	}

	@Override
	public void linkSortiePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
			String updatingJetons) throws Exception {
		this.transitions.get(transition).addPlaceCommuneSortie(placeCommune, updatingAvailability);
	}
	
}
