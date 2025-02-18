package frontend;


import java.util.Scanner;

import backend.Backend;

public class Frontend{
	
	public Frontend(Backend backend) {
    	this.backend = backend;
    }
    
	
	private Backend backend;
	
	
	public void CreatePlace(String uri) {
		this.backend.CreatePlace(uri);
	}

	public void LinkPlaces(String placeUri1, String placeUri2, String transitionUri) {
		this.backend.LinkPlaces(placeUri1, placeUri2, transitionUri);
	}

	public void LinkPlaceCommuneEtReseau(String placeUri1, String placeUri2, String transitionUri) {
		this.backend.LinkPlaceCommuneEtReseau(placeUri1, placeUri2, transitionUri);
	}

	public void InitializePlace(String placeUri, int nbJeton) {
		this.backend.InitializePlace(placeUri, nbJeton);
		
	}
	
	public void showPlateau() {
		this.backend.showPlateau();
	}
	
    public void randomTransition(int maxTransitions) {
		this.backend.randomTransition(maxTransitions);
	}
	
    public void manualTransition(Scanner scanner) {
		this.backend.manualTransition(scanner);
	}

}
