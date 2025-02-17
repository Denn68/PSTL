package classes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import interfaces.IPlateau;

public class Plateau implements IPlateau {
    ArrayList<Place> places;
    ArrayList<Transition> transitions;
    ArrayList<Jeton> jetons;

    public Plateau() {
        this.places = new ArrayList<Place>();
        this.transitions = new ArrayList<Transition>();
        this.jetons = new ArrayList<Jeton>();
    }

    @Override
    public void showPlateau() {
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
    @Override
    public ArrayList<Place> getPlaces() {
        return places;
    }

    @Override
    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    @Override
    public ArrayList<Jeton> getJetons() {
        return jetons;
    }

    // Setters
    @Override
    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    @Override
    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    @Override
    public void setJetons(ArrayList<Jeton> jetons) {
        this.jetons = jetons;
    }

    @Override
    public void activateTransition(Transition transition) {
        for (Place place : transition.getPlacesEntrees())
            place.setNbJeton(place.getNbJeton() - 1);
        for (Place place : transition.getPlacesSorties())
            place.setNbJeton(place.getNbJeton() + 1);
    }

    @Override
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

    @Override
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
    @Override
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