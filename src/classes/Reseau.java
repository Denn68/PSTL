package classes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;

public class Reseau<I, R, Place> 
extends Thread{
    ArrayList<Place> places;
    ArrayList<Transition<I, R>> transitions;
    String uri;

    public Reseau(String uri) {
        this.places = new ArrayList<Place>();
        this.transitions = new ArrayList<Transition<I, R>>();
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

    // Getters
    public ArrayList<Place> getPlaces() {
        return places;
    }

    public ArrayList<Transition<I, R>> getTransitions() {
        return transitions;
    }


    // Setters
    public void addPlace(Place place) {
        this.places.add(place);
    }

    public void addTransition(Transition<I, R> transition) {
        this.transitions.add(transition);
    }

    public Set<Transition<I, R>> update() {

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

    public void randomTransition() {
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

    // Fonction pour afficher les transitions possibles et permettre le choix
    public void manualTransition(Scanner scanner) {
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