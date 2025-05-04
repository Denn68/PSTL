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

@OfferedInterfaces(offered = { ReseauCI.class})
@RequiredInterfaces(required = { ReseauCI.class})
public class MobileRobotB<T, P>
extends ReseauComponent<T, P>
implements ReseauI<P>{
	
	public static final String		RESEAU_B_PLUGIN_URI = "reseau-b-plugin-uri";
	private String uri;
	
	@SuppressWarnings("unchecked")
	protected			MobileRobotB(String uri,
			String reflectionInboundPortURI,
			String semaphoreUpdatingAvailibility,
			ArrayList<String> semJetonUriList,
			ReseauEndpoint endPointServer,
			ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		
		super(uri,
				reflectionInboundPortURI,
				RESEAU_B_PLUGIN_URI,
				endPointServer,
				endPointClient);
		
		this.uri = uri;
		
		HashMap<String, String> updatingJetons = new HashMap<String, String>();
		
		String pc1 = "pc1";
    	String pc2 = "pc2";
    	String pc3 = "pc3";
    	String pc4 = "pc4";
    	String pc5 = "pc5";
    	
    	updatingJetons.put(pc1, semJetonUriList.get(0));
    	updatingJetons.put(pc2, semJetonUriList.get(1));
    	updatingJetons.put(pc3, semJetonUriList.get(2));
    	updatingJetons.put(pc4, semJetonUriList.get(3));
    	updatingJetons.put(pc5, semJetonUriList.get(4));
    	
    	//System.out.println("[" + this.uri + "] Updating jetons: " + updatingJetons);
		
		String p1 = "p1";
    	String p2 = "p2";
    	String p3 = "p3";
    	String p4 = "p4";
    	String p5 = "p5";
    	String p6 = "p6";
    	String p7 = "p7";
    	
    	Place P1 = new Place(p1);
    	Place P2 = new Place(p2);
    	Place P3 = new Place(p3);
    	Place P4 = new Place(p4);
    	Place P5 = new Place(p5);
    	Place P6 = new Place(p6);
    	Place P7 = new Place(p7);
    	
    	P1.addJeton();
    	
    	String tB1 = "tB1";
    	String tB2 = "tB2";
    	String tB3 = "tB3";
    	String tB4 = "tB4";
    	String tB5 = "tB5";
    	String tB6 = "tB6";
    	
    	  	
    	
    	Function<Void, Void> gotoPosition = input -> {
    	    System.out.println("gotoPosition p1 ");
    	    return null;
    	};
    	
    	Function<Void, Void> pick = input -> {
    	    System.out.println("pick");
    	    return null;
    	};
    	
    	Function<Void, Void> lift = input -> {
    	    System.out.println("lift");
    	    return null;
    	};
    	
    	Function<Void, Void> gotoPositionBis = input -> {
    	    System.out.println("gotoPosition pa");
    	    return null;
    	};
    	
    	Function<Void, Void> drop = input -> {
    	    System.out.println("drop");
    	    return null;
    	};
    	
    	Function<Void, Void> gotoBase = input -> {
    	    System.out.println("gotoBase");
    	    return null;
    	};
    	
    	Transition TB1 = new Transition(tB1, (Function<Void, Void>) gotoPosition);
    	Transition TB2 = new Transition(tB2, (Function<Void, Void>) pick);
    	Transition TB3 = new Transition(tB3, (Function<Void, Void>) lift);
    	Transition TB4 = new Transition(tB4, (Function<Void, Void>) gotoPositionBis);
    	Transition TB5 = new Transition(tB5, (Function<Void, Void>) drop);
    	Transition TB6 = new Transition(tB6, (Function<Void, Void>) gotoBase);
    	
    	TB1.addPlaceEntree(P1,1);
    	TB1.addPlaceSortie(P2);
    	TB2.addPlaceEntree(P2,1);
    	TB2.addPlaceSortie(P3);
    	TB3.addPlaceEntree(P3,1);
    	TB3.addPlaceSortie(P4);
    	TB4.addPlaceEntree(P4,1);
    	TB4.addPlaceSortie(P5);
    	TB5.addPlaceEntree(P5,1);
    	TB5.addPlaceSortie(P6);
    	TB6.addPlaceEntree(P6,1);
    	TB6.addPlaceSortie(P7);
	
	
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P1);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P2);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P3);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P4);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P5);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P6);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P7);
		
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(TB1);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(TB2);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(TB3);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(TB4);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(TB5);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(TB6);
		
		System.out.println("Connexion entre robot B et réseau PlaceCommune...");
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tB1", pc1, semaphoreUpdatingAvailibility, updatingJetons.get(pc1));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tB2", pc1, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc1));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tB2", pc2, semaphoreUpdatingAvailibility, updatingJetons.get(pc2));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tB3", pc2, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc2));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tB3", pc3, semaphoreUpdatingAvailibility, updatingJetons.get(pc3));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tB4", pc3, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc3));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tB4", pc4, semaphoreUpdatingAvailibility, updatingJetons.get(pc4));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tB5", pc4, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc4));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tB5", pc5, semaphoreUpdatingAvailibility, updatingJetons.get(pc5));
		
	}
	
}