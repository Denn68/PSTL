package Backend;

import classes.Place;
import classes.Plateau;
import classes.Transition;
import classes.TransitionExterne;

public class Backend 
implements IBackend{

	public Backend() {
    	this.petriNetwork = new Plateau();
    }
	
	Plateau petriNetwork;
	@Override
	public void CreatePlace(String uri) {
		Place place = new Place(uri);
		petriNetwork.addPlace(place);
	}

	@Override
	public void LinkPlaces(Place place1, Place place2, String transitionUri) {
		Transition transition = new Transition(transitionUri);
		transition.addPlaceEntree(place1);
		transition.addPlaceSortie(place2);
		place1.addTransSortie(transition);
		place2.addTransEntree(transition);
	}

	@Override
	public void LinkPlaceCommuneEtReseau(Place place1, Place place2, String transitionUri) {
		TransitionExterne transition = new TransitionExterne(transitionUri);
		transition.addPlaceEntree(place1);
		transition.addPlaceSortie(place2);
		place1.addTransExterneSortie(transition);
		place2.addTransExterneEntree(transition);
		
	}

	@Override
	public void InitializePlace(String placeUri, int nbJeton) {
		this.petriNetwork.initializePlace(placeUri, nbJeton);
		
	}

}
