package frontend;

import java.util.ArrayList;
import java.util.function.Function;

import backend.Backend;

public class Frontend {
	
	// Référence au backend pour déléguer les opérations liées au réseau de Petri
	private Backend backend;
	
	// Constructeur : initialise le frontend avec une instance du backend
	public Frontend(Backend backend) {
    	this.backend = backend;
    }
    
	// Crée un nouveau réseau de Petri identifié par son URI
	public void CreateNetwork(String uri) {
		this.backend.CreateNetwork(uri);
	}
	
	// Crée une place spécifique dans un réseau donné
	public void CreatePlace(String reseauUri, String placeUri) {
		this.backend.CreatePlace(reseauUri, placeUri);
	}
	
	// Crée une place commune (partagée entre plusieurs réseaux)
	public void CreatePlaceCommune(String placeUri) {
		this.backend.CreatePlaceCommune(placeUri);
	}
	
	// Crée plusieurs places dans un réseau donné
	public void CreatePlaces(String reseauUri, ArrayList<String> placeUri) {
		for (String uri : placeUri) {
			this.CreatePlace(reseauUri, uri);
		}
	}
	
	// Crée plusieurs places communes
	public void CreatePlacesCommunes(ArrayList<String> placeUri) {
		for (String uri : placeUri) {
			this.CreatePlaceCommune(uri);
		}
	}

	// Lie des places d’entrée et de sortie à une transition dans un réseau donné
	// La transition est associée à une fonction activable (fonction de garde)
	public void LinkPlaces(String reseauUri, ArrayList<String> listOfEnterPlaceUri, ArrayList<String> listOfExitPlaceUri, String transitionUri, Function <String, String> activableFunction) {
		this.backend.LinkPlaces(reseauUri, listOfEnterPlaceUri, listOfExitPlaceUri, transitionUri, activableFunction);
	}

	// Initialise une place d’un réseau avec un nombre de jetons donné
	public void InitializePlace(String reseauUri, String placeUri, int nbJeton) {
		this.backend.InitializePlace(reseauUri, placeUri, nbJeton);
	}
	
	// Initialise une place commune avec un nombre de jetons donné
	public void InitializePlaceCommune(String placeUri, int nbJeton) {
		this.backend.InitializePlaceCommune(placeUri, nbJeton);
	}
	
	// Démarre l'exécution des réseaux de Petri
	public void startPetriNetwork() {
		this.backend.startPetriNetwork();
	}
}
