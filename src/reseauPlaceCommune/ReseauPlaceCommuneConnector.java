package reseauPlaceCommune;

import java.util.ArrayList;

import classes.Transition;
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

	@Override
	public void acquireJeton(String placeCommune) throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).acquireJeton(placeCommune);
	}

	@Override
	public boolean tryAcquireJeton(String placeCommune) throws Exception {
		return ((ReseauPlaceCommuneCI<String>)this.offering).tryAcquireJeton(placeCommune);
	}

	@Override
	public void releaseJeton(String placeCommune) throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).releaseJeton(placeCommune);
	}

	@Override
	public void acquireAvailability() throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).acquireAvailability();
	}

	@Override
	public boolean tryAcquireAvailability() throws Exception {
		return ((ReseauPlaceCommuneCI<String>)this.offering).tryAcquireAvailability();
	}

	@Override
	public void releaseAvailability() throws Exception {
		((ReseauPlaceCommuneCI<String>)this.offering).releaseAvailability();
	}

	@Override
	public boolean isConnected() throws Exception {
		return ((ReseauPlaceCommuneCI<String>)this.offering).isConnected();
	}
}
