package reseau;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

import classes.Place;
import classes.Transition;

public class ReseauBComponent<T, I, R, P>
extends ReseauComponent<T, I, R, P>{
	
	public static final String		RESEAU_PLUGIN_URI = "reseau-plugin-uri";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected			ReseauBComponent(String uri) throws Exception
	{
		super(uri);
		String p5 = "p5";
    	String p6 = "p6";
    	String p7 = "p7";
    	String p8 = "p8";
    	String p9 = "p9";
    	
    	String t5 = "t5";
    	String t6 = "t6";
    	String t7 = "t7";
    	String t8 = "t8";
    	String t9 = "t9";
    	
    	Place P5 = new Place(p5);
    	Place P6 = new Place(p6);
    	Place P7 = new Place(p7);
    	Place P8 = new Place(p8);
    	Place P9 = new Place(p9);
    	
    	Function<String, String> fonction = input -> {
    	    System.out.println("Fonction activable de la transition: " + input);
    	    return "Transition activée: " + input;
    	};
    	
    	Transition<I, R> T5 = new Transition<I, R>(t5, (Function<I, R>) fonction);
    	Transition<I, R> T6 = new Transition<I, R>(t6, (Function<I, R>) fonction);
    	Transition<I, R> T7 = new Transition<I, R>(t7, (Function<I, R>) fonction);
    	Transition<I, R> T8 = new Transition<I, R>(t8, (Function<I, R>) fonction);
    	Transition<I, R> T9 = new Transition<I, R>(t9, (Function<I, R>) fonction);
    	
    	T5.addPlacesEntree(new ArrayList<>(Arrays.asList(P5)));
    	T6.addPlacesSortie(new ArrayList<>(Arrays.asList(P6)));
    	T7.addPlacesEntree(new ArrayList<>(Arrays.asList(P6)));
    	T7.addPlacesSortie(new ArrayList<>(Arrays.asList(P7)));
    	T8.addPlacesEntree(new ArrayList<>(Arrays.asList(P7)));
    	T8.addPlacesSortie(new ArrayList<>(Arrays.asList(P8)));
    	T9.addPlacesEntree(new ArrayList<>(Arrays.asList(P8)));
    	T9.addPlacesSortie(new ArrayList<>(Arrays.asList(P9)));
    	
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addPlace((P) P5);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addPlace((P) P6);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addPlace((P) P7);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addPlace((P) P8);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addPlace((P) P9);
		
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addTransition(T5);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addTransition(T6);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addTransition(T7);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addTransition(T8);
		((ReseauPlugin<I, R, P>) this.getPlugin(uri)).addTransition(T9);
	}

	
}
