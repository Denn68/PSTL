package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import classes_sansBCM.Place;
import classes_sansBCM.PlaceCommune;
import classes_sansBCM.Reseau;
import classes_sansBCM.Transition;

public class Backend {
	
	// Map des réseaux de Petri : chaque URI identifie un réseau
	Map<String, Reseau<String, String>> reseaux;
	
	// Map des places communes (partagées entre réseaux) identifiées par leur URI
    Map<String, PlaceCommune> placesCommunes;

	// Constructeur : initialise les maps des réseaux et des places communes
	public Backend() {
		this.reseaux = new HashMap<String, Reseau<String, String>>();
		this.placesCommunes = new HashMap<String, PlaceCommune>();
    }

	// Crée un nouveau réseau de Petri et l’associe à son URI
	public void CreateNetwork(String uri) {
		this.reseaux.put(uri, new Reseau<String, String>(uri));
	}

	// Crée une place dans un réseau donné
	public void CreatePlace(String reseauUri, String placeUri) {
		this.reseaux.get(reseauUri).addPlace(new Place(placeUri));
	}
	
	// Crée une place commune (globale à plusieurs réseaux)
	public void CreatePlaceCommune(String placeUri) {
		this.placesCommunes.put(placeUri, new PlaceCommune(placeUri));
	}

	// Crée une transition entre les places d’un réseau, en prenant en compte les places communes et la fonction de garde
	public void LinkPlaces(String reseauUri, ArrayList<String> listOfEnterPlaceUri, ArrayList<String> listOfExitPlaceUri, String transitionUri, Function<String, String> activablefunction) {
		boolean notFound = false; // indicateur de place introuvable
		String notFoundUri = ""; // stocke l'URI de la place non trouvée

		// Listes de places normales et communes en entrée et en sortie
		ArrayList<Place> placeEntrees = new ArrayList<Place>();
		ArrayList<PlaceCommune> placeCommuneEntrees = new ArrayList<PlaceCommune>();
		ArrayList<Place> placeSorties = new ArrayList<Place>();
		ArrayList<PlaceCommune> placeCommuneSorties = new ArrayList<PlaceCommune>();

		// Recherche des places d'entrée
		for (String placeUri : listOfEnterPlaceUri) {
			notFound = true;
			for (Place p : this.reseaux.get(reseauUri).getPlaces()) {
				if(p.getUri().equals(placeUri)) {
					notFound = false;
					placeEntrees.add(p);
				}
			}
			if(notFound) {
				if(this.placesCommunes.containsKey(placeUri)) {
					notFound = false;
					placeCommuneEntrees.add(this.placesCommunes.get(placeUri));
				} else {
					notFoundUri = placeUri;
					break;
				}
			}
		}

		// Si toutes les places d'entrée sont valides, on traite les sorties
		if(!notFound) {
			for (String placeUri : listOfExitPlaceUri) {
				notFound = true;
				for (Place p : this.reseaux.get(reseauUri).getPlaces()) {
					if(p.getUri().equals(placeUri)) {
						notFound = false;
						placeSorties.add(p);
					}
				}
				if(notFound) {
					if(this.placesCommunes.containsKey(placeUri)) {
						notFound = false;
						placeCommuneSorties.add(this.placesCommunes.get(placeUri));
					} else {
						notFoundUri = placeUri;
						break;
					}
				}
			}

			// Si toutes les places sont valides, on crée et connecte la transition
			if(!notFound) {
				Transition<String, String> transition = new Transition<String, String>(transitionUri, activablefunction);
				transition.addPlacesEntree(placeEntrees);
				transition.addPlacesSortie(placeSorties);
				transition.addPlacesCommuneEntree(placeCommuneEntrees);
				transition.addPlacesCommuneSortie(placeCommuneSorties);

				// Mise à jour des places avec la transition associée
				for(Place p: placeEntrees) {
					p.addTransSortie(transition);
				}
				for(Place p: placeSorties) {
					p.addTransEntree(transition);
				}
				for(PlaceCommune p: placeCommuneEntrees) {
					p.addTransSortie(transition);
				}
				for(PlaceCommune p: placeCommuneSorties) {
					p.addTransEntree(transition);
				}

				// Ajout de la transition au réseau
				this.reseaux.get(reseauUri).addTransition(transition);
			} else {
				System.out.printf("L'uri %s n'a pas été trouvée", notFoundUri);
			}
		} else {
			System.out.printf("L'uri %s n'a pas été trouvée", notFoundUri);
		}
	}

	// Initialise une place d’un réseau avec un nombre de jetons
	public void InitializePlace(String reseauUri, String placeUri, int nbJeton) {
		boolean found = false;
		for (Place p : this.reseaux.get(reseauUri).getPlaces()) {
			if(p.getUri() == placeUri) {
				p.setNbJeton(nbJeton);
				found = true;
			}
		}
		if(!found) {
			System.out.println("Il n'existe aucune place avec cette uri");
		}
	}
	
	// Initialise une place commune avec un nombre de jetons
	public void InitializePlaceCommune(String placeUri, int nbJeton) {
		if(this.placesCommunes.containsKey(placeUri)) {
			this.placesCommunes.get(placeUri).setNbJeton(nbJeton);
		}
		else {
			System.out.println("Il n'existe aucune place commune avec cette uri");
		}
	}
	
	// Démarre l'exécution des réseaux de Petri et des places communes (threads)
	public void startPetriNetwork() {
		for(PlaceCommune p : this.placesCommunes.values()) {
			p.start();
		}
		for(Reseau<?, ?> r : this.reseaux.values()) {
			r.start();
		}
	}
}
