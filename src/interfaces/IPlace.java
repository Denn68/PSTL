package interfaces;

import java.util.ArrayList;

import classes.Transition;
import classes.TransitionExterne;

public interface IPlace {
	
	public int getNbJeton();
	
	public String getUri();

    public void setNbJeton(int nbJeton);

    public ArrayList<Transition> getTransEntrees();
    
    public void addTransEntree(Transition entree);
    
    public void addTransSortie(Transition sortie);

    public ArrayList<Transition> getTransSorties();
}


