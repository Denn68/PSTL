package reseauPlaceCommune;

import java.util.ArrayList;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.PlaceCI;
import interfaces.ReseauPlaceCommuneCI;
import interfaces.ReseauPlaceCommuneI;

public class ReseauPlaceCommuneInboundPort
extends		AbstractInboundPort
implements	ReseauPlaceCommuneCI<String>{
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
	public ArrayList<String> getTransEntrees(String uri) throws Exception {
		return this.getOwner().handleRequest(
				owner -> ((ReseauPlaceCommuneI) owner).getTransEntrees(uri));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addTransEntree(String uri, String entree) throws Exception {
		this.getOwner().handleRequest(
				owner -> {((ReseauPlaceCommuneI) owner).addTransEntree(uri, entree);
				return null;});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addTransSortie(String uri, String sortie) throws Exception {
		this.getOwner().handleRequest(
				owner -> {((ReseauPlaceCommuneI) owner).addTransSortie(uri, sortie);
				return null;});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<String> getTransSorties(String uri) throws Exception {
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

	@Override
	public void acquireJeton(String placeCommune) throws Exception {
		this.getOwner().handleRequest(
				owner -> {((ReseauPlaceCommuneI) owner).acquireJeton(placeCommune);
				return null;});
	}

	@Override
	public boolean tryAcquireJeton(String placeCommune) throws Exception {
		return this.getOwner().handleRequest(
				owner -> ((ReseauPlaceCommuneI) owner).tryAcquireJeton(placeCommune));
	}

	@Override
	public void releaseJeton(String placeCommune) throws Exception {
		this.getOwner().handleRequest(
				owner -> {((ReseauPlaceCommuneI) owner).releaseJeton(placeCommune);
				return null;});
	}

	@Override
	public void acquireAvailability() throws Exception {
		this.getOwner().handleRequest(
				owner -> {((ReseauPlaceCommuneI) owner).acquireAvailability();
				return null;});
	}

	@Override
	public boolean tryAcquireAvailability() throws Exception {
		return this.getOwner().handleRequest(
				owner -> ((ReseauPlaceCommuneI) owner).tryAcquireAvailability());
	}

	@Override
	public void releaseAvailability() throws Exception {
		this.getOwner().handleRequest(
				owner -> {((ReseauPlaceCommuneI) owner).releaseAvailability();
				return null;});
	}

	@Override
	public boolean isConnected() throws Exception {
		return this.getOwner().handleRequest(
				owner -> ((ReseauPlaceCommuneI) owner).isConnected());
	}
}
