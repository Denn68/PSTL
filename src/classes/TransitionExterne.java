package classes;
import java.util.ArrayList;

import interfaces.ITransitionExterne;

public class TransitionExterne implements ITransitionExterne{
    private ArrayList<Place> placeEntre;
    private ArrayList<Place> placeSortie;
    private String uri;

    // Constructor
    public TransitionExterne(String uri) {
        this.placeEntre = new ArrayList<Place>();
        this.placeSortie = new ArrayList<Place>();
        this.uri = uri;
    }

    @Override
    public String getUri() {
        return uri;
    }
    
    @Override
    public ArrayList<Place> getPlacesEntrees() {
        return placeEntre;
    }
    
    @Override
    public void addPlaceEntree(Place entree) {
        this.placeEntre.add(entree);
    }

    @Override
    public ArrayList<Place> getPlacesSorties() {
        return placeSortie;
    }
    
    @Override
    public void addPlaceSortie(Place sortie) {
        this.placeSortie.add(sortie);
    }

}