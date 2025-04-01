package reseau;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

import classes.Place;
import classes.Transition;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;

public class ReseauAComponent<T, P>
extends ReseauComponent<T, P>{
	
	public static final String		RESEAU_A_PLUGIN_URI = "reseau-a-plugin-uri";
	private String uri;
	
	@SuppressWarnings("unchecked")
	protected			ReseauAComponent(String uri,
			String semaphorePluginAjoutInboundPortURI,
			String semaphorePluginRetraitInboundPortURI,
			ReseauEndpoint endPointServer,
			ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		
		super(uri,
				RESEAU_A_PLUGIN_URI,
				semaphorePluginAjoutInboundPortURI,
				semaphorePluginRetraitInboundPortURI,
				endPointServer,
				endPointClient);
		
		System.out.println(endPointClient.getClass());
		
		this.uri = uri;
		
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
    	
    	Transition T1 = new Transition(t1, (Function) fonction);
    	Transition T2 = new Transition(t2, (Function) fonction);
    	Transition T3 = new Transition(t3, (Function) fonction);
    	Transition T4 = new Transition(t4, (Function) fonction);
    	
    	T1.addPlacesEntree(new ArrayList<>(Arrays.asList(P1)));
    	T2.addPlacesSortie(new ArrayList<>(Arrays.asList(P2)));
    	T3.addPlacesEntree(new ArrayList<>(Arrays.asList(P2)));
    	T3.addPlacesSortie(new ArrayList<>(Arrays.asList(P3)));
    	T4.addPlacesEntree(new ArrayList<>(Arrays.asList(P3)));
    	T4.addPlacesSortie(new ArrayList<>(Arrays.asList(P4)));
	
	
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P1);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P2);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P3);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P4);
		
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(T1);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(T2);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(T3);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(T4);
		
	}

	
}
