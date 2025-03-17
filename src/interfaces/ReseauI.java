package interfaces;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import classes.Transition;

public interface ReseauI<I, R, P> {
	
	// Getters
	public String getUri() throws Exception;

    public ArrayList<P> getPlaces() throws Exception;

    public ArrayList<Transition<I, R>> getTransitions() throws Exception;

    // Setters
    public void addPlace(P place) throws Exception;

    public void addTransition(Transition<I, R> transition) throws Exception;

    public Set<Transition<I, R>> update() throws Exception;
    
    public void showReseau() throws Exception;

    public void randomTransition() throws Exception;

    public void manualTransition(Scanner scanner) throws Exception;
}
