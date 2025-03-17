package interfaces;

import java.util.ArrayList;

public interface PlaceI<T> {
	
	public int getNbJeton() throws Exception;
	
	public String getUri() throws Exception;

    public void setNbJeton(int nbJeton) throws Exception;

    public ArrayList<T> getTransEntrees() throws Exception;
    
    public void addTransEntree(T entree) throws Exception;
    
    public void addTransSortie(T sortie) throws Exception;

    public ArrayList<T> getTransSorties() throws Exception;
    
    public void addJeton() throws Exception;
    
    public void retrieveJeton() throws Exception;
}


