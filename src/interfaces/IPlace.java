package interfaces;

import java.util.ArrayList;

import classes.Transition;

public interface IPlace {
	
	public int getNbJeton();

    public void setNbJeton(int nbJeton);

    public ArrayList<Transition> getTransEntrees();

    public ArrayList<Transition> getTransSorties();
}
