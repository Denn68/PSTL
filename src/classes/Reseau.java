package classes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Reseau 
extends Thread{
    ArrayList<Place> places;
    ArrayList<Transition> transitions;
    String uri;

    public Reseau(String uri) {
        this.places = new ArrayList<Place>();
        this.transitions = new ArrayList<Transition>();
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
            	sb.append(p.getNbJeton()).append(", ");
            } else {
            	sb.append(p.getNbJeton());
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

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }


    // Setters
    public void addPlace(Place place) {
        this.places.add(place);
    }

    public void addTransition(Transition transition) {
        this.transitions.add(transition);
    }

    public Set<Transition> update() {

        Set<Transition> transitionsPossibles = new HashSet<>();

        for (Place place : places) {

            if (place.getNbJeton() > 0) {
                for (Transition tr_sortie : place.getTransSorties()) {

                    boolean appendTrans = true;
                    for (Place place_entree : tr_sortie.getPlacesEntrees()) {

                        if (place_entree.getNbJeton() == 0) {
                            appendTrans = false;
                            break;
                        }
                    }

                    if (appendTrans && tr_sortie.isActivable())
                        transitionsPossibles.add(tr_sortie);
                }
            }
        }

        return transitionsPossibles;
    }

    public void randomTransition() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while(true) {
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

    // Fonction pour afficher les transitions possibles et permettre le choix
    public void manualTransition(Scanner scanner) {
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

}