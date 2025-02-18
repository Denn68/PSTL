package classes;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import interfaces.IPlace;

public class PlaceCommune extends Thread implements IPlace {
    private int nbJeton;
    private String uri;
    private ArrayList<Transition> transEntrees;
    private ArrayList<Transition> transSorties;
    private Semaphore semaphore;
    //private volatile boolean running = true;

    public PlaceCommune(String uri) {
        this.transEntrees = new ArrayList<>();
        this.transSorties = new ArrayList<>();
        this.nbJeton = 0;
        this.uri = uri;
        this.semaphore = new Semaphore(0);
    }

    @Override
    public void run() {
        try {
        	if(this.nbJeton == 0) {
        		for(Transition t : this.transSorties) {
        			t.updateIsActivable(this);
        		}
        	}
            while (true) {
                semaphore.acquire(); // Attente ici, pas d'attente active
                //if (!running) break; // Vérifie si on doit s'arrêter
                System.out.println("Thread actif : " + uri);

                for(Transition t: this.transSorties) {
                	t.updateIsActivable(this);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Thread stoppé proprement : " + uri);
    }

    public void pauseThread() {
        System.out.println("Pause du thread...");
        semaphore.drainPermits(); // Bloque le thread
    }

    public void resumeThread() {
        System.out.println("Reprise du thread...");
        semaphore.release(); // Libère un permis pour redémarrer
    }

    public void stopThread() {
        System.out.println("Arrêt du thread...");
        running = false;
        semaphore.release(); // Libère un permis pour sortir de `acquire()`
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
        this.semaphore.release();
    }

    @Override
    public ArrayList<Transition> getTransEntrees() {
        return transEntrees;
    }

    @Override
    public void addTransEntree(Transition entree) {
        transEntrees.add(entree);
    }

    @Override
    public ArrayList<Transition> getTransSorties() {
        return transSorties;
    }

    @Override
    public void addTransSortie(Transition sortie) {
        transSorties.add(sortie);
    }
    
    public void addJeton() {
    	this.nbJeton ++;
    }
    
    public void retrieveJeton() {
    	this.nbJeton--;
    }
}
