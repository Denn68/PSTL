package frontend;


import Backend.IBackend;
import classes.Place;

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
	public void LinkPlaces(Place place1, Place place2, String transitionUri) {
		this.backend.LinkPlaces(place1, place2, transitionUri);
	}

	@Override
	public void LinkPlaceCommuneEtReseau(Place place1, Place place2, String transitionUri) {
		this.backend.LinkPlaceCommuneEtReseau(place1, place2, transitionUri);
	}

	@Override
	public void InitializePlace(String placeUri, int nbJeton) {
		this.backend.InitializePlace(placeUri, nbJeton);
		
	}

}
