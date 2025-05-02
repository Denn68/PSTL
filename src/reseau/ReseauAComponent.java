package reseau;

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
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;

@OfferedInterfaces(offered = { ReseauCI.class})
@RequiredInterfaces(required = { ReseauCI.class})
public class ReseauAComponent<T, P>
extends ReseauComponent<T, P>
implements ReseauI<P>{
	
	public static final String		RESEAU_A_PLUGIN_URI = "reseau-a-plugin-uri";
	private String uri;
	
	@SuppressWarnings("unchecked")
	protected			ReseauAComponent(String uri,
			String reflectionInboundPortURI,
			String semaphoreUpdatingAvailibility,
			String semaphoreUpdatingJeton,
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
    	
    	updatingJetons.put(pc1, semJetonUriList.get(0));
    	updatingJetons.put(pc2, semJetonUriList.get(1));
    	updatingJetons.put(pc3, semJetonUriList.get(2));
    	updatingJetons.put(pc4, semJetonUriList.get(3));
		
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
    	
    	P1.addJeton();
    	
    	Function<String, String> fonction = input -> {
    	    System.out.println("Fonction activable de la transition: " + input);
    	    return "Transition activée: " + input;
    	};
    	
    	Transition T1 = new Transition(t1, (Function) fonction);
    	Transition T2 = new Transition(t2, (Function) fonction);
    	Transition T3 = new Transition(t3, (Function) fonction);
    	Transition T4 = new Transition(t4, (Function) fonction);
    	
    	T1.addPlaceEntree(P1);
    	T2.addPlaceSortie(P2);
    	T3.addPlaceEntree(P2);
    	T3.addPlaceSortie(P3);
    	T4.addPlaceEntree(P3);
    	T4.addPlaceSortie(P4);
	
	
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P1);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P2);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P3);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addPlace((P) P4);
		
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(T1);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(T2);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(T3);
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).addTransition(T4);
		
		System.out.println("Connexion entre réseau A et réseau PlaceCommune...");
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkSortiePlaceCommuneTransition("t1", pc1, semaphoreUpdatingAvailibility, updatingJetons.get(pc1));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkEntreePlaceCommuneTransition("t2", pc3, semaphoreUpdatingAvailibility, updatingJetons.get(pc3));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkEntreePlaceCommuneTransition("t3", pc4, semaphoreUpdatingAvailibility, updatingJetons.get(pc4));
		((ReseauPlugin<P>) this.getPlugin(RESEAU_A_PLUGIN_URI)).linkSortiePlaceCommuneTransition("t4", pc4, semaphoreUpdatingAvailibility, updatingJetons.get(pc4));
		
	}
	
}
