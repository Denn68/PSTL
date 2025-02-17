package Backend;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import classes.Jeton;
import classes.Place;
import classes.Transition;
import classes.TransitionExterne;

public interface IBackend {

	public void CreatePlace(String uri);
	
	public void LinkPlaces(String placeUri1, String placeUri2, String transitionUri);
	
	public void LinkPlaceCommuneEtReseau(String placeUri1, String placeUri2, String transitionUri);
	
	public void InitializePlace(String placeUri, int nbJeton);
	

	public void addTransSortie(String uri, Transition transition);
	public void addTransEntree(String uri, Transition transition);
	
	public void addTransExterneSortie(String uri, TransitionExterne transitionExterne);
	
	public void addTransExterneEntree(String uri, TransitionExterne transitionExterne);
	
	// PARTIE PLATEAU
	
	public void showPlateau();
	
    public ArrayList<Place> getPlaces();

    public ArrayList<Transition> getTransitions();
    
    public ArrayList<TransitionExterne> getTransitionsExternes();

    public ArrayList<Jeton> getJetons();
    
    /*public void setPlaces(ArrayList<Place> places);

    public void setTransitions(ArrayList<Transition> transitions);

    public void setJetons(ArrayList<Jeton> jetons);*/

    public void activateTransition(Transition transition);
    
    public void activateTransition(TransitionExterne transitionExterne);

    public Set<Object> update();

    public void randomTransition(int maxTransitions);

    public void manualTransition(Scanner scanner);
}
