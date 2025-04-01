package reseau;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import classes.Place;
import classes.PlaceCommune;
import classes.Transition;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ConnectionException;
import interfaces.ReseauCI;
import interfaces.ReseauPlaceCommuneCI;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import interfaces.ReseauI;

public class ReseauPlugin<P>
extends 	AbstractPlugin
implements ReseauI<P>{
	private static final long serialVersionUID = 1L;

	protected ReseauInboundPortForPlugin<P>	rip;

	protected ArrayList<P> 				places;
	protected ArrayList<Transition> 	transitions;
	protected String 					uri;
	
	private String semaphorePluginAjoutInboundPortURI;
	private String semaphorePluginRetraitInboundPortURI;
	private ReseauPlaceCommuneEndpoint endPointClient;

	public				ReseauPlugin(String uri) throws Exception
	{
		super();
		this.uri = uri;
	}
	
	public				ReseauPlugin(String uri,
			String semaphorePluginAjoutInboundPortURI,
			String semaphorePluginRetraitInboundPortURI,
			ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		super();
		System.out.println(endPointClient.getClass());
		this.uri = uri;
		this.semaphorePluginAjoutInboundPortURI = semaphorePluginAjoutInboundPortURI;
		this.semaphorePluginRetraitInboundPortURI = semaphorePluginRetraitInboundPortURI;
		this.endPointClient = endPointClient;
	}


	@Override
	public void			installOn(ComponentI owner) throws Exception
	{
		super.installOn(owner);

		// Add the interface
		this.addOfferedInterface(ReseauCI.class);
	}

	@Override
	public void			initialise() throws Exception
	{
		super.initialise();
		
		if(!endPointClient.clientSideInitialised()) {
			try {
				endPointClient.initialiseClientSide(this);
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}

		this.places = new ArrayList<>();
		this.transitions = new ArrayList<>();

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
	public ArrayList<P> getPlaces() throws Exception {
		return places;
	}


	@Override
	public ArrayList<Transition> getTransitions() throws Exception {
		return transitions;
	}


	@Override
	public void addPlace(P place) throws Exception {
		this.places.add(place);		
	}


	@Override
	public void addTransition(Transition transition) throws Exception {
		this.transitions.add(transition);
		
	}


	@SuppressWarnings("rawtypes")
	@Override
	public Set<Transition> update() throws Exception {
		Set<Transition> transitionsPossibles = new HashSet<>();
        
        for (Transition transition: this.transitions) {
        	boolean appendTrans = true;
        	for(Place p: transition.getPlacesEntrees()) {
        		if(transition.getUri() == "t8") {
        			System.out.println("ETAT T8" + transition.isActivable());
        		}
        		if(p.getNbJeton() == 0) {
        			appendTrans = false;
        		}
        	}
        		
    		if (appendTrans && transition.isActivable())
                transitionsPossibles.add(transition);
        }
        
        return transitionsPossibles;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public void showReseau() throws Exception {
		int i = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Réseau ").append(uri).append("(");
        for (P p : places) {
            if (i != (places.size() - 1)) {
            	sb.append(((Place) p).getNbJeton()).append(", ");
            } else {
            	sb.append(((Place) p).getNbJeton());
            }
            i++;
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

                transitionChoisie.activateTransition();
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
        transitionChoisie.activateTransition();
        showReseau();		
	}

	@Override
	public void linkPlacesTransition(ArrayList<P> entrees, String t, ArrayList<P> sorties) throws Exception {
		
		
		for(Transition tr : transitions) {
			if(tr.getUri().equals(t)) {
				tr.addPlacesCommuneEntree((ArrayList<PlaceCommune>) entrees);
				tr.addPlacesCommuneSortie((ArrayList<PlaceCommune>) sorties);
				for(P p : entrees) {
					((PlaceCommune) p).addTransSortie(tr);
				}
				for(P p : sorties) {
					((PlaceCommune) p).addTransEntree(tr);
				}
			}
		}
	}
}
