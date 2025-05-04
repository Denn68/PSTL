package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import classes.Place;

// Supprime les warnings liés à l'utilisation de types bruts (non paramétrés)
@SuppressWarnings("rawtypes")
public class Transition {

    // Places simples en entrée, avec le seuil minimum de jetons requis
    private Map<String, Integer> placeEntrees;

    // URI des places simples en sortie
    private ArrayList<String> placeSorties;

    // Places communes en entrée, avec seuil de jetons requis
    private Map<String, Integer> placeCommuneEntrees;

    // URI des places communes en sortie
    private ArrayList<String> placeCommuneSorties;

    // Indique pour chaque place commune si la condition d'activation est remplie
    private Map<String, Boolean> activable;

    // Map des URIs des places vers les URI de leurs sémaphores de disponibilité
    private Map<String, String> updatingAvailability;

    // Map des URIs des places vers les URI de leurs sémaphores de contrôle des jetons
    private Map<String, String> updatingJetons;

    // Identifiant unique de la transition
    private String uri;

    // Fonction utilisée pour déterminer dynamiquement si la transition est activable
    private Function activableFunction;

    // Constructeur
    public Transition(String uri, Function function) {
        this.placeEntrees = new HashMap<String, Integer>();
        this.placeSorties = new ArrayList<String>();
        this.placeCommuneEntrees = new HashMap<String, Integer>();
        this.placeCommuneSorties = new ArrayList<String>();
        this.activable = new HashMap<String, Boolean>();
        this.updatingJetons = new HashMap<String, String>();
        this.updatingAvailability = new HashMap<String, String>();
        this.uri = uri;
        this.setActivableFunction(function);
    }

    // Renvoie l'identifiant de la transition
    public String getUri() {
        return uri;
    }

    // Met à jour le statut d'activabilité pour une place commune d'entrée donnée
    public void updateIsActivable(String place, int nbJeton) {
        boolean state = (nbJeton >= placeCommuneEntrees.get(place));
        this.activable.put(place, state);
    }

    // Vérifie si la transition est activable (toutes les places communes d'entrée doivent être activables)
    public boolean isActivable() {
        for (String p : this.placeCommuneEntrees.keySet()) {
            if (this.activable.get(p) != true) {
                return false;
            }
        }
        return true;
    }

    // Renvoie la map des places simples d'entrée avec leurs seuils
    public Map<String, Integer> getPlacesEntrees() {
        return this.placeEntrees;
    }

    // Ajoute plusieurs places d'entrée (fusionne avec celles déjà présentes)
    public void addPlacesEntree(Map<String, Integer> entrees) {
        this.placeEntrees.putAll(entrees);
    }

    // Ajoute une seule place d’entrée et enregistre la transition dans la place (liaison bidirectionnelle)
    public void addPlaceEntree(Place entree, int thresHold) {
        this.placeEntrees.put(entree.getUri(), thresHold);
        entree.addTransSortie(this); // La transition est une sortie de la place
    }

    // Renvoie les URI des places simples en sortie
    public ArrayList<String> getPlacesSorties() {
        return this.placeSorties;
    }

    // Ajoute plusieurs places de sortie à partir d’une liste
    public void addPlacesSortie(ArrayList<Place> sorties) {
        for (Place p : sorties) {
            this.placeSorties.add(p.getUri());
        }
    }

    // Ajoute une seule place de sortie et enregistre la transition comme entrée de cette place
    public void addPlaceSortie(Place sortie) {
        this.placeSorties.add(sortie.getUri());
        sortie.addTransEntree(this);
    }

    // Renvoie la map des places communes en entrée avec leur seuil de jetons
    public Map<String, Integer> getPlacesCommuneEntrees() {
        return this.placeCommuneEntrees;
    }

    // Renvoie les URI des places communes en sortie
    public ArrayList<String> getPlacesCommuneSorties() {
        return this.placeCommuneSorties;
    }

    // Ajoute une place commune en entrée avec son seuil et les URIs de ses sémaphores
    public void addPlaceCommuneEntree(String entree, int thresHold, String updatingAvailability, String updatingJetons) {
        this.placeCommuneEntrees.put(entree, thresHold);
        this.activable.put(entree, false); // Par défaut, la place n'est pas activable
        this.updatingAvailability.put(entree, updatingAvailability);
        this.updatingJetons.put(entree, updatingJetons);
    }

    // Ajoute une place commune en sortie avec son sémaphore de disponibilité
    public void addPlaceCommuneSortie(String sortie, String updatingAvailability) {
        this.placeCommuneSorties.add(sortie);
        this.updatingAvailability.put(sortie, updatingAvailability);
    }

    // Accès à la fonction utilisée pour déterminer l'activabilité de la transition
    public Function getActivableFunction() {
        return activableFunction;
    }

    // Définition de cette fonction
    public void setActivableFunction(Function activableFunction) {
        this.activableFunction = activableFunction;
    }
}
