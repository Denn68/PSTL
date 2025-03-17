package classes;
import java.util.ArrayList;

import interfaces.PlaceI;

public class Place<T, R> implements PlaceI<Transition<T, R>> {
    private int nbJeton; // should be replace by ArrayList<Jeton>
    private String uri;
    private ArrayList<Transition<T, R>> transEntrees;
    private ArrayList<Transition<T, R>> transSorties;

    public Place(String uri) {
    	this.transEntrees = new ArrayList<Transition<T, R>>();
    	this.transSorties = new ArrayList<Transition<T, R>>();
    	this.nbJeton = 0;
    	this.uri = uri;
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
    public ArrayList<Transition<T, R>> getTransEntrees() {
        return transEntrees;
    }
    
    @Override
    public void addTransEntree(Transition<T, R> entree) {
        transEntrees.add(entree);
    }

    @Override
    public ArrayList<Transition<T, R>> getTransSorties() {
        return transSorties;
    }
    
    @Override
    public void addTransSortie(Transition<T, R> sortie) {
        transSorties.add(sortie);
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