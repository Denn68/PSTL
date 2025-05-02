package reseau;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;

import classes.Place;
import classes.Transition;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.exceptions.ConnectionException;
import interfaces.ReseauCI;
import interfaces.ReseauI;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import test.CVM;

@OfferedInterfaces(offered = { ReseauCI.class})
@RequiredInterfaces(required = { ReseauCI.class})
public class ReseauAComponent<T, P>
extends ReseauClientComponent<T, P>
implements ReseauI<P>{
	
	private String uri;
	
	@SuppressWarnings("unchecked")
	protected			ReseauAComponent(String uri,
			String reflectionInboundPortURI,
			ReseauPlaceCommuneEndpoint endPointClient,
			ReseauEndpoint endPointServer) throws Exception
	{
		
		super(uri,
				CVM.RESEAU_A_PLUGIN_URI,
				reflectionInboundPortURI,
				endPointClient,
				endPointServer);
		
		this.uri = uri;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void init() {
		try {
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
	
		
			
			this.getServerRef().addPlace((P) P1);
			
			this.getServerRef().addPlace((P) P2);
			this.getServerRef().addPlace((P) P3);
			this.getServerRef().addPlace((P) P4);
			
			this.getServerRef().addTransition(T1);
			this.getServerRef().addTransition(T2);
			this.getServerRef().addTransition(T3);
			this.getServerRef().addTransition(T4);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
