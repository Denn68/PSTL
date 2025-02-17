package classes;
import java.util.ArrayList;

import interfaces.ITransition;

public class Transition implements ITransition{
    private int nbJetonMax;
    private int numId;
    private ArrayList<Place> placeEntre;
    private ArrayList<Place> placeSortie;

    // Constructor
    public Transition(int nbJetonMax, int numId, ArrayList<Place> entree, ArrayList<Place> sortie) {
        this.nbJetonMax = nbJetonMax;
        this.numId = numId;
        this.placeEntre = entree;
        this.placeSortie = sortie;
    }

    @Override
    public int numId() {
        return numId;
    }

    @Override
    public ArrayList<Place> getPlacesEntrees() {
        return placeEntre;
    }

    @Override
    public ArrayList<Place> getPlacesSorties() {
        return placeSortie;
    }

}