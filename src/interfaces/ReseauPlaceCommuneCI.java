package interfaces;

import java.util.ArrayList;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface ReseauPlaceCommuneCI<T>
extends		OfferedCI,
RequiredCI,
ReseauPlaceCommuneI<T>{
	
	public int getNbJeton(String uri) throws Exception;
	
	public boolean isConnected() throws Exception;
	
	public String getUri() throws Exception;

    public void setNbJeton(String uri, int nbJeton) throws Exception;

    public ArrayList<T> getTransEntrees(String uri) throws Exception;
    
    public void addTransEntree(String uri, T entree) throws Exception;
    
    public void addTransSortie(String uri, T sortie) throws Exception;

    public ArrayList<T> getTransSorties(String uri) throws Exception;
    
    public void addJeton(String uri) throws Exception;
    
    public void retrieveJeton(String uri) throws Exception;
    
    public void acquireJeton(String placeCommune) throws Exception;

	public boolean tryAcquireJeton(String placeCommune) throws Exception;

	public void releaseJeton(String placeCommune) throws Exception;
	
	public void acquireAvailability() throws Exception;

	public boolean tryAcquireAvailability() throws Exception;
	
	public void releaseAvailability() throws Exception;
	
	public void acquireUpdate() throws Exception;

	public boolean tryAcquireUpdate() throws Exception;
	
	public void releaseUpdate() throws Exception;
}


