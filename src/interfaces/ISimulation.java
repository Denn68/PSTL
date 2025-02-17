package interfaces;

import java.util.ArrayList;

import classes.Jeton;
import classes.Place;
import classes.Plateau;
import classes.Transition;

public interface ISimulation {
	public Plateau createNetwork(ArrayList<Place> places,
            ArrayList<Transition> transitions,
            ArrayList<Jeton> jetons);
}
