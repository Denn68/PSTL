package frontend;


import java.util.Scanner;

import Backend.IBackend;

public class Frontend 
implements IFrontend{
	
	public Frontend(IBackend backend) {
    	this.backend = backend;
    }
    
	
	private IBackend backend;
	
	
	@Override
	public void CreatePlace(String uri) {
		this.backend.CreatePlace(uri);
	}

	@Override
	public void LinkPlaces(String placeUri1, String placeUri2, String transitionUri) {
		this.backend.LinkPlaces(placeUri1, placeUri2, transitionUri);
	}

	@Override
	public void LinkPlaceCommuneEtReseau(String placeUri1, String placeUri2, String transitionUri) {
		this.backend.LinkPlaceCommuneEtReseau(placeUri1, placeUri2, transitionUri);
	}

	@Override
	public void InitializePlace(String placeUri, int nbJeton) {
		this.backend.InitializePlace(placeUri, nbJeton);
		
	}
	
	@Override
	public void showPlateau() {
		this.backend.showPlateau();
	}
	
	@Override
    public void randomTransition(int maxTransitions) {
		this.backend.randomTransition(maxTransitions);
	}
	
	@Override
    public void manualTransition(Scanner scanner) {
		this.backend.manualTransition(scanner);
	}

}
