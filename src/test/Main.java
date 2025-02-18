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
    	String r2 = "r2";
    	
    	String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	
    	String p1 = "p1";
    	String p2 = "p2";
    	String p3 = "p3";
    	String p4 = "p4";
    	String p5 = "p5";
    	String p6 = "p6";
    	String p7 = "p7";
    	String p8 = "p8";
    	String p9 = "p9";
    	
    	String t1 = "t1";
    	String t2 = "t2";
    	String t3 = "t3";
    	String t4 = "t4";
    	String t5 = "t5";
    	String t6 = "t6";
    	String t7 = "t7";
    	String t8 = "t8";
    	String t9 = "t9";
    	
    	frontend.CreateNetwork(r1);
    	frontend.CreateNetwork(r2);
    	
    	frontend.CreatePlacesCommunes(new ArrayList<>(List.of(pc1,pc2,pc3,pc4)));
    	
    	frontend.CreatePlaces(r1, new ArrayList<>(List.of(p1,p2,p3,p4)));
    	frontend.CreatePlaces(r2, new ArrayList<>(List.of(p5,p6,p7,p8,p9)));

    	frontend.LinkPlaces(r1, new ArrayList<>(List.of(p1)), new ArrayList<>(List.of(pc1)), t1);
    	frontend.LinkPlaces(r1, new ArrayList<>(List.of(pc3)), new ArrayList<>(List.of(p2)), t2);
    	frontend.LinkPlaces(r1, new ArrayList<>(List.of(p2,pc4)), new ArrayList<>(List.of(p3)), t3);
    	frontend.LinkPlaces(r1, new ArrayList<>(List.of(p3)), new ArrayList<>(List.of(pc4,p4)), t4);
    	frontend.LinkPlaces(r2, new ArrayList<>(List.of(p5)), new ArrayList<>(List.of(pc2)), t5);
    	frontend.LinkPlaces(r2, new ArrayList<>(List.of(pc1,pc2)), new ArrayList<>(List.of(p6,pc3)), t6);
    	frontend.LinkPlaces(r2, new ArrayList<>(List.of(p6)), new ArrayList<>(List.of(p7)), t7);
    	frontend.LinkPlaces(r2, new ArrayList<>(List.of(p7,pc4)), new ArrayList<>(List.of(p8)), t8);
    	frontend.LinkPlaces(r2, new ArrayList<>(List.of(p8)), new ArrayList<>(List.of(pc4,p9)), t9);


    	frontend.InitializePlace(r1, p1, 1);
    	frontend.InitializePlace(r2, p5, 1);
    	frontend.InitializePlaceCommune(pc4, 1);
    	
    	frontend.startPetriNetwork();

        // Lancer le menu
        /*Scanner scanner = new Scanner(System.in);
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
        scanner.close();*/
        
    }

}