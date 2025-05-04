package interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

import classes.Transition;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface ReseauCI<P>
extends OfferedCI,
RequiredCI,
ReseauI<P>{
	
	// Getters
	public String getUri() throws Exception;

    public Collection<P> getPlaces() throws Exception;

    public Collection<Transition> getTransitions() throws Exception;

    // Setters
    public void addPlace(P place) throws Exception;

    public void addTransition(Transition transition) throws Exception;

    public Set<Transition> update() throws Exception;
    
    public void showReseau() throws Exception;
    
    public void activeTransition(Transition tr) throws Exception;

    public void randomTransition() throws Exception;

    public void manualTransition(Scanner scanner) throws Exception;
    
    public void linkEntreePlaceCommuneTransition(
    		String transition,
    		String placeCommune,
    		int seuil,
    		String updatingAvailability,
    		String updatingJetons) throws Exception;
    
    public void linkSortiePlaceCommuneTransition(
    		String transition, 
    		String placeCommune,
    		String updatingAvailability,
    		String updatingJetons) throws Exception;

	public void updateTransitionsActivable(
			String uri,
			ArrayList<String> transSorties,
			int nbJeton) throws Exception;
    
}
