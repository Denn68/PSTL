package classes;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import interfaces.PlaceI;

public class PlaceCommune<I, R> extends Thread implements PlaceI<Transition<I, R>> {
    private int nbJeton;
    private String uri;
    private ArrayList<Transition<I, R>> transEntrees;
    private ArrayList<Transition<I, R>> transSorties;
    private Semaphore updatingAvailability;
    private Semaphore updatingJetons;
    //private volatile boolean running = true;

    public PlaceCommune(String uri) {
        this.transEntrees = new ArrayList<>();
        this.transSorties = new ArrayList<>();
        this.nbJeton = 0;
        this.uri = uri;
        this.updatingAvailability = new Semaphore(0);
        this.updatingJetons = new Semaphore(1);
    }

    @Override
    public void run() {
        try {
        	if(this.nbJeton == 0) {
        		for(Transition<I, R> t : this.transSorties) {
        			t.updateIsActivable(this);
        		}
        	}
            while (true) {
            	updatingAvailability.acquire(); // Attente ici, pas d'attente active
                System.out.println("Mise à jour des possibilités de transitions: " + uri);

                for(Transition<I, R> t: this.transSorties) {
                	t.updateIsActivable(this);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Thread stoppé proprement : " + uri);
    }

    @Override
    public int getNbJeton() {
        return nbJeton;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public void setNbJeton(int nbJeton) {
        this.nbJeton = nbJeton;
    }

    @Override
    public ArrayList<Transition<I, R>> getTransEntrees() {
        return transEntrees;
    }

    @Override
    public void addTransEntree(Transition<I, R> entree) {
        transEntrees.add(entree);
    }

    @Override
    public ArrayList<Transition<I, R>> getTransSorties() {
        return transSorties;
    }

    @Override
    public void addTransSortie(Transition<I, R> sortie) {
        transSorties.add(sortie);
    }
    
    public Semaphore getUpdatingAvailability() {
    	return this.updatingAvailability;
    }
    
    public Semaphore getUpdatingJetons() {
    	return this.updatingJetons;
    }
    
    public void addJeton() {
    	System.out.printf("Add dans %s\n", uri);
    	this.nbJeton ++;
    }
    
    public void retrieveJeton() {
    	System.out.printf("Retrieve dans %s\n", uri);
    	this.nbJeton--;
    }
}
