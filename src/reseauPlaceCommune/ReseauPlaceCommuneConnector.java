package reseauPlaceCommune;

import java.util.ArrayList;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.ReseauPlaceCommuneCI;

public class ReseauPlaceCommuneConnector
extends AbstractConnector
implements ReseauPlaceCommuneCI<String>{

	@SuppressWarnings("unchecked")
	@Override
	public int getNbJeton(String uri) throws Exception {
		return ((ReseauPlaceCommuneCI<String>)this.offering).getNbJeton(uri);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getUri() throws Exception {
		return ((ReseauPlaceCommuneCI<String>)this.offering).getUri();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setNbJeton(String uri, int nbJeton) throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).setNbJeton(uri, nbJeton);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<String> getTransEntrees(String uri) throws Exception {
		return ((ReseauPlaceCommuneCI<String>)this.offering).getTransEntrees(uri);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTransEntree(String uri, String entree) throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).addTransEntree(uri, entree);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTransSortie(String uri, String sortie) throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).addTransSortie(uri, sortie);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<String> getTransSorties(String uri) throws Exception {
		return ((ReseauPlaceCommuneCI<String>)this.offering).getTransSorties(uri);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addJeton(String uri) throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).addJeton(uri);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void retrieveJeton(String uri) throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).retrieveJeton(uri);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void acquireJeton(String placeCommune) throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).acquireJeton(placeCommune);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean tryAcquireJeton(String placeCommune) throws Exception {
		return ((ReseauPlaceCommuneCI<String>)this.offering).tryAcquireJeton(placeCommune);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void releaseJeton(String placeCommune) throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).releaseJeton(placeCommune);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void acquireAvailability() throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).acquireAvailability();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean tryAcquireAvailability() throws Exception {
		return ((ReseauPlaceCommuneCI<String>)this.offering).tryAcquireAvailability();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void releaseAvailability() throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).releaseAvailability();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void acquireUpdate() throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).acquireUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean tryAcquireUpdate() throws Exception {
		return ((ReseauPlaceCommuneCI<String>)this.offering).tryAcquireUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void releaseUpdate() throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).releaseUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isConnected() throws Exception {
		return ((ReseauPlaceCommuneCI<String>)this.offering).isConnected();
	}
}
