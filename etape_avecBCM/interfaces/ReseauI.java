package interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

import classes.Transition;

// Interface pour la gestion des réseaux de Petri, où chaque réseau peut être constitué de places et de transitions
public interface ReseauI<P> {
    
    // Getters
    // Retourne l'URI (identifiant unique) du réseau
    public String getUri() throws Exception;

    // Retourne une collection de toutes les places du réseau
    public Collection<P> getPlaces() throws Exception;

    // Retourne une collection de toutes les transitions du réseau
    public Collection<Transition> getTransitions() throws Exception;

    // Setters
    // Ajoute une place au réseau
    public void addPlace(P place) throws Exception;

    // Ajoute une transition au réseau
    public void addTransition(Transition transition) throws Exception;

    // Met à jour les transitions activables en fonction de l'état des places et des jetons
    public Set<Transition> update() throws Exception;
    
    // Affiche l'état actuel du réseau
    public void showReseau() throws Exception;
    
    // Active une transition donnée si elle est activable
    public void activeTransition(Transition tr) throws Exception;

    // Exécute une transition choisie au hasard parmi celles activables
    public void randomTransition() throws Exception;

    // Permet à un utilisateur de choisir manuellement une transition à activer via le scanner
    public void manualTransition(Scanner scanner) throws Exception;
    
    // Lie une place commune en entrée d'une transition avec des paramètres spécifiques (seuil, disponibilité, jetons)
    public void linkEntreePlaceCommuneTransition(
        String transition,
        String placeCommune,
        int seuil,
        String updatingAvailability,
        String updatingJetons) throws Exception;
    
    // Lie une place commune en sortie d'une transition avec des paramètres pour la gestion de la disponibilité et des jetons
    public void linkSortiePlaceCommuneTransition(
        String transition, 
        String placeCommune,
        String updatingAvailability,
        String updatingJetons) throws Exception;
    
    // Met à jour l'état des transitions activables en fonction de l'URI, des transitions sortantes et du nombre de jetons
    public void updateTransitionsActivable(
        String uri,
        ArrayList<String> transSorties,
        int nbJeton) throws Exception;
}
