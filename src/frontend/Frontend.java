package frontend;


import java.util.ArrayList;
import java.util.function.Function;

import backend.Backend;

public class Frontend{
	
	public Frontend(Backend backend) {
    	this.backend = backend;
    }
    
	
	private Backend backend;
	
	public void CreateNetwork(String uri) {
		this.backend.CreateNetwork(uri);
	}
	
	public void CreatePlace(String reseauUri, String placeUri) {
		this.backend.CreatePlace(reseauUri, placeUri);
	}
	
	public void CreatePlaceCommune(String placeUri) {
		this.backend.CreatePlaceCommune(placeUri);
	}
	
	public void CreatePlaces(String reseauUri, ArrayList<String> placeUri) {
		for (String uri : placeUri) {
			this.CreatePlace(reseauUri, uri);
		}
	}
	
	public void CreatePlacesCommunes(ArrayList<String> placeUri) {
		for (String uri : placeUri) {
			this.CreatePlaceCommune(uri);
		}
	}

	public void LinkPlaces(String reseauUri, ArrayList<String> listOfEnterPlaceUri, ArrayList<String> listOfExitPlaceUri, String transitionUri, Function <String, String> activableFunction) {
		this.backend.LinkPlaces(reseauUri, listOfEnterPlaceUri, listOfExitPlaceUri, transitionUri, activableFunction);
	}

	public void InitializePlace(String reseauUri, String placeUri, int nbJeton) {
		this.backend.InitializePlace(reseauUri, placeUri, nbJeton);
	}
	
	public void InitializePlaceCommune(String placeUri, int nbJeton) {
		this.backend.InitializePlaceCommune(placeUri, nbJeton);
	}
	
	public void startPetriNetwork() {
		this.backend.startPetriNetwork();
	}

}
