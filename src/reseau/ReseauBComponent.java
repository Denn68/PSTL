package reseau;

import java.util.ArrayList;
import java.util.HashMap;
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
    	
    	updatingJetons.put(pc1, semJetonUriList.get(0));
    	updatingJetons.put(pc2, semJetonUriList.get(1));
    	updatingJetons.put(pc3, semJetonUriList.get(2));
    	updatingJetons.put(pc4, semJetonUriList.get(3));
		
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
    	
    	Transition T5 = new Transition(t5, (Function<String, String>) fonction);
    	Transition T6 = new Transition(t6, (Function<String, String>) fonction);
    	Transition T7 = new Transition(t7, (Function<String, String>) fonction);
    	Transition T8 = new Transition(t8, (Function<String, String>) fonction);
    	Transition T9 = new Transition(t9, (Function<String, String>) fonction);
    	
    	T5.addPlaceEntree(P5,1);
    	T6.addPlaceSortie(P6);
    	T7.addPlaceEntree(P6,1);
    	T7.addPlaceSortie(P7);
    	T8.addPlaceEntree(P7,1);
    	T8.addPlaceSortie(P8);
    	T9.addPlaceEntree(P8,1);
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
		
		
		System.out.println("Connexion entre réseau B et réseau PlaceCommune...");
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkSortiePlaceCommuneTransition("t5", pc2, semaphoreUpdatingAvailibility, updatingJetons.get(pc2));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkEntreePlaceCommuneTransition("t6", pc1, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc1));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkEntreePlaceCommuneTransition("t6", pc2, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc2));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkSortiePlaceCommuneTransition("t6", pc3, semaphoreUpdatingAvailibility, updatingJetons.get(pc3));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkEntreePlaceCommuneTransition("t8", pc4, 1, semaphoreUpdatingAvailibility, updatingJetons.get(pc4));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_B_PLUGIN_URI)).linkSortiePlaceCommuneTransition("t9", pc4, semaphoreUpdatingAvailibility, updatingJetons.get(pc4));
	}
}
