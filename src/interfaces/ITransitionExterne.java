package interfaces;

import java.util.ArrayList;

import classes.Place;

public interface ITransitionExterne {
	
	public String getUri();
	
	public ArrayList<Place> getPlacesEntrees();

    public ArrayList<Place> getPlacesSorties();
    
    public void addPlaceEntree(Place entree);
    
    public void addPlaceSortie(Place sortie);

}
