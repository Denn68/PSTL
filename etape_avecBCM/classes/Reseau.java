package classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import classes_sansBCM.Place;

public class Reseau<I, R> extends Thread {
    Map<String, Place> places; // Stocke les places du réseau (identifiées par leur URI)
    ArrayList<Transition> transitions; // Liste des transitions du réseau
    private Map<String, String> updatingAvailability; // Placeholder pour gestion future des sémaphores d'accessibilité
    private Map<String, String> updatingJetons; // Placeholder pour gestion future des sémaphores de jetons
    String uri; // Identifiant du réseau

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
        showReseau(); // Affiche l'état initial du réseau
        this.randomTransition(); // Lance la boucle d'exécution aléatoire des transitions
    }

    public String getUri() {
        return this.uri;
    }

    // Affiche le nombre de jetons de chaque place du réseau
    public void showReseau() {
        StringBuilder sb = new StringBuilder();
        sb.append("Réseau ").append(uri).append("(");

        Iterator<Place> it = places.values().iterator();
        while (it.hasNext()) {
            Place p = it.next();
            sb.append(((classes_sansBCM.Place) p).getNbJeton());
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

    // Calcule l'ensemble des transitions activables à l'instant t
    public Set<Transition> update() {
        Set<Transition> transitionsPossibles = new HashSet<>();

        for (Transition transition : this.transitions) {
            AtomicBoolean appendTrans = new AtomicBoolean(true);

            // Vérifie que chaque place d'entrée a au moins un jeton
            for (String place : transition.getPlacesEntrees().keySet()) {
                if (transition.getUri().equals("t8")) {
                    System.out.println("ETAT T8 " + transition.isActivable());
                }

                this.places.forEach((key, value) -> {
                    if (key.equals(place)) {
                        if (value.getNbJeton() == 0) {
                            appendTrans.set(false);
                        }
                    }
                });
            }

            // Si les conditions sont réunies et que la transition est activable, on l'ajoute
            if (appendTrans.get() && transition.isActivable())
                transitionsPossibles.add(transition);
        }

        return transitionsPossibles;
    }

    @SuppressWarnings({ "unused", "unchecked" })
    public void activeTransition(Transition tr) {
        if (tr.isActivable()) {
            boolean skip = false;

            // Gestion prévue pour les places communes (non encore implémentée)
            for (String placeCommune : tr.getPlacesCommuneEntrees().keySet()) {
                // Tentative d'acquisition de sémaphore (non implémentée ici)
            }

            if (!skip) {
                System.out.printf("Not Skip - %s\n", uri);

                // Gestion des places communes d'entrée (non implémentée ici)
                for (String placeCommune : tr.getPlacesCommuneEntrees().keySet()) {
                    // Récupération d’un jeton depuis la place partagée (à implémenter)
                    // Libération de sémaphores de coordination (à implémenter)
                }

                // Retrait des jetons dans les places d'entrée locales
                for (String place : tr.getPlacesEntrees().keySet())
                    places.get(place).retrieveJeton();

                // Ajout de jetons dans les places de sortie locales
                for (String place : tr.getPlacesSorties())
                    places.get(place).addJeton();

                // Ajout de jetons dans les places communes de sortie (à implémenter)
                for (String placeCommune : tr.getPlacesCommuneSorties()) {
                    // Ajout d’un jeton à une place partagée (à implémenter)
                    // Libération de sémaphores de coordination (à implémenter)
                }

                // Exécution de la fonction liée à l'activation de la transition
                tr.getActivableFunction().apply(null);
            } else {
                System.out.println("La transition a été prise par un autre thread");
            }
        }
    }

    // Boucle principale qui exécute aléatoirement des transitions activables
    public void randomTransition() {
        Random random = new Random();
        while (true) {
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

                // Sélection aléatoire d'une transition parmi celles possibles
                Transition transitionChoisie = listeTransitions.get(random.nextInt(listeTransitions.size()));

                message = String.format("Transition choisie : %s\n", transitionChoisie.getUri());
                sb.append(message);

                activeTransition(transitionChoisie); // Activation réelle de la transition
            }

            sb.append("\n");
            System.out.println(sb.toString());

            showReseau(); // Affichage de l'état courant du réseau
        }
    }

    // Méthode pour choix manuel d'une transition (désactivée ici)
    /*
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
    */
}
