package reseauPlaceCommune;

import java.util.ArrayList;

import classes.Transition;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.ReseauPlaceCommuneCI;

public class ReseauPlaceCommuneOutboundPort
extends		AbstractOutboundPort
implements	ReseauPlaceCommuneCI<Transition>{
	private static final long serialVersionUID = 1L;

	public				ReseauPlaceCommuneOutboundPort(
		String URI,
		ComponentI owner
		) throws Exception
	{
		super(URI, ReseauPlaceCommuneCI.class, owner);
	}

	public				ReseauPlaceCommuneOutboundPort(
		ComponentI owner
		) throws Exception
	{
		super(ReseauPlaceCommuneCI.class, owner);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int getNbJeton(String uri) throws Exception {
		return ((ReseauPlaceCommuneCI) this.getConnector()).getNbJeton(uri);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getUri() throws Exception {
		return ((ReseauPlaceCommuneCI) this.getConnector()).getUri();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setNbJeton(String uri, int nbJeton) throws Exception {
		((ReseauPlaceCommuneCI) this.getConnector()).setNbJeton(uri, nbJeton);
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<Transition> getTransEntrees(String uri) throws Exception {
		return ((ReseauPlaceCommuneCI) this.getConnector()).getTransEntrees(uri);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addTransEntree(String uri, Transition entree) throws Exception {
		((ReseauPlaceCommuneCI) this.getConnector()).addTransEntree(uri, entree);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addTransSortie(String uri, Transition sortie) throws Exception {
		((ReseauPlaceCommuneCI) this.getConnector()).addTransSortie(uri, sortie);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<Transition> getTransSorties(String uri) throws Exception {
		return ((ReseauPlaceCommuneCI) this.getConnector()).getTransSorties(uri);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addJeton(String uri) throws Exception {
		((ReseauPlaceCommuneCI) this.getConnector()).addJeton(uri);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void retrieveJeton(String uri) throws Exception {
		((ReseauPlaceCommuneCI) this.getConnector()).retrieveJeton(uri);
	}
}
