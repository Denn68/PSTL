package interfaces;

import java.util.ArrayList;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

// Interface pour une place dans un réseau de Petri distribué (via BCM)
// Étend les interfaces BCM pour permettre l'appel de méthodes entre composants
public interface PlaceCI<T>
extends OfferedCI, // Interface marquant les services offerts
         RequiredCI, // Interface marquant les services requis
         PlaceI<T> // Hérite également d'une interface locale PlaceI
{
    // Retourne le nombre de jetons présents dans la place
    public int getNbJeton() throws Exception;

    // Retourne l'URI (identifiant unique) de la place
    public String getUri() throws Exception;

    // Définit le nombre de jetons présents dans la place
    public void setNbJeton(int nbJeton) throws Exception;

    // Retourne la liste des transitions ayant cette place comme entrée
    public ArrayList<T> getTransEntrees() throws Exception;

    // Ajoute une transition entrante à cette place
    public void addTransEntree(T entree) throws Exception;

    // Ajoute une transition sortante depuis cette place
    public void addTransSortie(T sortie) throws Exception;

    // Retourne la liste des transitions où cette place est en sortie
    public ArrayList<T> getTransSorties() throws Exception;

    // Ajoute un jeton à la place
    public void addJeton() throws Exception;

    // Retire un jeton de la place
    public void retrieveJeton() throws Exception;
}
