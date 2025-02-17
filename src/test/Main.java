package test;
import java.util.*;

import classes.Place;
import classes.Plateau;
import classes.Transition;

public class Main {
    public static void main(String[] args) {
    	// ICI tu test, t'appel les fonctions du frontend en initialisant un seul backend. puis tu simule comme tu le faisiais ici, base toi sur les interfaces
    	/*
        // Créer les places
        Place p1 = new Place(1, 0, 1, new ArrayList<>(), new ArrayList<>());
        Place p2 = new Place(1, 1, 2, new ArrayList<>(), new ArrayList<>());
        Place p3 = new Place(2, 0, 3, new ArrayList<>(), new ArrayList<>());
        Place p4 = new Place(1, 1, 4, new ArrayList<>(), new ArrayList<>());

        // Créer les transitions
        Transition t1 = new Transition(2, 1, new ArrayList<>(List.of(p2, p3)), new ArrayList<>(List.of(p1)));
        Transition t2 = new Transition(2, 2, new ArrayList<>(List.of(p1)), new ArrayList<>(List.of(p2)));
        Transition t3 = new Transition(2, 3, new ArrayList<>(List.of(p1)), new ArrayList<>(List.of(p2, p4)));
        Transition t4 = new Transition(2, 4, new ArrayList<>(List.of(p3)), new ArrayList<>(List.of(p4)));
        Transition t5 = new Transition(2, 5, new ArrayList<>(List.of(p4)), new ArrayList<>(List.of(p3)));
        

        // Ajouter les transitions aux places correspondantes
        p1.getTransSorties().add(t2);
        p1.getTransSorties().add(t3);
        p1.getTransEntrees().add(t1);

        p2.getTransSorties().add(t1);
        p2.getTransEntrees().add(t2);
        p2.getTransEntrees().add(t3);

        p3.getTransSorties().add(t1);
        p3.getTransSorties().add(t4);
        p3.getTransEntrees().add(t5);

        p4.getTransSorties().add(t5);
        p4.getTransEntrees().add(t3);
        p4.getTransEntrees().add(t4);

        // Créer le plateau
        Plateau plateau = new Plateau(new ArrayList<>(List.of(p1, p2, p3, p4)),
                new ArrayList<>(List.of(t1, t2)),
                new ArrayList<>());

        // Lancer le menu
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        while (continuer) {
            System.out.println("\nPlateau actuel :");
            plateau.showPlateau();
            System.out.println("\nMenu:");
            System.out.println("1. Transitions aléatoires");
            System.out.println("2. Choisir une transition");
            System.out.println("3. Quitter");
            System.out.print("Votre choix: ");

            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    System.out.print("Nombre maximum de transitions aléatoires: ");
                    int maxTransitions = scanner.nextInt();
                    plateau.randomTransition(maxTransitions);
                    break;
                case 2:
                    plateau.manualTransition(scanner);
                    break;
                case 3:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide. Réessayez.");
            }
        }
        scanner.close();
        */
    }

}