package frontend;

import classes.Place;

public interface IFrontend {

	public void CreatePlace(String uri);
	
	public void LinkPlaces(Place place1, Place place2, String transitionUri);
	
	public void LinkPlaceCommuneEtReseau(Place place1, Place place2, String transitionUri);
	
	public void InitializePlace(String placeUri, int nbJeton);
}
