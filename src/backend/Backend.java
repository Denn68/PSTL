package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import classes.Place;
import classes.PlaceCommune;
import classes.Reseau;
import classes.Transition;

public class Backend{
	Map<String, Reseau> reseaux;
    Map<String, PlaceCommune> placesCommunes;

	public Backend() {
		this.reseaux = new HashMap<String, Reseau>();
		this.placesCommunes = new HashMap<String, PlaceCommune>();
    }
	public void CreateNetwork(String uri, Function<String, String> activableFunction) {
		this.reseaux.put(uri, new Reseau(uri, activableFunction));
	}

	public void CreatePlace(String reseauUri, String placeUri) {
		this.reseaux.get(reseauUri).addPlace(new Place(placeUri));
	}
	
	public void CreatePlaceCommune(String placeUri) {
		this.placesCommunes.put(placeUri, new PlaceCommune(placeUri));
	}

	public void LinkPlaces(String reseauUri, ArrayList<String> listOfEnterPlaceUri, ArrayList<String> listOfExitPlaceUri, String transitionUri) {
		boolean notFound = false;
		String notFoundUri = "";
		ArrayList<Place> placeEntrees = new ArrayList<Place>();
		ArrayList<PlaceCommune> placeCommuneEntrees = new ArrayList<PlaceCommune>();
		ArrayList<Place> placeSorties = new ArrayList<Place>();
		ArrayList<PlaceCommune> placeCommuneSorties = new ArrayList<PlaceCommune>();
		for (String placeUri : listOfEnterPlaceUri) {
			notFound = true;
			for (Place p : this.reseaux.get(reseauUri).getPlaces()) {
				if(p.getUri().equals(placeUri)) {
					notFound = false;
					placeEntrees.add(p);
				}
			}
			if(notFound) {
				if(this.placesCommunes.containsKey(placeUri)) {
						notFound = false;
						placeCommuneEntrees.add(this.placesCommunes.get(placeUri));
				} 
				else {
					notFoundUri = placeUri;
					break;
				}			
			}
		}
		if(!notFound) {
			for (String placeUri : listOfExitPlaceUri) {
				notFound = true;
				for (Place p : this.reseaux.get(reseauUri).getPlaces()) {
					if(p.getUri().equals(placeUri)) {
						notFound = false;
						placeSorties.add(p);
					}
				}
				if(notFound) {
					if(this.placesCommunes.containsKey(placeUri)) {
							notFound = false;
							placeCommuneSorties.add(this.placesCommunes.get(placeUri));
					} 
					else {
						notFoundUri = placeUri;
						break;
					}			
				}
			}
			if(!notFound) {
				Transition<String> transition = new Transition<>(transitionUri);
				transition.addPlacesEntree(placeEntrees);
				transition.addPlacesSortie(placeSorties);
				transition.addPlacesCommuneEntree(placeCommuneEntrees);
				transition.addPlacesCommuneSortie(placeCommuneSorties);
				for(Place p: placeEntrees) {
					p.addTransSortie(transition);
				}
				for(Place p: placeSorties) {
					p.addTransEntree(transition);
				}
				for(PlaceCommune p: placeCommuneEntrees) {
					p.addTransSortie(transition);
				}
				for(PlaceCommune p: placeCommuneSorties) {
					p.addTransEntree(transition);
				}
				this.reseaux.get(reseauUri).addTransition(transition);
			}
			else {
				System.out.printf("L'uri %s n'a pas été trouvée", notFoundUri);
			}
		}
		else {
			System.out.printf("L'uri %s n'a pas été trouvée", notFoundUri);
		}
	}

	public void InitializePlace(String reseauUri, String placeUri, int nbJeton) {
		boolean found = false;
		for (Place p : this.reseaux.get(reseauUri).getPlaces()) {
			if(p.getUri() == placeUri) {
				p.setNbJeton(nbJeton);
				found = true;
			}
		}
		if(!found) {
			System.out.println("Il n'existe aucune place avec cette uri");
		}
	}
	
	public void InitializePlaceCommune(String placeUri, int nbJeton) {
		if(this.placesCommunes.containsKey(placeUri)) {
			this.placesCommunes.get(placeUri).setNbJeton(nbJeton);
		}
		else {
			System.out.println("Il n'existe aucune place commune avec cette uri");
		}
	}
	
	public void startPetriNetwork() {
		for(PlaceCommune p : this.placesCommunes.values()) {
			p.start();
		}
		for(Reseau r: this.reseaux.values()) {
			r.start();
		}
	}
}
