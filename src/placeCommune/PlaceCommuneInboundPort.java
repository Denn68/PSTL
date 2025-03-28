package placeCommune;

import java.util.ArrayList;

import classes.Transition;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.PlaceCI;
import interfaces.PlaceI;

public class PlaceCommuneInboundPort<I, R>
extends		AbstractInboundPort
implements	PlaceCI<Transition<I, R>>{
	private static final long serialVersionUID = 1L;

	public				PlaceCommuneInboundPort(
		ComponentI owner,
		String URI
		) throws Exception
	{
		super(PlaceCI.class, owner, URI, null);
	}

	public				PlaceCommuneInboundPort(
		String uri,
		ComponentI owner,
		String URI
		) throws Exception
	{
		super(uri, PlaceCI.class, owner, URI, null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int getNbJeton() throws Exception {
		return this.getOwner().handleRequest(
			owner -> ((PlaceI) owner).getNbJeton());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getUri() throws Exception {
		return this.getOwner().handleRequest(
				owner -> ((PlaceI) owner).getUri());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setNbJeton(int nbJeton) throws Exception {
		this.getOwner().handleRequest(
				owner -> {((PlaceI) owner).setNbJeton(nbJeton);
				return null;});
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<Transition<I, R>> getTransEntrees() throws Exception {
		return this.getOwner().handleRequest(
				owner -> ((PlaceI) owner).getTransEntrees());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addTransEntree(Transition<I, R> entree) throws Exception {
		this.getOwner().handleRequest(
				owner -> {((PlaceI) owner).addTransEntree(entree);
				return null;});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addTransSortie(Transition<I, R> sortie) throws Exception {
		this.getOwner().handleRequest(
				owner -> {((PlaceI) owner).addTransSortie(sortie);
				return null;});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<Transition<I, R>> getTransSorties() throws Exception {
		return this.getOwner().handleRequest(
				owner -> ((PlaceI) owner).getTransSorties());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addJeton() throws Exception {
		this.getOwner().handleRequest(
				owner -> {((PlaceI) owner).addJeton();
				return null;});
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void retrieveJeton() throws Exception {
		this.getOwner().handleRequest(
				owner -> {((PlaceI) owner).retrieveJeton();
				return null;});
	}
}
