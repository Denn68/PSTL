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
public class Conveyor<T, P>
extends ReseauComponent<T, P>
implements ReseauI<P>{
	
	public static final String		CONVOYEUR_PLUGIN_URI = "convoyeur-plugin-uri";
	private String uri;
	
	@SuppressWarnings("unchecked")
	protected			Conveyor(String uri,
			String reflectionInboundPortURI,
			String semaphoreUpdatingAvailibility,
			ArrayList<String> semJetonUriList,
			ReseauEndpoint endPointServer,
			ReseauPlaceCommuneEndpoint endPointClient) throws Exception
	{
		
		super(uri,
				reflectionInboundPortURI,
				CONVOYEUR_PLUGIN_URI,
				endPointServer,
				endPointClient);
		
		this.uri = uri;
		
		HashMap<String, String> updatingJetons = new HashMap<String, String>();
		
		String pc5 = "pc5";
    	String pc6 = "pc6";
    	
    	updatingJetons.put(pc5, semJetonUriList.get(4));
    	updatingJetons.put(pc6, semJetonUriList.get(5));
    	
    	//System.out.println("[" + this.uri + "] Updating jetons: " + updatingJetons);
		
		String p1 = "p1";
    	String p2 = "p2";
    	
    	Place P1 = new Place(p1);
    	Place P2 = new Place(p2);
    	
    	P1.addJeton();
    	
    	String tCONV = "tCONV";
    	
    	  	
    	
    	Function<Void, Void> move = input -> {
    	    System.out.println("move");
    	    return null;
    	};
    	
    	Transition TCONV = new Transition(tCONV, (Function<Void, Void>) move);
    	
    	TCONV.addPlaceEntree(P1,1);
    	TCONV.addPlaceSortie(P2);
	
	
		((ReseauPlugin<P>) this.getPlugin(CONVOYEUR_PLUGIN_URI)).addPlace((P) P1);
		((ReseauPlugin<P>) this.getPlugin(CONVOYEUR_PLUGIN_URI)).addPlace((P) P2);
		
		((ReseauPlugin<P>) this.getPlugin(CONVOYEUR_PLUGIN_URI)).addTransition(TCONV);
		
		System.out.println("Connexion entre convoyeur et réseau PlaceCommune...");
		((ReseauPlugin<P>) this.getPlugin(CONVOYEUR_PLUGIN_URI)).linkEntreePlaceCommuneTransition("tCONV", pc5, 2, semaphoreUpdatingAvailibility, updatingJetons.get(pc5));
		((ReseauPlugin<P>) this.getPlugin(CONVOYEUR_PLUGIN_URI)).linkSortiePlaceCommuneTransition("tCONV", pc6, semaphoreUpdatingAvailibility, updatingJetons.get(pc6));
		
	}
	
}
