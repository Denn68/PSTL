package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class Transition{

    private Map<String, Integer> placeEntrees;
    private ArrayList<String> placeSorties;
	private Map<String, Integer> placeCommuneEntrees;
    private ArrayList<String> placeCommuneSorties;
    private Map<String, Boolean> activable;
    private Map<String, String> updatingAvailability;
    private Map<String, String> updatingJetons;
    private String uri;
    private Function activableFunction;

    // Constructor
    public Transition(String uri, Function function) {
        this.placeEntrees = new HashMap<String, Integer>();
        this.placeSorties = new ArrayList<String>();
        this.placeCommuneEntrees = new HashMap<String, Integer>();
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
    
    public void updateIsActivable(String place, int nbJeton) {
    	//System.out.println(place + " a " + nbJeton + " et il faut " + placeCommuneEntrees.get(place) + " donc " + (nbJeton >= placeCommuneEntrees.get(place)));
    	boolean state = (nbJeton >= placeCommuneEntrees.get(place));
    	this.activable.put(place, state);
    }
    
    public boolean isActivable() {
    	for(String p : this.placeCommuneEntrees.keySet()) {
    		if(this.activable.get(p) != true) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public Map<String, Integer> getPlacesEntrees() {
        return this.placeEntrees;
    }
    
    public void addPlacesEntree(Map<String, Integer> entrees) {
    	this.placeEntrees.putAll(entrees);
    }
    
    public void addPlaceEntree(Place entree, int thresHold) {
    	this.placeEntrees.put(entree.getUri(), thresHold);
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
    
    public Map<String, Integer> getPlacesCommuneEntrees() {
        return this.placeCommuneEntrees;
    }
    
    public ArrayList<String> getPlacesCommuneSorties() {
        return this.placeCommuneSorties;
    }
    
    public void addPlaceCommuneEntree(String entree, int thresHold, String updatingAvailability, String updatingJetons) {    
		this.placeCommuneEntrees.put(entree, thresHold);
		this.activable.put(entree, false);
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
}