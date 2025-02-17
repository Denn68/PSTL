package frontend;

import java.util.Scanner;

public interface IFrontend {

	public void CreatePlace(String uri);
	
	public void LinkPlaces(String placeUri1, String placeUri2, String transitionUri);
	
	public void LinkPlaceCommuneEtReseau(String placeUri1, String placeUri2, String transitionUri);
	
	public void InitializePlace(String placeUri, int nbJeton);
	
	public void showPlateau();
	
	public void randomTransition(int maxTransitions);
	
	public void manualTransition(Scanner scanner);
}
