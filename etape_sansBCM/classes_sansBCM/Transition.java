package classes_sansBCM;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

//Déclaration de la classe Transition, générique avec types T (entrée) et R (résultat de la fonction)
public class Transition<T, R> {
 // Places locales en entrée et sortie
 private ArrayList<Place> placeEntrees;
 private ArrayList<Place> placeSorties;

 // Places communes (partagées entre réseaux) en entrée et sortie
 private ArrayList<PlaceCommune> placeCommuneEntrees;
 private ArrayList<PlaceCommune> placeCommuneSorties;

 // Map associant à chaque place commune une valeur booléenne indiquant si elle est activable
 private Map<PlaceCommune, Boolean> activable;

 // Sémaphores pour la mise à jour des places communes (contrôle d'accès concurrent)
 private Map<PlaceCommune, Semaphore> updatingAvailability;
 private Map<PlaceCommune, Semaphore> updatingJetons;

 // Identifiant unique de la transition
 private String uri;

 // Fonction passée à la transition, utilisée lors de son activation
 Function<T, R> activableFunction;

 // Constructeur : initialise toutes les structures de données
 public Transition(String uri, Function<T, R> function) {
     this.placeEntrees = new ArrayList<>();
     this.placeSorties = new ArrayList<>();
     this.placeCommuneEntrees = new ArrayList<>();
     this.placeCommuneSorties = new ArrayList<>();
     this.activable = new HashMap<>();
     this.updatingJetons = new HashMap<>();
     this.updatingAvailability = new HashMap<>();
     this.uri = uri;
     this.activableFunction = function;
 }

 // Renvoie l'URI de la transition
 public String getUri() {
     return uri;
 }

 // Inverse l'état d'activabilité pour une place commune (utilisé pour synchronisation)
 public void updateIsActivable(PlaceCommune place) {
     boolean currentPlaceState = this.activable.get(place); 
     this.activable.put(place, !currentPlaceState);
 }

 // Vérifie si la transition est activable (toutes les places communes d'entrée doivent l'être)
 public boolean isActivable() {
     for (PlaceCommune p : this.placeCommuneEntrees) {
         if (!this.activable.get(p)) {
             return false;
         }
     }
     return true;
 }

 // Accesseurs pour les places d'entrée locales
 public ArrayList<Place> getPlacesEntrees() {
     return this.placeEntrees;
 }

 // Ajoute plusieurs places d’entrée locales à la transition
 public void addPlacesEntree(ArrayList<Place> entrees) {
     for (Place p : entrees) {
         this.placeEntrees.add(p);
     }
 }

 // Accesseurs pour les places de sortie locales
 public ArrayList<Place> getPlacesSorties() {
     return this.placeSorties;
 }

 // Ajoute plusieurs places de sortie locales à la transition
 public void addPlacesSortie(ArrayList<Place> sorties) {
     for (Place p : sorties) {
         this.placeSorties.add(p);
     }
 }

 // Accesseurs pour les places communes d’entrée
 public ArrayList<PlaceCommune> getPlacesCommuneEntrees() {
     return this.placeCommuneEntrees;
 }

 // Ajoute plusieurs places communes en entrée et initialise leur état et sémaphores
 public void addPlacesCommuneEntree(ArrayList<PlaceCommune> entrees) {
     for (PlaceCommune p : entrees) {        
         this.placeCommuneEntrees.add(p);
         this.activable.put(p, true);  // initialement activable
         this.updatingAvailability.put(p, p.getUpdatingAvailability());
         this.updatingJetons.put(p, p.getUpdatingJetons());
     }
 }

 // Accesseurs pour les places communes de sortie
 public ArrayList<PlaceCommune> getPlacesCommuneSorties() {
     return this.placeCommuneSorties;
 }

 // Ajoute plusieurs places communes en sortie et initialise leurs sémaphores
 public void addPlacesCommuneSortie(ArrayList<PlaceCommune> sorties) {
     for (PlaceCommune p : sorties) {        
         this.placeCommuneSorties.add(p);
         this.updatingAvailability.put(p, p.getUpdatingAvailability());
     }
 }

 // Activation d’une transition : effectue les changements d’état si elle est activable
 public void activateTransition(T input) {
     if (this.isActivable()) {
         boolean skip = false;

         // Tente d'acquérir les sémaphores des jetons des places communes d'entrée
         for (PlaceCommune placeCommune : this.getPlacesCommuneEntrees()) {
             skip = this.updatingJetons.get(placeCommune).tryAcquire();
         }

         // Si les sémaphores ont été correctement acquis
         if (!skip) {
             System.out.printf("Not Skip - %s\n", uri);

             // Consomme les jetons des places communes d’entrée et relâche le verrou availability
             for (PlaceCommune placeCommune : this.getPlacesCommuneEntrees()) {
                 placeCommune.retrieveJeton();
                 this.updatingAvailability.get(placeCommune).release();
                 this.updatingJetons.get(placeCommune).release();
             }

             // Consomme les jetons des places locales d’entrée
             for (Place place : this.getPlacesEntrees()) {
                 place.retrieveJeton();
             }

             // Ajoute des jetons aux places locales de sortie
             for (Place place : this.getPlacesSorties()) {
                 place.addJeton();
             }

             // Ajoute des jetons aux places communes de sortie
             for (PlaceCommune placeCommune : this.getPlacesCommuneSorties()) {
                 placeCommune.addJeton();
                 this.updatingAvailability.get(placeCommune).release();
             }

             // Appel de la fonction associée à la transition
             this.activableFunction.apply(input);
         } else {
             System.out.println("La transition a été prise par un autre thread");
         }
     } else {
         System.out.println("La transition n'est pas activable, la place commune n'a pas de jeton");
     }
 }
}
