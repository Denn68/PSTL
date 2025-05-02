package casUtilisation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;

import classes.Place;
import classes.PlaceCommune;
import classes.Transition;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.exceptions.ConnectionException;
import interfaces.ReseauCI;
import interfaces.ReseauI;
import reseau.ReseauComponent;
import reseau.ReseauEndpoint;
import reseau.ReseauPlugin;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;

@OfferedInterfaces(offered = { ReseauCI.class})
@RequiredInterfaces(required = { ReseauCI.class})
public class ForkliftC<T, P>
extends ReseauComponent<T, P>
implements ReseauI<P>{
	
	public static final String		FORKLIFT_C_PLUGIN_URI = "forklift-c-plugin-uri";
	private String uri;
	
	@SuppressWarnings("unchecked")
	protected			ForkliftC(String uri,
			String reflectionInboundPortURI,
			String semaphoreUpdatingAvailibility,
			String semaphoreUpdatingJeton,
			ArrayList<String> semJetonUriList,
			ReseauEndpoint endPointServer,
			ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		
		super(uri,
				reflectionInboundPortURI,
				FORKLIFT_C_PLUGIN_URI,
				endPointServer,
				endPointClient);
		
		this.uri = uri;
		
		HashMap<String, String> updatingJetons = new HashMap<String, String>();
		
		String pc6 = "pc6";
    	String pc7 = "pc7";
    	
    	updatingJetons.put(pc6, semJetonUriList.get(5));
    	updatingJetons.put(pc7, semJetonUriList.get(6));
    	
		
		String p1 = "p1";
    	String p2 = "p2";
    	String p3 = "p3";
    	String p4 = "p4";
    	String p5 = "p5";
    	
    	Place P1 = new Place(p1);
    	Place P2 = new Place(p2);
    	Place P3 = new Place(p3);
    	Place P4 = new Place(p4);
    	Place P5 = new Place(p5);
    	
    	P1.addJeton();
    	
    	String tC1 = "tC1";
    	String tC2 = "tC2";
    	String tC3 = "tC3";
    	String tC4 = "tC4";
    	String tC5 = "tC5";
    	
    	  	
    	
    	Function<String, String> gotoPosition = input -> {
    	    System.out.println("gotoPosition pb ");
    	    return "Test";
    	};
    	
    	Function<String, String> lift = input -> {
    	    System.out.println("lift");
    	    return "Test";
    	};
    	
    	Function<String, String> gotoPositionBis = input -> {
    	    System.out.println("gotoPosition pc");
    	    return "Test";
    	};
    	
    	Function<String, String> drop = input -> {
    	    System.out.println("drop");
    	    return "Test";
    	};
    	
    	Transition TC1 = new Transition(tC1, (Function) gotoPosition);
    	Transition TC2 = new Transition(tC2, (Function) lift);
    	Transition TC3 = new Transition(tC3, (Function) gotoPositionBis);
    	Transition TC4 = new Transition(tC4, (Function) drop);
    	Transition TC5 = new Transition(tC5, (Function) gotoPosition);
    	
    	TC1.addPlaceEntree(P1);
    	TC1.addPlaceSortie(P2);
    	TC2.addPlaceEntree(P2);
    	TC2.addPlaceSortie(P3);
    	TC3.addPlaceEntree(P3);
    	TC3.addPlaceSortie(P4);
    	TC4.addPlaceEntree(P4);
    	TC4.addPlaceSortie(P5);
    	TC5.addPlaceEntree(P5);
    	TC5.addPlaceSortie(P2);
	
	
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addPlace((P) P1);
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addPlace((P) P2);
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addPlace((P) P3);
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addPlace((P) P4);
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addPlace((P) P5);
		
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addTransition(TC1);
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addTransition(TC2);
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addTransition(TC3);
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addTransition(TC4);
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).addTransition(TC5);
		
		System.out.println("Connexion entre forklift C et réseau PlaceCommune...");
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tC2", pc6, semaphoreUpdatingAvailibility, updatingJetons.get(pc6));
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tC2", pc7, semaphoreUpdatingAvailibility, updatingJetons.get(pc7));
		((ReseauPlugin<P>) this.getPlugin(FORKLIFT_C_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tC3", pc7, semaphoreUpdatingAvailibility, updatingJetons.get(pc7));
	}
	
}
