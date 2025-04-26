package classes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class Transition{
	/*
	private ArrayList<Place> placeEntrees;
    private ArrayList<Place> placeSorties;
	private ArrayList<PlaceCommune> placeCommuneEntrees;
    private ArrayList<PlaceCommune> placeCommuneSorties;
    private Map<PlaceCommune, Boolean> activable;
    private Map<PlaceCommune, Semaphore> updatingAvailability;
    private Map<PlaceCommune, Semaphore> updatingJetons;
    */
    private ArrayList<String> placeEntrees;
    private ArrayList<String> placeSorties;
	private ArrayList<String> placeCommuneEntrees;
    private ArrayList<String> placeCommuneSorties;
    private Map<String, Boolean> activable;
    private Map<String, String> updatingAvailability;
    private Map<String, String> updatingJetons;
    private String uri;
    private Function activableFunction;

    // Constructor
    public Transition(String uri, Function function) {
        this.placeEntrees = new ArrayList<String>();
        this.placeSorties = new ArrayList<String>();
        this.placeCommuneEntrees = new ArrayList<String>();
        this.placeCommuneSorties = new ArrayList<String>();
        this.activable = new HashMap<String, Boolean>();
        this.updatingJetons = new HashMap<String, String>();
        this.updatingAvailability = new HashMap<String, String>();
        this.uri = uri;
        this.setActivableFunction(function);
    }
   

    public String getUri() {
        return uri;
    }
    
    public void updateIsActivable(String place) {
    	boolean currentPlaceState = this.activable.get(place); 
    	this.activable.put(place, !currentPlaceState);
    }
    
    public boolean isActivable() {
    	for(String p : this.placeCommuneEntrees) {
    		if(this.activable.get(p) != true) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public ArrayList<String> getPlacesEntrees() {
        return this.placeEntrees;
    }
    
    public void addPlacesEntree(ArrayList<Place> entrees) {
    	for(Place p : entrees) {
    		this.placeEntrees.add(p.getUri());
    	}
    }
    
    public void addPlaceEntree(Place entree) {
    	this.placeEntrees.add(entree.getUri());
    	entree.addTransSortie(this);
    }

    public ArrayList<String> getPlacesSorties() {
        return this.placeSorties;
    }
    
    public void addPlacesSortie(ArrayList<Place> sorties) {
    	for(Place p : sorties) {
    		this.placeSorties.add(p.getUri());
    	}
    }
    
    public void addPlaceSortie(Place sortie) {
    	this.placeSorties.add(sortie.getUri());
    	sortie.addTransEntree(this);
    }
    
    public ArrayList<String> getPlacesCommuneEntrees() {
        return this.placeCommuneEntrees;
    }
    
    public ArrayList<String> getPlacesCommuneSorties() {
        return this.placeCommuneSorties;
    }
    
    public void addPlaceCommuneEntree(String entree, String updatingAvailability, String updatingJetons) {    
		this.placeCommuneEntrees.add(entree);
		this.activable.put(entree, true);
        this.updatingAvailability.put(entree, updatingAvailability);
        this.updatingJetons.put(entree, updatingJetons);
    }
    
    public void addPlaceCommuneSortie(String sortie, String updatingAvailability) {    
    	this.placeCommuneSorties.add(sortie);
		this.updatingAvailability.put(sortie, updatingAvailability);
    }


	public Function getActivableFunction() {
		return activableFunction;
	}


	public void setActivableFunction(Function activableFunction) {
		this.activableFunction = activableFunction;
	}
    
    /*@SuppressWarnings("unchecked")
	public void activateTransition() {
    	if (this.isActivable()) {
    		boolean skip = false;
    		for (String placeCommune : this.getPlacesCommuneEntrees()) {
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
		        this.activableFunction.apply(null);
    		} else {
    			System.out.println("La transition a été prise par un autre thread");
    		}
    	}
    	else {
    		System.out.println("La transition n'est pas activable, la place commune n'a pas de jeton");
    	}
    }*/
}