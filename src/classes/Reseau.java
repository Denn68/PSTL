package classes;
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
import java.util.function.Function;

public class Reseau<I, R> 
extends Thread{
	Map<String, Place> places;
    ArrayList<Transition> transitions;
    private Map<String, String> updatingAvailability;
    private Map<String, String> updatingJetons;
    String uri;

    public Reseau(String uri) {
        this.places = new HashMap<String, Place>();
        this.transitions = new ArrayList<Transition>();
        this.updatingJetons = new HashMap<String, String>();
        this.updatingAvailability = new HashMap<String, String>();
        this.uri = uri;
    }
    
    @Override
    public void run() {
    	System.out.println("------- DEBUT -------");
    	showReseau();
        this.randomTransition();
    }
    
    public String getUri() {
    	return this.uri;
    }

    public void showReseau() {
        StringBuilder sb = new StringBuilder();
        sb.append("Réseau ").append(uri).append("(");

        Iterator<Place> it = places.values().iterator();
        while (it.hasNext()) {
            Place p = it.next();
            sb.append(((classes.Place) p).getNbJeton());
            if (it.hasNext()) sb.append(", ");
        }

        sb.append(")");
        System.out.println(sb.toString());
    }



    // Getters
    public Collection<Place> getPlaces() {
        return places.values();
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }


    // Setters
    public void addPlace(Place place) {
        this.places.put(place.getUri(), place);
    }

    public void addTransition(Transition transition) {
        this.transitions.add(transition);
    }

    public Set<Transition> update() {

        Set<Transition> transitionsPossibles = new HashSet<>();
        
        for (Transition transition: this.transitions) {
            AtomicBoolean appendTrans = new AtomicBoolean(true);

            for(String place: transition.getPlacesEntrees().keySet()) {
                if(transition.getUri().equals("t8")) {
                    System.out.println("ETAT T8 " + transition.isActivable());
                }

                this.places.forEach((key, value) -> {
                    if(key.equals(place)) {
                        if(value.getNbJeton() == 0) {
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
    
    @SuppressWarnings({ "unused", "unchecked" })
    public void activeTransition(Transition tr) {
    	if(tr.isActivable()) {
    		boolean skip = false;
    		for ( String placeCommune : tr.getPlacesCommuneEntrees().keySet()) {
				// skip = this.updatingJetons.get(placeCommune).tryAcquire();
    			// => tryAcquire dans le composant Semaphore updatingJeton avec l'URI placeCommune
    			// transition -> uriPlacesCommunes -> ReseauPlaceCommune-> Semaphore correspondante
	        }
    		if(!skip) {
    			System.out.printf("Not Skip - %s\n", uri);
				for (String placeCommune : tr.getPlacesCommuneEntrees().keySet()) {
					// placeCommune.retrieveJeton(uri); => retrieveJeton dans le composant ReseauPlaceCommune
					
					// this.updatingAvailability.get(placeCommune).release();
					// => release dans le composant Semaphore updatingAvailability avec l'URI placeCommune
	    			// transition -> uriPlacesCommunes -> ReseauPlaceCommune-> Semaphore correspondante
					
					// this.updatingJetons.get(placeCommune).release();
	    			// => release dans le composant Semaphore updatingJeton avec l'URI placeCommune
	    			// transition -> uriPlacesCommunes -> ReseauPlaceCommune-> Semaphore correspondante
		        }
				
		        for (String place : tr.getPlacesEntrees().keySet())
		        	places.get(place).retrieveJeton();
		        
		        for (String place : tr.getPlacesSorties())
		        	places.get(place).addJeton();
		        
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

    public void randomTransition() {
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
                //transitionChoisie.activateTransition((I) transitionChoisie.getUri());
            }
            
            sb.append("\n");
            
            System.out.println(sb.toString());
            
            showReseau();
        }
    }

    // Fonction pour afficher les transitions possibles et permettre le choix
    /*public void manualTransition(Scanner scanner) {
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
    }*/

}