package classes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Reseau {
    ArrayList<Place> places;
    ArrayList<Transition> transitions;

    public Reseau() {
        this.places = new ArrayList<Place>();
        this.transitions = new ArrayList<Transition>();
    }

    public void showReseau() {
        int i = 0;
        System.out.print("(");
        for (Place p : places) {
            if (i != (places.size() - 1)) {
                System.out.print(p.getNbJeton() + ", ");
            } else {
                System.out.print(p.getNbJeton());
            }
            i++;
        }
        System.out.print(")\n");
    }

    // Getters
    public ArrayList<Place> getPlaces() {
        return places;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }


    // Setters
    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
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

                    if (appendTrans)
                        transitionsPossibles.add(tr_sortie);
                }
            }
        }

        return transitionsPossibles;
    }

    public void randomTransition(int maxTransitions) {
        Random random = new Random();

        for (int i = 0; i < maxTransitions; i++) {
            Set<Transition> transitionsPossibles = update();
            System.out.print("Transition possible : ");

            for (Transition t : transitionsPossibles)
            	// A FAIRE afficher trans possible

            System.out.println();

            if (transitionsPossibles.isEmpty()) {
                System.out.println("Aucune transition possible.");
                break;
            }

            List<Transition> listeTransitions = new ArrayList<>(transitionsPossibles);
            Transition transitionChoisie = listeTransitions.get(random.nextInt(listeTransitions.size()));

            // A FAIRE afficher trans choisie

            activateTransition(transitionChoisie);
            showPlateau();
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
        	// A FAIRE afficher trans possible
        System.out.print("Choisissez une transition : ");
        int choix = scanner.nextInt();

        if (choix < 1 || choix > listeTransitions.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Transition transitionChoisie = listeTransitions.get(choix - 1);
        activateTransition(transitionChoisie);
        showPlateau();
    }

}