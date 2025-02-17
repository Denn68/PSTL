package interfaces;

import java.util.ArrayList;

import classes.Place;

public interface ITransition {
	
	public int numId();

    public ArrayList<Place> getPlacesEntrees();

    public ArrayList<Place> getPlacesSorties();

}
