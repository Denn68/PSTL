package classes;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import interfaces.PlaceCommuneI;

// Classe représentant une place "commune" dans un réseau de Petri, avec gestion concurrente via des sémaphores
public class PlaceCommune extends Thread implements PlaceCommuneI<String> {

    // Nombre de jetons présents dans la place
    private int nbJeton;

    // Identifiant unique de la place (ex. : son nom ou URI)
    private String uri;

    // Liste des identifiants de transitions entrantes (en tant que chaînes de caractères)
    private ArrayList<String> transEntrees;

    // Liste des identifiants de transitions sortantes (en tant que chaînes de caractères)
    private ArrayList<String> transSorties;

    // Sémaphore utilisée pour synchroniser la disponibilité de la place (probablement pour la coordination inter-threads)
    private Semaphore updatingAvailability;

    // Sémaphore utilisée pour contrôler l'accès à la modification du nombre de jetons
    private Semaphore updatingJetons;

    // Variable potentiellement utilisée pour arrêter proprement un thread (commentée ici)
    //private volatile boolean running = true;

    // Constructeur : initialise les listes, les sémaphores et les champs de la place
    public PlaceCommune(String uri) {
        this.transEntrees = new ArrayList<>();
        this.transSorties = new ArrayList<>();
        this.nbJeton = 0;
        this.uri = uri;
        this.updatingAvailability = new Semaphore(0); // Sémaphore initialement indisponible
        this.updatingJetons = new Semaphore(1);       // Sémaphore binaire pour accès exclusif aux jetons
    }

    // Renvoie le nombre actuel de jetons dans la place
    @Override
    public int getNbJeton() {
        return nbJeton;
    }

    // Renvoie l'identifiant de la place
    @Override
    public String getUri() {
        return uri;
    }

    // Met à jour le nombre de jetons (sans synchronisation ici)
    @Override
    public void setNbJeton(int nbJeton) {
        this.nbJeton = nbJeton;
    }

    // Renvoie la liste des transitions entrantes
    @Override
    public ArrayList<String> getTransEntrees() {
        return transEntrees;
    }

    // Ajoute une transition entrante à la liste
    @Override
    public void addTransEntree(String entree) {
        transEntrees.add(entree);
    }

    // Renvoie la liste des transitions sortantes
    @Override
    public ArrayList<String> getTransSorties() {
        return transSorties;
    }

    // Ajoute une transition sortante à la liste
    @Override
    public void addTransSortie(String sortie) {
        transSorties.add(sortie);
    }

    // Accès au sémaphore de disponibilité (utilisé par d'autres threads pour synchroniser)
    public Semaphore getUpdatingAvailability() {
        return this.updatingAvailability;
    }

    // Accès au sémaphore de contrôle des jetons (pour synchroniser les modifications sur nbJeton)
    public Semaphore getUpdatingJetons() {
        return this.updatingJetons;
    }

    // Incrémente le nombre de jetons de la place
    public void addJeton() {
        this.nbJeton++;
    }

    // Décrémente le nombre de jetons de la place
    public void retrieveJeton() {
        this.nbJeton--;
    }
}
