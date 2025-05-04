package interfaces;

import java.util.ArrayList;

// Interface pour la gestion des opérations sur une place commune dans un réseau de Petri
// Cette interface définit les méthodes permettant de manipuler les jetons et les transitions dans une place partagée.
public interface ReseauPlaceCommuneI<T> {
    
    // Retourne le nombre de jetons dans la place commune identifiée par son URI
    public int getNbJeton(String uri) throws Exception;
    
    // Retourne l'URI de la place commune
    public String getUri() throws Exception;

    // Définit le nombre de jetons dans la place commune identifiée par son URI
    public void setNbJeton(String uri, int nbJeton) throws Exception;

    // Retourne la liste des transitions d'entrée pour la place commune identifiée par son URI
    public ArrayList<T> getTransEntrees(String uri) throws Exception;
    
    // Ajoute une transition d'entrée pour la place commune identifiée par son URI
    public void addTransEntree(String uri, T entree) throws Exception;
    
    // Ajoute une transition de sortie pour la place commune identifiée par son URI
    public void addTransSortie(String uri, T sortie) throws Exception;

    // Retourne la liste des transitions de sortie pour la place commune identifiée par son URI
    public ArrayList<T> getTransSorties(String uri) throws Exception;
    
    // Ajoute un jeton dans la place commune identifiée par son URI
    public void addJeton(String uri) throws Exception;
    
    // Retire un jeton de la place commune identifiée par son URI
    public void retrieveJeton(String uri) throws Exception;
    
    // Acquiert un jeton pour la place commune spécifique
    public void acquireJeton(String placeCommune) throws Exception;

    // Tente d'acquérir un jeton pour la place commune spécifique, sans bloquer
    public boolean tryAcquireJeton(String placeCommune) throws Exception;

    // Libère un jeton pour la place commune spécifique
    public void releaseJeton(String placeCommune) throws Exception;
    
    // Acquiert un verrou de disponibilité pour la place commune
    public void acquireAvailability() throws Exception;

    // Tente d'acquérir un verrou de disponibilité pour la place commune, sans bloquer
    public boolean tryAcquireAvailability() throws Exception;
    
    // Libère le verrou de disponibilité pour la place commune
    public void releaseAvailability() throws Exception;
    
    // Acquiert un verrou de mise à jour pour la place commune
    public void acquireUpdate() throws Exception;

    // Tente d'acquérir un verrou de mise à jour pour la place commune, sans bloquer
    public boolean tryAcquireUpdate() throws Exception;
    
    // Libère le verrou de mise à jour pour la place commune
    public void releaseUpdate() throws Exception;
    
    // Vérifie si la place commune est connectée (à un réseau ou autre composant)
    public boolean isConnected() throws Exception;
}
