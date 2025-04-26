package reseauPlaceCommune;

import java.util.ArrayList;

import classes.Transition;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.PlaceCI;
import interfaces.ReseauPlaceCommuneCI;
import interfaces.ReseauPlaceCommuneI;

public class ReseauPlaceCommuneInboundPort
extends		AbstractInboundPort
implements	ReseauPlaceCommuneCI<Transition>{
	private static final long serialVersionUID = 1L;

	public				ReseauPlaceCommuneInboundPort(
		ComponentI owner,
		String URI
		) throws Exception
	{
		super(URI, ReseauPlaceCommuneCI.class, owner);
	}

	public				ReseauPlaceCommuneInboundPort(
		String uri,
		ComponentI owner,
		String URI
		) throws Exception
	{
		super(uri, PlaceCI.class, owner, URI, null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int getNbJeton(String uri) throws Exception {
		return this.getOwner().handleRequest(
			owner -> ((ReseauPlaceCommuneI) owner).getNbJeton(uri));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getUri() throws Exception {
		return this.getOwner().handleRequest(
				owner -> ((ReseauPlaceCommuneI) owner).getUri());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setNbJeton(String uri, int nbJeton) throws Exception {
		this.getOwner().handleRequest(
				owner -> {((ReseauPlaceCommuneI) owner).setNbJeton(uri, nbJeton);
				return null;});
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<Transition> getTransEntrees(String uri) throws Exception {
		return this.getOwner().handleRequest(
				owner -> ((ReseauPlaceCommuneI) owner).getTransEntrees(uri));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addTransEntree(String uri, Transition entree) throws Exception {
		this.getOwner().handleRequest(
				owner -> {((ReseauPlaceCommuneI) owner).addTransEntree(uri, entree);
				return null;});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addTransSortie(String uri, Transition sortie) throws Exception {
		this.getOwner().handleRequest(
				owner -> {((ReseauPlaceCommuneI) owner).addTransSortie(uri, sortie);
				return null;});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<Transition> getTransSorties(String uri) throws Exception {
		return this.getOwner().handleRequest(
				owner -> ((ReseauPlaceCommuneI) owner).getTransSorties(uri));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addJeton(String uri) throws Exception {
		this.getOwner().handleRequest(
				owner -> {((ReseauPlaceCommuneI) owner).addJeton(uri);
				return null;});
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void retrieveJeton(String uri) throws Exception {
		this.getOwner().handleRequest(
				owner -> {((ReseauPlaceCommuneI) owner).retrieveJeton(uri);
				return null;});
	}
}
