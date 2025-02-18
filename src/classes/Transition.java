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
    private Map<PlaceCommune, Semaphore> updatingAvailability;
    private Map<PlaceCommune, Semaphore> updatingJetons;
    private String uri;

    // Constructor
    public Transition(String uri) {
        this.placeEntrees = new ArrayList<Place>();
        this.placeSorties = new ArrayList<Place>();
        this.placeCommuneEntrees = new ArrayList<PlaceCommune>();
        this.placeCommuneSorties = new ArrayList<PlaceCommune>();
        this.activable = new HashMap<PlaceCommune, Boolean>();
        this.updatingJetons = new HashMap<PlaceCommune, Semaphore>();
        this.updatingAvailability = new HashMap<PlaceCommune, Semaphore>();
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
    
    public void addPlacesEntree(ArrayList<Place> entrees) {
    	for(Place p : entrees) {
    		this.placeEntrees.add(p);
    	}
    }

    public ArrayList<Place> getPlacesSorties() {
        return this.placeSorties;
    }
    
    public void addPlacesSortie(ArrayList<Place> sorties) {
    	for(Place p : sorties) {
    		this.placeEntrees.add(p);
    	}
    }
    
    public ArrayList<PlaceCommune> getPlacesCommuneEntrees() {
        return this.placeCommuneEntrees;
    }
    
    public void addPlacesCommuneEntree(ArrayList<PlaceCommune> entrees) {
    	for(PlaceCommune p: entrees) {        
    		this.placeCommuneEntrees.add(p);
    		this.activable.put(p, true);
            this.updatingAvailability.put(p, p.getUpdatingAvailability());
            this.updatingJetons.put(p, p.getUpdatingJetons());
    	}
    }

    public ArrayList<PlaceCommune> getPlacesCommuneSorties() {
        return this.placeCommuneSorties;
    }
    
    public void addPlacesCommuneSortie(ArrayList<PlaceCommune> sorties) {
    	for(PlaceCommune p: sorties) {        
    		this.placeCommuneSorties.add(p);
    		this.updatingAvailability.put(p, p.getUpdatingAvailability());
    	}
    }
    
    public void activateTransition() {
    	if (this.isActivable()) {
    		boolean skip = false;
    		System.out.println(this.getPlacesCommuneEntrees().size());
    		for (PlaceCommune placeCommune : this.getPlacesCommuneEntrees()) {
				skip = this.updatingJetons.get(placeCommune).tryAcquire();
	        }
    		if(!skip) {
    			System.out.printf("Not Skip - %s\n", uri);
				for (PlaceCommune placeCommune : this.getPlacesCommuneEntrees()) {
					placeCommune.retrieveJeton();
					this.updatingAvailability.get(placeCommune).release();
					this.updatingJetons.get(placeCommune).release();
		        }
		        for (Place place : this.getPlacesEntrees())
		        	place.retrieveJeton();
		        for (Place place : this.getPlacesSorties())
		        	place.addJeton();
		        for (PlaceCommune placeCommune : this.getPlacesCommuneSorties()) {
		        	placeCommune.addJeton();
		        	this.updatingAvailability.get(placeCommune).release();
	        	}
    		} else {
    			System.out.println("La transition a été prise par un autre thread");
    		}
    	}
    	else {
    		System.out.println("La transition n'est pas activable, la place commune n'a pas de jeton");
    	}
    }
}