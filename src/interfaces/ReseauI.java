package interfaces;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import classes.Transition;

public interface ReseauI<P> {
	
	// Getters
	public String getUri() throws Exception;

    public ArrayList<P> getPlaces() throws Exception;

    public ArrayList<Transition> getTransitions() throws Exception;

    // Setters
    public void addPlace(P place) throws Exception;

    public void addTransition(Transition transition) throws Exception;

    public Set<Transition> update() throws Exception;
    
    public void showReseau() throws Exception;

    public void randomTransition() throws Exception;

    public void manualTransition(Scanner scanner) throws Exception;
    
    public void linkPlacesTransition(ArrayList<P> entrees, String t, ArrayList<P> sorties) throws Exception;
}
