package classes;
import java.util.ArrayList;

import interfaces.IPlace;

public class Place implements IPlace {
    private int nbJeton; // should be replace by ArrayList<Jeton>
    private String uri;
    private ArrayList<Transition> transEntrees;
    private ArrayList<Transition> transSorties;
    private ArrayList <TransitionExterne> transExterneEntrees;
    private ArrayList <TransitionExterne> transExterneSorties;

    public Place(String uri) {
    	this.transEntrees = new ArrayList<Transition>();
    	this.transSorties = new ArrayList<Transition>();
    	this.transExterneEntrees = new ArrayList<TransitionExterne>();
    	this.transExterneSorties = new ArrayList<TransitionExterne>();
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
    
    @Override
    public ArrayList<TransitionExterne> getTransExterneEntrees() {
        return transExterneEntrees;
    }
    
    @Override
    public void addTransExterneEntree(TransitionExterne entree) {
        transExterneEntrees.add(entree);
    }

    @Override
    public ArrayList<TransitionExterne> getTransExterneSorties() {
        return transExterneSorties;
    }
    
    @Override
    public void addTransExterneSortie(TransitionExterne sortie) {
        transExterneSorties.add(sortie);
    }
}