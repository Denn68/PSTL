package reseau;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

import classes.Place;
import classes.Transition;

public class ReseauAComponent<T, I, R, P>
extends ReseauComponent<T, I, R, P>{
	
	public static final String		RESEAU_PLUGIN_URI = "reseau-plugin-uri";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected			ReseauAComponent(String uri) throws Exception
	{
		super(uri);
		String p1 = "p1";
    	String p2 = "p2";
    	String p3 = "p3";
    	String p4 = "p4";
    	
    	String t1 = "t1";
    	String t2 = "t2";
    	String t3 = "t3";
    	String t4 = "t4";
    	
    	Place P1 = new Place(p1);
    	Place P2 = new Place(p2);
    	Place P3 = new Place(p3);
    	Place P4 = new Place(p4);
    	
    	Function<String, String> fonction = input -> {
    	    System.out.println("Fonction activable de la transition: " + input);
    	    return "Transition activée: " + input;
    	};
    	
    	Transition<I, R> T1 = new Transition<I, R>(t1, (Function<I, R>) fonction);
    	Transition<I, R> T2 = new Transition<I, R>(t2, (Function<I, R>) fonction);
    	Transition<I, R> T3 = new Transition<I, R>(t3, (Function<I, R>) fonction);
    	Transition<I, R> T4 = new Transition<I, R>(t4, (Function<I, R>) fonction);
    	
    	T1.addPlacesEntree(new ArrayList<>(Arrays.asList(P1)));
    	T2.addPlacesSortie(new ArrayList<>(Arrays.asList(P2)));
    	T3.addPlacesEntree(new ArrayList<>(Arrays.asList(P2)));
    	T3.addPlacesSortie(new ArrayList<>(Arrays.asList(P3)));
    	T4.addPlacesEntree(new ArrayList<>(Arrays.asList(P3)));
    	T4.addPlacesSortie(new ArrayList<>(Arrays.asList(P4)));
    	
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addPlace((P) P1);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addPlace((P) P2);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addPlace((P) P3);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addPlace((P) P4);
		
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addTransition(T1);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addTransition(T2);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addTransition(T3);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addTransition(T4);
	}

	
}
