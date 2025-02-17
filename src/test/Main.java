package test;
import java.util.*;

import Backend.Backend;
import classes.Place;
import classes.Plateau;
import classes.Transition;
import frontend.Frontend;

public class Main {
    public static void main(String[] args) {

    	Backend backend = new Backend();
    	
    	Frontend frontend = new Frontend(backend);
    	
    	String p1 = "p1";
    	String p2 = "p2";
    	String p3 = "p3";
    	String p4 = "p4";
    	
    	String t1 = "t1";
    	String t2 = "t2";
    	String t3 = "t3";
    	String t4 = "t4";
    	String t5 = "t5";
    	
    	frontend.CreatePlace(p1);
    	frontend.CreatePlace(p2);
    	frontend.CreatePlace(p3);
    	frontend.CreatePlace(p4);
    	
    	frontend.LinkPlaces(p2, p1, t1);
    	frontend.LinkPlaces(p3, p1, t1);
    	frontend.LinkPlaces(p1, p2, t2);
    	frontend.LinkPlaces(p1, p2, t3);
    	frontend.LinkPlaces(p1, p4, t3);
    	frontend.LinkPlaces(p3, p4, t4);
    	frontend.LinkPlaces(p4, p3, t5);

    	frontend.InitializePlace(p2, 1);
    	frontend.InitializePlace(p4, 1);

        // Lancer le menu
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        while (continuer) {
            System.out.println("\nPlateau actuel :");
            frontend.showPlateau();
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
                    frontend.randomTransition(maxTransitions);
                    break;
                case 2:
                    frontend.manualTransition(scanner);
                    break;
                case 3:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide. Réessayez.");
            }
        }
        scanner.close();
        
    }

}