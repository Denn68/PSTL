package interfaces_sansBCM;

import java.util.ArrayList;

import classes_sansBCM.Transition;

public interface IPlace {
	
	public int getNbJeton();
	
	public String getUri();

    public void setNbJeton(int nbJeton);

    @SuppressWarnings("rawtypes")
	public ArrayList<Transition> getTransEntrees();
    
    @SuppressWarnings("rawtypes")
	public void addTransEntree(Transition entree);
    
    @SuppressWarnings("rawtypes")
	public void addTransSortie(Transition sortie);

    @SuppressWarnings("rawtypes")
	public ArrayList<Transition> getTransSorties();
    
}


