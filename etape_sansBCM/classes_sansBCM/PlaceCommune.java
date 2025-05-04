package classes_sansBCM;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import interfaces_sansBCM.IPlace;

public class PlaceCommune extends Thread implements IPlace {

    // Nombre de jetons présents dans la place
    private int nbJeton;

    // Identifiant unique (URI) de la place
    private String uri;

    // Transitions entrantes (celles qui mènent vers cette place)
    @SuppressWarnings("rawtypes")
	private ArrayList<Transition> transEntrees;

    // Transitions sortantes (celles qui partent de cette place)
    @SuppressWarnings("rawtypes")
	private ArrayList<Transition> transSorties;

    // Sémaphore utilisé pour signaler qu'une mise à jour des transitions est nécessaire
    private Semaphore updatingAvailability;

    // Sémaphore utilisé pour contrôler l'accès à la modification du nombre de jetons
    private Semaphore updatingJetons;

    //private volatile boolean running = true; // Potentiellement utile pour stopper proprement le thread (non utilisé ici)

    // Constructeur : initialise les listes de transitions, les sémaphores, le nombre de jetons et l’URI
    public PlaceCommune(String uri) {
        this.transEntrees = new ArrayList<>();
        this.transSorties = new ArrayList<>();
        this.nbJeton = 0;
        this.uri = uri;
        this.updatingAvailability = new Semaphore(0); // bloqué jusqu'à ce qu'on libère ce sémaphore
        this.updatingJetons = new Semaphore(1); // permet une exclusion mutuelle sur les jetons
    }

    // Méthode exécutée dans le thread associé à cette place
    @SuppressWarnings("rawtypes")
    @Override
    public void run() {
        try {
        	// Si la place est initialement vide, on met à jour les transitions sortantes
        	if(this.nbJeton == 0) {
        		for( Transition t : this.transSorties) {
        			t.updateIsActivable(this);
        		}
        	}
            // Boucle principale : attend qu'on signale une mise à jour à effectuer
            while (true) {
            	updatingAvailability.acquire(); // attend une autorisation pour faire une mise à jour
                System.out.println("Mise à jour des possibilités de transitions: " + uri);

                // Pour chaque transition sortante, on met à jour son état d'activation
                for(Transition t: this.transSorties) {
                	t.updateIsActivable(this);
                }
            }
        } catch (InterruptedException e) {
            // Interruption propre du thread
            Thread.currentThread().interrupt();
        }

        // Message affiché si le thread se termine
        System.out.println("Thread stoppé proprement : " + uri);
    }

    // Accesseur pour le nombre de jetons
    @Override
    public int getNbJeton() {
        return nbJeton;
    }

    // Accesseur pour l’URI de la place
    @Override
    public String getUri() {
        return uri;
    }

    // Mutateur pour le nombre de jetons
    @Override
    public void setNbJeton(int nbJeton) {
        this.nbJeton = nbJeton;
    }

    // Accesseur pour les transitions entrantes
    @SuppressWarnings("rawtypes")
	@Override
    public ArrayList<Transition> getTransEntrees() {
        return transEntrees;
    }

    // Ajoute une transition entrante
    @SuppressWarnings("rawtypes")
	@Override
    public void addTransEntree(Transition entree) {
        transEntrees.add(entree);
    }

    // Accesseur pour les transitions sortantes
    @SuppressWarnings("rawtypes")
	@Override
    public ArrayList<Transition> getTransSorties() {
        return transSorties;
    }

    // Ajoute une transition sortante
    @SuppressWarnings("rawtypes")
	@Override
    public void addTransSortie(Transition sortie) {
        transSorties.add(sortie);
    }
    
    // Accesseur pour le sémaphore de mise à jour des transitions
    public Semaphore getUpdatingAvailability() {
    	return this.updatingAvailability;
    }
    
    // Accesseur pour le sémaphore de mise à jour des jetons
    public Semaphore getUpdatingJetons() {
    	return this.updatingJetons;
    }
    
    // Incrémente le nombre de jetons (et affiche un message de debug)
    public void addJeton() {
    	//System.out.printf("Add dans %s\n", uri);
    	this.nbJeton ++;
    }
    
    // Décrémente le nombre de jetons (et affiche un message de debug)
    public void retrieveJeton() {
    	//System.out.printf("Retrieve dans %s\n", uri);
    	this.nbJeton--;
    }
}
