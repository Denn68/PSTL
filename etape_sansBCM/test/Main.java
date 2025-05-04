package test;
import java.util.*;
import java.util.function.Function;

import backend.Backend;
import frontend.Frontend;

public class Main {
    public static void main(String[] args) {

    	// Création du backend (logique du réseau de Petri)
    	Backend backend = new Backend();
    	
    	// Création du frontend (interface de gestion par l'utilisateur)
    	Frontend frontend = new Frontend(backend);
    	
    	// Identifiants des réseaux de Petri
    	String r1 = "r1";
    	String r2 = "r2";
    	
    	// Places communes partagées entre les réseaux
    	String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	
    	// Places du réseau r1
    	String p1 = "p1";
    	String p2 = "p2";
    	String p3 = "p3";
    	String p4 = "p4";
    	
    	// Places du réseau r2
    	String p5 = "p5";
    	String p6 = "p6";
    	String p7 = "p7";
    	String p8 = "p8";
    	String p9 = "p9";
    	
    	// Transitions utilisées dans les réseaux
    	String t1 = "t1";
    	String t2 = "t2";
    	String t3 = "t3";
    	String t4 = "t4";
    	String t5 = "t5";
    	String t6 = "t6";
    	String t7 = "t7";
    	String t8 = "t8";
    	String t9 = "t9";
    	
    	// Fonction associée aux transitions : ici un simple affichage console
    	Function<String, String> fonction = input -> {
    	    System.out.println("Fonction activable de la transition: " + input);
    	    return "Transition activée: " + input;
    	};

    	// Création des deux réseaux
    	frontend.CreateNetwork(r1);
    	frontend.CreateNetwork(r2);
    	
    	// Création des places communes entre les réseaux
    	frontend.CreatePlacesCommunes(new ArrayList<>(Arrays.asList(pc1,pc2,pc3,pc4)));
    	
    	// Création des places propres à chaque réseau
    	frontend.CreatePlaces(r1, new ArrayList<>(Arrays.asList(p1,p2,p3,p4)));
    	frontend.CreatePlaces(r2, new ArrayList<>(Arrays.asList(p5,p6,p7,p8,p9)));

    	// Définition des transitions pour r1 avec les places en entrée et sortie
    	frontend.LinkPlaces(r1, new ArrayList<>(Arrays.asList(p1)), new ArrayList<>(Arrays.asList(pc1)), t1, fonction);
    	frontend.LinkPlaces(r1, new ArrayList<>(Arrays.asList(pc3)), new ArrayList<>(Arrays.asList(p2)), t2, fonction);
    	frontend.LinkPlaces(r1, new ArrayList<>(Arrays.asList(p2,pc4)), new ArrayList<>(Arrays.asList(p3)), t3, fonction);
    	frontend.LinkPlaces(r1, new ArrayList<>(Arrays.asList(p3)), new ArrayList<>(Arrays.asList(pc4,p4)), t4, fonction);
    	
    	// Définition des transitions pour r2 avec les places en entrée et sortie
    	frontend.LinkPlaces(r2, new ArrayList<>(Arrays.asList(p5)), new ArrayList<>(Arrays.asList(pc2)), t5, fonction);
    	frontend.LinkPlaces(r2, new ArrayList<>(Arrays.asList(pc1,pc2)), new ArrayList<>(Arrays.asList(p6,pc3)), t6, fonction);
    	frontend.LinkPlaces(r2, new ArrayList<>(Arrays.asList(p6)), new ArrayList<>(Arrays.asList(p7)), t7, fonction);
    	frontend.LinkPlaces(r2, new ArrayList<>(Arrays.asList(p7,pc4)), new ArrayList<>(Arrays.asList(p8)), t8, fonction);
    	frontend.LinkPlaces(r2, new ArrayList<>(Arrays.asList(p8)), new ArrayList<>(Arrays.asList(pc4,p9)), t9, fonction);

    	// Initialisation des marquages (jetons) dans les places initiales
    	frontend.InitializePlace(r1, p1, 1);     // 1 jeton dans p1 de r1
    	frontend.InitializePlace(r2, p5, 1);     // 1 jeton dans p5 de r2
    	frontend.InitializePlaceCommune(pc4, 1); // 1 jeton dans la place commune pc4
    	
    	// Démarrage du réseau de Petri (exécution des transitions)
    	frontend.startPetriNetwork();

        // Partie interactive (menu) désactivée pour l’instant
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
