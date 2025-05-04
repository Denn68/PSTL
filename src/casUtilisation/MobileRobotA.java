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
public class MobileRobotA<T, P>
extends ReseauComponent<T, P>
implements ReseauI<P>{
	
	public static final String		RESEAU_A_PLUGIN_URI = "reseau-a-plugin-uri";
	private String uri;
	
	@SuppressWarnings("unchecked")
	protected			MobileRobotA(String uri,
			String reflectionInboundPortURI,
			String semaphoreUpdatingAvailibility,
			ArrayList<String> semJetonUriList,
			ReseauEndpoint endPointServer,
			ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		
		super(uri,
				reflectionInboundPortURI,
				RESEAU_A_PLUGIN_URI,
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
    	
    	String tA1 = "tA1";
    	String tA2 = "tA2";
    	String tA3 = "tA3";
    	String tA4 = "tA4";
    	String tA5 = "tA5";
    	String tA6 = "tA6";
    	
    	  	
    	
    	Function<Void, Void> gotoPosition = input -> {
    	    System.out.println("gotoPosition p1");
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
    	
    	Transition TA1 = new Transition(tA1, (Function<Void, Void>) gotoPosition);
    	Transition TA2 = new Transition(tA2, (Function<Void, Void>) pick);
    	Transition TA3 = new Transition(tA3, (Function<Void, Void>) lift);
    	Transition TA4 = new Transition(tA4, (Function<Void, Void>) gotoPositionBis);
    	Transition TA5 = new Transition(tA5, (Function<Void, Void>) drop);
    	Transition TA6 = new Transition(tA6, (Function<Void, Void>) gotoBase);
    	
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
	
	
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P1);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P2);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P3);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P4);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P5);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P6);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P7);
		
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(TA1);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(TA2);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(TA3);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(TA4);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(TA5);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(TA6);
		
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
