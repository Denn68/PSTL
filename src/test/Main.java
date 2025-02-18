package test;
import java.util.*;

import backend.Backend;
import frontend.Frontend;

public class Main {
    public static void main(String[] args) {

    	// FAIS LE RESEAU QUE LE PROF A DONNER
    	Backend backend = new Backend();
    	
    	Frontend frontend = new Frontend(backend);
    	String r1 = "r1";
    	
    	String p1 = "p1";
    	String p2 = "p2";
    	String p3 = "p3";
    	String p4 = "p4";
    	
    	String t1 = "t1";
    	String t2 = "t2";
    	String t3 = "t3";
    	String t4 = "t4";
    	String t5 = "t5";
    	
    	frontend.CreatePlace(r1, p1);
    	frontend.CreatePlace(r1, p2);
    	frontend.CreatePlace(r1, p3);
    	frontend.CreatePlace(r1, p4);
    	
    	ArrayList<String> placesEntreesPourT1 = new ArrayList<String>();
    	placesEntreesPourT1.add(p2);
    	placesEntreesPourT1.add(p3);
    	ArrayList<String> placesSortiesPourT1 = new ArrayList<String>();
    	placesSortiesPourT1.add(p1);
    	frontend.LinkPlaces(r1, placesEntreesPourT1, placesSortiesPourT1, t1);
    	
    	ArrayList<String> placesEntreesPourT2 = new ArrayList<String>();
    	placesEntreesPourT2.add(p1);
    	ArrayList<String> placesSortiesPourT2 = new ArrayList<String>();
    	placesSortiesPourT1.add(p1);
    	frontend.LinkPlaces(r1, placesEntreesPourT1, placesSortiesPourT1, t1);
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