package interfaces;

import java.util.ArrayList;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

// Interface pour la gestion des places communes dans un réseau de Petri
// Permet de gérer des places partagées entre plusieurs réseaux avec des opérations atomiques sur les jetons et la disponibilité
public interface ReseauPlaceCommuneCI<T>
extends OfferedCI, // Interface offerte pour fournir des services
RequiredCI, // Interface requise pour demander des services
ReseauPlaceCommuneI<T> { // Hérite des méthodes de l'interface de place commune de base
	
    // Retourne le nombre de jetons dans une place commune identifiée par son URI
    public int getNbJeton(String uri) throws Exception;
    
    // Vérifie si la place commune est connectée (à un réseau ou autre composant)
    public boolean isConnected() throws Exception;
    
    // Retourne l'URI de la place commune
    public String getUri() throws Exception;

    // Définit le nombre de jetons dans la place commune identifiée par son URI
    public void setNbJeton(String uri, int nbJeton) throws Exception;

    // Retourne la liste des transitions d'entrée pour une place commune donnée
    public ArrayList<T> getTransEntrees(String uri) throws Exception;
    
    // Ajoute une transition d'entrée pour la place commune identifiée par son URI
    public void addTransEntree(String uri, T entree) throws Exception;
    
    // Ajoute une transition de sortie pour la place commune identifiée par son URI
    public void addTransSortie(String uri, T sortie) throws Exception;

    // Retourne la liste des transitions de sortie pour une place commune donnée
    public ArrayList<T> getTransSorties(String uri) throws Exception;
    
    // Ajoute un jeton dans la place commune identifiée par son URI
    public void addJeton(String uri) throws Exception;
    
    // Retire un jeton de la place commune identifiée par son URI
    public void retrieveJeton(String uri) throws Exception;
    
    // Acquiert un jeton pour une place commune spécifique
    public void acquireJeton(String placeCommune) throws Exception;

    // Tente d'acquérir un jeton pour une place commune spécifique, sans bloquer
    public boolean tryAcquireJeton(String placeCommune) throws Exception;

    // Libère un jeton pour une place commune spécifique
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
}
