package classes_sansBCM;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Reseau<T, R> 
extends Thread {
    // Liste des places du réseau
    ArrayList<Place> places;

    // Liste des transitions du réseau
    ArrayList<Transition<T, R>> transitions;

    // URI identifiant le réseau
    String uri;

    // Constructeur
    public Reseau(String uri) {
        this.places = new ArrayList<Place>();
        this.transitions = new ArrayList<Transition<T, R>>();
        this.uri = uri;
    }

    // Méthode principale exécutée dans le thread
    @Override
    public void run() {
        System.out.println("------- DEBUT -------");
        showReseau();         // Affiche l'état initial du réseau
        this.randomTransition(); // Démarre le tirage aléatoire des transitions
    }

    // Retourne l'identifiant du réseau
    public String getUri() {
        return this.uri;
    }

    // Affiche les jetons présents dans chaque place du réseau
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

    public ArrayList<Transition<T, R>> getTransitions() {
        return transitions;
    }

    // Ajoute une place au réseau
    public void addPlace(Place place) {
        this.places.add(place);
    }

    // Ajoute une transition au réseau
    public void addTransition(Transition<T, R> transition) {
        this.transitions.add(transition);
    }

    // Met à jour la liste des transitions activables
    public Set<Transition<T, R>> update() {
        Set<Transition<T, R>> transitionsPossibles = new HashSet<>();

        for (Transition<T, R> transition : this.transitions) {
            boolean appendTrans = true;

            // Vérifie que toutes les places d'entrée ont au moins un jeton
            for (Place p : transition.getPlacesEntrees()) {
                if (transition.getUri() == "t8") {
                    System.out.println("ETAT T8" + transition.isActivable());
                }
                if (p.getNbJeton() == 0) {
                    appendTrans = false;
                }
            }

            // Ajoute la transition si elle est activable
            if (appendTrans && transition.isActivable())
                transitionsPossibles.add(transition);
        }

        return transitionsPossibles;
    }

    // Choisit et active aléatoirement une transition activable
    @SuppressWarnings("unchecked")
	public void randomTransition() {
        Random random = new Random();
        while (true) {
            StringBuilder sb = new StringBuilder();
            Set<Transition<T, R>> transitionsPossibles = update();
            sb.append("Transitions possible : ");
            String message = new String();

            // Liste les transitions activables
            for (Transition<T, R> t : transitionsPossibles) {
                message = String.format("%s,", t.getUri());
                sb.append(message);
            }

            sb.append("\n");

            if (transitionsPossibles.isEmpty()) {
                sb.append("Aucune transition possible.");
                try {
                    Thread.sleep(300); // Attente pour éviter la boucle infinie trop rapide
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // Choisit une transition aléatoirement parmi celles possibles
                List<Transition<T, R>> listeTransitions = new ArrayList<>(transitionsPossibles);
                Transition<T, R> transitionChoisie = listeTransitions.get(random.nextInt(listeTransitions.size()));

                message = String.format("Transition choisie : %s\n", transitionChoisie.getUri());
                sb.append(message);

                // Active la transition choisie
                transitionChoisie.activateTransition((T) transitionChoisie.getUri());
            }

            sb.append("\n");

            System.out.println(sb.toString());

            // Affiche l’état du réseau après activation
            showReseau();
        }
    }

    // Permet à l'utilisateur de choisir manuellement une transition
    @SuppressWarnings("unchecked")
	public void manualTransition(Scanner scanner) {
        Set<Transition<T, R>> transitionsPossibles = update();
        if (transitionsPossibles.isEmpty()) {
            System.out.println("Aucune transition possible.");
            return;
        }

        System.out.println("Transitions possibles :");
        List<Transition<T, R>> listeTransitions = new ArrayList<>(transitionsPossibles);

        for (int i = 0; i < listeTransitions.size(); i++)
            System.out.printf("%s,\n", listeTransitions.get(i).getUri());

        System.out.print("Choisissez une transition : ");
        int choix = scanner.nextInt();

        if (choix < 1 || choix > listeTransitions.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Transition<T, R> transitionChoisie = listeTransitions.get(choix - 1);

        // Active la transition choisie
        transitionChoisie.activateTransition((T) transitionChoisie.getUri());
        showReseau();
    }
}
