package interfaces;

import java.util.ArrayList;

// Interface locale d'une place d'un réseau de Petri
public interface PlaceI<T> {

    // Retourne le nombre de jetons actuellement présents dans la place
    public int getNbJeton() throws Exception;

    // Retourne l'URI (identifiant unique) de cette place
    public String getUri() throws Exception;

    // Définit explicitement le nombre de jetons dans la place
    public void setNbJeton(int nbJeton) throws Exception;

    // Retourne la liste des transitions qui consomment des jetons depuis cette place
    public ArrayList<T> getTransEntrees() throws Exception;

    // Ajoute une transition qui consomme des jetons depuis cette place
    public void addTransEntree(T entree) throws Exception;

    // Ajoute une transition qui produit des jetons dans cette place
    public void addTransSortie(T sortie) throws Exception;

    // Retourne la liste des transitions qui produisent des jetons dans cette place
    public ArrayList<T> getTransSorties() throws Exception;

    // Ajoute un jeton dans cette place
    public void addJeton() throws Exception;

    // Retire un jeton de cette place
    public void retrieveJeton() throws Exception;
}
