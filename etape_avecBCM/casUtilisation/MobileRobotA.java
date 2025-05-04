package casUtilisation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import classes.Place;
import classes.Transition;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import interfaces.ReseauCI;
import interfaces.ReseauI;
import reseau.ReseauComponent;
import reseau.ReseauEndpoint;
import reseau.ReseauPlugin;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;

@OfferedInterfaces(offered = { ReseauCI.class}) // Déclare les interfaces offertes par le composant
@RequiredInterfaces(required = { ReseauCI.class}) // Déclare les interfaces requises par le composant
public class MobileRobotA<T, P>
extends ReseauComponent<T, P> // Hérite de la classe ReseauComponent
implements ReseauI<P>{ // Implémente l'interface ReseauI
	
	public static final String		RESEAU_A_PLUGIN_URI = "reseau-a-plugin-uri"; // URI du plugin réseau A
	private String uri; // URI spécifique à l'instance du robot
	
	@SuppressWarnings("unchecked") // Ignore l'avertissement concernant le cast générique
	protected			MobileRobotA(String uri,
			String reflectionInboundPortURI,
			String semaphoreUpdatingAvailibility,
			ArrayList<String> semJetonUriList,
			ReseauEndpoint endPointServer,
			ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		
		// Appel du constructeur de la classe parente (ReseauComponent)
		super(uri,
				reflectionInboundPortURI,
				RESEAU_A_PLUGIN_URI,
				endPointServer,
				endPointClient);
		
		this.uri = uri; // Initialisation de l'URI spécifique à ce robot
		
		// Initialisation de la map pour stocker les jetons de mise à jour
		HashMap<String, String> updatingJetons = new HashMap<String, String>();
		
		// Définition des identifiants pour les places
		String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	String pc5 = "pc5";
    	
    	// Mise en place des jetons de mise à jour pour chaque place
    	updatingJetons.put(pc1, semJetonUriList.get(0));
    	updatingJetons.put(pc2, semJetonUriList.get(1));
    	updatingJetons.put(pc3, semJetonUriList.get(2));
    	updatingJetons.put(pc4, semJetonUriList.get(3));
    	updatingJetons.put(pc5, semJetonUriList.get(4));
    	
    	// Définition des identifiants pour les places
		String p1 = "p1";
    	String p2 = "p2";
    	String p3 = "p3";
    	String p4 = "p4";
    	String p5 = "p5";
    	String p6 = "p6";
    	String p7 = "p7";
    	
    	// Création des objets Place pour chaque identifiant
    	Place P1 = new Place(p1);
    	Place P2 = new Place(p2);
    	Place P3 = new Place(p3);
    	Place P4 = new Place(p4);
    	Place P5 = new Place(p5);
    	Place P6 = new Place(p6);
    	Place P7 = new Place(p7);
    	
    	P1.addJeton(); // Ajout d'un jeton à la place P1
    	
    	// Définition des identifiants pour les transitions
    	String tA1 = "tA1";
    	String tA2 = "tA2";
    	String tA3 = "tA3";
    	String tA4 = "tA4";
    	String tA5 = "tA5";
    	String tA6 = "tA6";
    	
    	// Définition des fonctions qui représentent les actions dans les transitions
    	Function<String, Void> gotoPosition = input -> {
    	    System.out.println("gotoPosition p1"); // Action pour aller à la position p1
    	    return null;
    	};
    	
    	Function<String, Void> pick = input -> {
    	    System.out.println("pick"); // Action pour effectuer un pick
    	    return null;
    	};
    	
    	Function<String, Void> lift = input -> {
    	    System.out.println("lift"); // Action pour soulever un objet
    	    return null;
    	};
    	
    	Function<String, Void> gotoPositionBis = input -> {
    	    System.out.println("gotoPosition pa"); // Action pour aller à une autre position
    	    return null;
    	};
    	
    	Function<String, Void> drop = input -> {
    	    System.out.println("drop"); // Action pour déposer un objet
    	    return null;
    	};
    	
    	Function<String, Void> gotoBase = input -> {
    	    System.out.println("gotoBase"); // Action pour retourner à la base
    	    return null;
    	};
    	
    	// Création des transitions en associant une action à chaque transition
    	Transition TA1 = new Transition(tA1, (Function<String, Void>) gotoPosition);
    	Transition TA2 = new Transition(tA2, (Function<String, Void>) pick);
    	Transition TA3 = new Transition(tA3, (Function<String, Void>) lift);
    	Transition TA4 = new Transition(tA4, (Function<String, Void>) gotoPositionBis);
    	Transition TA5 = new Transition(tA5, (Function<String, Void>) drop);
    	Transition TA6 = new Transition(tA6, (Function<String, Void>) gotoBase);
    	
    	// Ajout des places aux transitions
    	TA1.addPlaceEntree(P1,1);
    	TA1.addPlaceSortie(P2);
    	TA2.addPlaceEntree(P2,1);
    	TA2.addPlaceSortie(P3);
    	TA3.addPlaceEntree(P3,1);
    	TA3.addPlaceSortie(P4);
    	TA4.addPlaceEntree(P4,1);
    	TA4.addPlaceSortie(P5);
    	TA5.addPlaceEntree(P5,1);
    	TA5.addPlaceSortie(P6);
    	TA6.addPlaceEntree(P6,1);
    	TA6.addPlaceSortie(P7);
	
		// Ajout des places au plugin réseau
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P1);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P2);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P3);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P4);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P5);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P6);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P7);
		
		// Ajout des transitions au plugin réseau
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(TA1);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(TA2);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(TA3);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(TA4);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(TA5);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(TA6);
		
		// Connexion des transitions aux places communes avec les jetons de mise à jour
		System.out.println("Connexion entre robot A et réseau PlaceCommune...");
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tA1", pc1, semaphoreUpdatingAvailibility, updatingJetons.get(pc1));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tA2", pc1, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc1));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tA2", pc2, semaphoreUpdatingAvailibility, updatingJetons.get(pc2));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tA3", pc2, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc2));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tA3", pc3, semaphoreUpdatingAvailibility, updatingJetons.get(pc3));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tA4", pc3, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc3));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tA4", pc4, semaphoreUpdatingAvailibility, updatingJetons.get(pc4));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tA5", pc4, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc4));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tA5", pc5, semaphoreUpdatingAvailibility, updatingJetons.get(pc5));
		
	}
	
}
