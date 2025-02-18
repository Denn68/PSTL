package classes;
import java.util.ArrayList;

public class TransitionExterne{
    private ArrayList<Place> placeEntre;
    private ArrayList<Place> placeSortie;
    private String uri;

    // Constructor
    public TransitionExterne(String uri) {
        this.placeEntre = new ArrayList<Place>();
        this.placeSortie = new ArrayList<Place>();
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
    
    public ArrayList<Place> getPlacesEntrees() {
        return placeEntre;
    }
    
    public void addPlaceEntree(Place entree) {
        this.placeEntre.add(entree);
    }

    public ArrayList<Place> getPlacesSorties() {
        return placeSortie;
    }
    
    public void addPlaceSortie(Place sortie) {
        this.placeSortie.add(sortie);
    }

}