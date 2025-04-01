package classes;
import java.util.ArrayList;

import interfaces.PlaceI;

public class Place implements PlaceI<Transition> {
    private int nbJeton; // should be replace by ArrayList<Jeton>
    private String uri;
    private ArrayList<Transition> transEntrees;
    private ArrayList<Transition> transSorties;

    public Place(String uri) {
    	this.transEntrees = new ArrayList<Transition>();
    	this.transSorties = new ArrayList<Transition>();
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
    	System.out.printf("Add dans %s\n", uri);
    	this.nbJeton ++;
    }
    
    public void retrieveJeton() {
    	System.out.printf("Retrieve dans %s\n", uri);
    	this.nbJeton--;
    }
    
}