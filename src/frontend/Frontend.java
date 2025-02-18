package frontend;


import java.util.ArrayList;

import backend.Backend;

public class Frontend{
	
	public Frontend(Backend backend) {
    	this.backend = backend;
    }
    
	
	private Backend backend;
	
	public void CreateNetwork(String uri) {
		this.backend.CreateNetwork(uri);
	}
	public void CreatePlace(String placeUri, String reseauUri) {
		this.backend.CreatePlace(placeUri, reseauUri);
	}

	public void LinkPlaces(String reseauUri, ArrayList<String> listOfEnterPlaceUri, ArrayList<String> listOfExitPlaceUri, String transitionUri) {
		this.backend.LinkPlaces(reseauUri, listOfEnterPlaceUri, listOfExitPlaceUri, transitionUri);
	}

	public void InitializePlace(String reseauUri, String placeUri, int nbJeton) {
		this.backend.InitializePlace(reseauUri, placeUri, nbJeton);
	}
	
	public void startPetriNetwork() {
		this.backend.startPetriNetwork();
	}

}
