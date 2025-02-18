package classes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Transition{
    private ArrayList<Place> placeEntrees;
    private ArrayList<Place> placeSorties;
    private ArrayList<PlaceCommune> placeCommuneEntrees;
    private ArrayList<PlaceCommune> placeCommuneSorties;
    private Map<PlaceCommune, Boolean> activable;
    private Map<PlaceCommune, Semaphore> semaphores;
    private String uri;

    // Constructor
    public Transition(String uri) {
        this.placeEntrees = new ArrayList<Place>();
        this.placeSorties = new ArrayList<Place>();
        this.placeCommuneEntrees = new ArrayList<PlaceCommune>();
        this.placeCommuneSorties = new ArrayList<PlaceCommune>();
        this.activable = new HashMap<PlaceCommune, Boolean>();
        this.semaphores = new HashMap<PlaceCommune, Semaphore>();
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
    
    public void updateIsActivable(PlaceCommune place) {
    	boolean currentPlaceState = this.activable.get(place); 
    	this.activable.put(place, !currentPlaceState);
    }
    
    public boolean isActivable() {
    	for(PlaceCommune p : this.placeCommuneEntrees) {
    		if(this.activable.get(p) != true) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public ArrayList<Place> getPlacesEntrees() {
        return this.placeEntrees;
    }
    
    public void addPlaceEntree(Place entree) {
        this.placeEntrees.add(entree);
    }

    public ArrayList<Place> getPlacesSorties() {
        return this.placeSorties;
    }
    
    public void addPlaceSortie(Place sortie) {
        this.placeSorties.add(sortie);
    }
    
    public ArrayList<PlaceCommune> getPlacesCommuneEntrees() {
        return this.placeCommuneEntrees;
    }
    
    public void addPlaceCommuneEntree(PlaceCommune entree) {
        this.placeCommuneEntrees.add(entree);
        this.activable.put(entree, true);
    }

    public ArrayList<PlaceCommune> getPlacesCommuneSorties() {
        return this.placeCommuneSorties;
    }
    
    public void addPlaceCommuneSortie(PlaceCommune sortie) {
        this.placeCommuneSorties.add(sortie);
    }
    
    public void activateTransition() {
    	if (this.isActivable()) {
	        for (Place place : this.getPlacesEntrees())
	        	place.retrieveJeton();
	        for (PlaceCommune placeCommune : this.getPlacesCommuneEntrees()) {
	        	placeCommune.retrieveJeton();
	        	try {
					this.semaphores.get(placeCommune).acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
	        for (Place place : this.getPlacesSorties())
	        	place.addJeton();
	        for (PlaceCommune placeCommune : this.getPlacesCommuneSorties()) {
	        	placeCommune.addJeton();
	        	this.semaphores.get(placeCommune).release();
	        }
	        	
	        
    	}
    }
}