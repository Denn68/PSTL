package reseauPlaceCommune;

import java.util.ArrayList;

import classes.Transition;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.ReseauPlaceCommuneCI;

public class ReseauPlaceCommuneConnector
extends AbstractConnector
implements ReseauPlaceCommuneCI<Transition>{

	@SuppressWarnings("unchecked")
	@Override
	public int getNbJeton(String uri) throws Exception {
		return ((ReseauPlaceCommuneCI<Transition>)this.offering).getNbJeton(uri);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getUri() throws Exception {
		return ((ReseauPlaceCommuneCI<Transition>)this.offering).getUri();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setNbJeton(String uri, int nbJeton) throws Exception {
		((ReseauPlaceCommuneCI<Transition>)this.offering).setNbJeton(uri, nbJeton);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Transition> getTransEntrees(String uri) throws Exception {
		return ((ReseauPlaceCommuneCI<Transition>)this.offering).getTransEntrees(uri);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTransEntree(String uri, Transition entree) throws Exception {
		((ReseauPlaceCommuneCI<Transition>)this.offering).addTransEntree(uri, entree);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTransSortie(String uri, Transition sortie) throws Exception {
		((ReseauPlaceCommuneCI<Transition>)this.offering).addTransSortie(uri, sortie);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Transition> getTransSorties(String uri) throws Exception {
		return ((ReseauPlaceCommuneCI<Transition>)this.offering).getTransSorties(uri);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addJeton(String uri) throws Exception {
		((ReseauPlaceCommuneCI<Transition>)this.offering).addJeton(uri);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void retrieveJeton(String uri) throws Exception {
		((ReseauPlaceCommuneCI<Transition>)this.offering).retrieveJeton(uri);
	}
}
