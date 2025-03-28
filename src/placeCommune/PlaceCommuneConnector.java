package placeCommune;

import java.util.ArrayList;

import classes.Transition;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.PlaceCI;

public class PlaceCommuneConnector<I, R>
extends AbstractConnector
implements PlaceCI<Transition<I, R>>{

	@SuppressWarnings("unchecked")
	@Override
	public int getNbJeton() throws Exception {
		return ((PlaceCI<Transition<I, R>>)this.offering).getNbJeton();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getUri() throws Exception {
		return ((PlaceCI<Transition<I, R>>)this.offering).getUri();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setNbJeton(int nbJeton) throws Exception {
		((PlaceCI<Transition<I, R>>)this.offering).setNbJeton(nbJeton);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Transition<I, R>> getTransEntrees() throws Exception {
		return ((PlaceCI<Transition<I, R>>)this.offering).getTransEntrees();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTransEntree(Transition<I, R> entree) throws Exception {
		((PlaceCI<Transition<I, R>>)this.offering).addTransEntree(entree);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTransSortie(Transition<I, R> sortie) throws Exception {
		((PlaceCI<Transition<I, R>>)this.offering).addTransSortie(sortie);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Transition<I, R>> getTransSorties() throws Exception {
		return ((PlaceCI<Transition<I, R>>)this.offering).getTransSorties();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addJeton() throws Exception {
		((PlaceCI<Transition<I, R>>)this.offering).addJeton();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void retrieveJeton() throws Exception {
		((PlaceCI<Transition<I, R>>)this.offering).retrieveJeton();
	}
}
