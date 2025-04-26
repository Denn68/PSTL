package reseau;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

import classes.Place;
import classes.Transition;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import interfaces.ReseauCI;
import interfaces.ReseauI;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;

@OfferedInterfaces(offered = { ReseauCI.class})
@RequiredInterfaces(required = { ReseauCI.class})
public class ReseauBComponent<T, P>
extends ReseauComponent<T, P>
implements ReseauI<P>{
	
	public static final String		RESEAU_B_PLUGIN_URI = "reseau-b-plugin-uri";
	private String uri;

	@SuppressWarnings("unchecked")
	protected			ReseauBComponent(String uri,
			String reflectionInboundPortURI,
			//String semaphorePluginAjoutInboundPortURI,
			//String semaphorePluginRetraitInboundPortURI,
			ReseauEndpoint endPointServer,
			ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		
		super(uri,
				reflectionInboundPortURI,
				RESEAU_B_PLUGIN_URI,
				//semaphorePluginAjoutInboundPortURI,
				//semaphorePluginRetraitInboundPortURI,
				endPointServer,
				endPointClient);
		
		this.uri = uri;
		
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
    	
    	P5.addJeton();
    	
    	Function<String, String> fonction = input -> {
    	    System.out.println("Fonction activable de la transition: " + input);
    	    return "Transition activée: " + input;
    	};
    	
    	Transition T5 = new Transition(t5, (Function) fonction);
    	Transition T6 = new Transition(t6, (Function) fonction);
    	Transition T7 = new Transition(t7, (Function) fonction);
    	Transition T8 = new Transition(t8, (Function) fonction);
    	Transition T9 = new Transition(t9, (Function) fonction);
    	
    	T5.addPlaceEntree(P5);
    	T6.addPlaceSortie(P6);
    	T7.addPlaceEntree(P6);
    	T7.addPlaceSortie(P7);
    	T8.addPlaceEntree(P7);
    	T8.addPlaceSortie(P8);
    	T9.addPlaceEntree(P8);
    	T9.addPlaceSortie(P9);

		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P5);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P6);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P7);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P8);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addPlace((P) P9);
		
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(T5);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(T6);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(T7);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(T8);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).addTransition(T9);
	}
}
