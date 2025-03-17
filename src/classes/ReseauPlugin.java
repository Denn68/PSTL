package classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import interfaces.ReseauCI;
import interfaces.ReseauI;

public class ReseauPlugin<I, R, Place>
extends 	AbstractPlugin
implements ReseauI<I, R, Place>{
	private static final long serialVersionUID = 1L;

	protected ReseauInboundPortForPlugin<I, R, Place>	rip;

	protected ArrayList<Place> 				places;
	protected ArrayList<Transition<I, R>> 	transitions;
	protected String 						uri;

	public				ReseauPlugin(String uri)
	{
		super();
		this.uri = uri;
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

		this.places = new ArrayList<>();
		this.transitions = new ArrayList<>();

		// Create the inbound port
		this.rip = new ReseauInboundPortForPlugin<I, R, Place>(this.getOwner(),
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
	public ArrayList<Place> getPlaces() throws Exception {
		return places;
	}


	@Override
	public ArrayList<Transition<I, R>> getTransitions() throws Exception {
		return transitions;
	}


	@Override
	public void addPlace(Place place) throws Exception {
		this.places.add(place);		
	}


	@Override
	public void addTransition(Transition<I, R> transition) throws Exception {
		this.transitions.add(transition);
		
	}


	@Override
	public Set<Transition<I, R>> update() throws Exception {
Set<Transition<I, R>> transitionsPossibles = new HashSet<>();
        
        for (Transition<I, R> transition: this.transitions) {
        	boolean appendTrans = true;
        	for(classes.Place p: transition.getPlacesEntrees()) {
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


	@Override
	public void showReseau() throws Exception {
		int i = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Réseau ").append(uri).append("(");
        for (Place p : places) {
            if (i != (places.size() - 1)) {
            	sb.append(((classes.Place) p).getNbJeton()).append(", ");
            } else {
            	sb.append(((classes.Place) p).getNbJeton());
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
            Set<Transition<I, R>> transitionsPossibles = update();
            sb.append("Transitions possible : ");
            String message = new String();
            
            for (Transition<I, R> t : transitionsPossibles) {
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
            	List<Transition<I, R>> listeTransitions = new ArrayList<>(transitionsPossibles);

            	Transition<I, R> transitionChoisie = listeTransitions.get(random.nextInt(listeTransitions.size()));

                message = String.format("Transition choisie : %s\n", transitionChoisie.getUri());
                sb.append(message);

                transitionChoisie.activateTransition((I) transitionChoisie.getUri());
            }
            
            sb.append("\n");
            
            System.out.println(sb.toString());
            
            showReseau();
        }
	}


	@Override
	public void manualTransition(Scanner scanner) throws Exception {
		Set<Transition<I, R>> transitionsPossibles = update();
        if (transitionsPossibles.isEmpty()) {
            System.out.println("Aucune transition possible.");
            return;
        }

        System.out.println("Transitions possibles :");
        List<Transition<I, R>> listeTransitions = new ArrayList<>(transitionsPossibles);

        for (int i = 0; i < listeTransitions.size(); i++)
        	System.out.printf("%s,\n", listeTransitions.get(i).getUri());
        System.out.print("Choisissez une transition : ");
        int choix = scanner.nextInt();

        if (choix < 1 || choix > listeTransitions.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Transition<I, R> transitionChoisie = listeTransitions.get(choix - 1);
        transitionChoisie.activateTransition((I) transitionChoisie.getUri());
        showReseau();		
	}
}
