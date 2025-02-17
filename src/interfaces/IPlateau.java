package interfaces;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import classes.Jeton;
import classes.Place;
import classes.Transition;

public interface IPlateau {

	public void showPlateau();
	
    public ArrayList<Place> getPlaces();

    public ArrayList<Transition> getTransitions();

    public ArrayList<Jeton> getJetons();
    
    public void setPlaces(ArrayList<Place> places);

    public void setTransitions(ArrayList<Transition> transitions);

    public void setJetons(ArrayList<Jeton> jetons);

    public void activateTransition(Transition transition);

    public Set<Transition> update();

    public void randomTransition(int maxTransitions);

    public void manualTransition(Scanner scanner);
}
