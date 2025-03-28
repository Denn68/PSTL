package placeCommune;

import java.util.ArrayList;

import classes.Transition;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.PlaceCI;

public class PlaceCommuneOutboundPort<I, R>
extends		AbstractOutboundPort
implements	PlaceCI<Transition<I, R>>{
	private static final long serialVersionUID = 1L;

	public				PlaceCommuneOutboundPort(
		String URI,
		ComponentI owner
		) throws Exception
	{
		super(URI, PlaceCI.class, owner);
	}

	public				PlaceCommuneOutboundPort(
		ComponentI owner
		) throws Exception
	{
		super(PlaceCI.class, owner);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int getNbJeton() throws Exception {
		return ((PlaceCI) this.getConnector()).getNbJeton();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getUri() throws Exception {
		return ((PlaceCI) this.getConnector()).getUri();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setNbJeton(int nbJeton) throws Exception {
		((PlaceCI) this.getConnector()).setNbJeton(nbJeton);
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<Transition<I, R>> getTransEntrees() throws Exception {
		return ((PlaceCI) this.getConnector()).getTransEntrees();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addTransEntree(Transition<I, R> entree) throws Exception {
		((PlaceCI) this.getConnector()).addTransEntree(entree);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addTransSortie(Transition<I, R> sortie) throws Exception {
		((PlaceCI) this.getConnector()).addTransSortie(sortie);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<Transition<I, R>> getTransSorties() throws Exception {
		return ((PlaceCI) this.getConnector()).getTransSorties();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addJeton() throws Exception {
		((PlaceCI) this.getConnector()).addJeton();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void retrieveJeton() throws Exception {
		((PlaceCI) this.getConnector()).retrieveJeton();
	}
}
