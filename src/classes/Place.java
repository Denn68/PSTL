package classes;
import java.util.ArrayList;

import interfaces.IPlace;

public class Place implements IPlace {
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
}