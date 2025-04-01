package interfaces;

import java.util.ArrayList;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface ReseauPlaceCommuneCI<T>
extends		OfferedCI,
RequiredCI,
ReseauPlaceCommuneI<T>{
	
	public int getNbJeton(String uri) throws Exception;
	
	public String getUri() throws Exception;

    public void setNbJeton(String uri, int nbJeton) throws Exception;

    public ArrayList<T> getTransEntrees(String uri) throws Exception;
    
    public void addTransEntree(String uri, T entree) throws Exception;
    
    public void addTransSortie(String uri, T sortie) throws Exception;

    public ArrayList<T> getTransSorties(String uri) throws Exception;
    
    public void addJeton(String uri) throws Exception;
    
    public void retrieveJeton(String uri) throws Exception;
}


