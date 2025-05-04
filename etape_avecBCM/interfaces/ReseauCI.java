package interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

import classes.Transition;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

// Interface pour les réseaux de Petri, définissant les méthodes offertes et requises
public interface ReseauCI<P>
extends OfferedCI,    // Cette interface offre des méthodes pour une communication avec des composants externes
        RequiredCI,    // Cette interface dépend d'autres composants externes pour son fonctionnement
        ReseauI<P> {   // Hérite des méthodes de ReseauI<P> (celles qui sont spécifiques aux réseaux)

    // Getters
    // Retourne l'URI (identifiant unique) de ce réseau
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

    // Met à jour les transitions activables en fonction de l'état actuel des places et des jetons
    public Set<Transition> update() throws Exception;
    
    // Affiche l'état actuel du réseau
    public void showReseau() throws Exception;
    
    // Active une transition donnée (si elle est activable)
    public void activeTransition(Transition tr) throws Exception;

    // Exécute une transition au hasard parmi celles qui sont activables
    public void randomTransition() throws Exception;

    // Permet à un utilisateur de choisir manuellement une transition à activer via le scanner
    public void manualTransition(Scanner scanner) throws Exception;
    
    // Lier une place commune en entrée d'une transition avec des paramètres spécifiques pour la gestion des jetons et de la disponibilité
    public void linkEntreePlaceCommuneTransition(
        String transition,
        String placeCommune,
        int seuil,
        String updatingAvailability,
        String updatingJetons) throws Exception;
    
    // Lier une place commune en sortie d'une transition avec des paramètres pour la gestion des jetons et de la disponibilité
    public void linkSortiePlaceCommuneTransition(
        String transition, 
        String placeCommune,
        String updatingAvailability,
        String updatingJetons) throws Exception;

    // Met à jour l'état des transitions activables en fonction des jetons et des transitions sortantes
    public void updateTransitionsActivable(
        String uri,
        ArrayList<String> transSorties,
        int nbJeton) throws Exception;
    
}
