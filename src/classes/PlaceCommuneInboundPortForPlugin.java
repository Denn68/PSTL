package classes;

import java.util.ArrayList;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.PlaceCI;

public class PlaceCommuneInboundPortForPlugin<I, R>
extends		AbstractInboundPort
implements	PlaceCI<Transition<I, R>>{
	private static final long serialVersionUID = 1L;

	public				PlaceCommuneInboundPortForPlugin(
		ComponentI owner,
		String pluginURI
		) throws Exception
	{
		super(PlaceCI.class, owner, pluginURI, null);

		assert	owner.isInstalled(pluginURI);
	}

	public				PlaceCommuneInboundPortForPlugin(
		String uri,
		ComponentI owner,
		String pluginURI
		) throws Exception
	{
		super(uri, PlaceCI.class, owner, pluginURI, null);

		assert	owner.isInstalled(pluginURI);
	}

	@Override
	public int getNbJeton() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Integer>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Integer call() throws Exception {
					return ((PlaceCommunePlugin<I, R>) this.getServiceProviderReference()).getNbJeton();
				}
			});
	}

	@Override
	public String getUri() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<String>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public String call() throws Exception {
					return ((PlaceCommunePlugin<I, R>) this.getServiceProviderReference()).getUri();
				}
			});
	}

	@Override
	public void setNbJeton(int nbJeton) throws Exception {
		this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Void call() throws Exception {
					((PlaceCommunePlugin<I, R>) this.getServiceProviderReference()).setNbJeton(nbJeton);
					return null;
				}
			});
		
	}

	@Override
	public ArrayList<Transition<I, R>> getTransEntrees() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<ArrayList<Transition<I, R>>>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public ArrayList<Transition<I, R>> call() throws Exception {
					return ((PlaceCommunePlugin<I, R>) this.getServiceProviderReference()).getTransEntrees();
				}
			});
	}

	@Override
	public void addTransEntree(Transition<I, R> entree) throws Exception {
		this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Void call() throws Exception {
					((PlaceCommunePlugin<I, R>) this.getServiceProviderReference()).addTransEntree(entree);
					return null;
				}
			});
	}

	@Override
	public void addTransSortie(Transition<I, R> sortie) throws Exception {
		this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Void call() throws Exception {
					((PlaceCommunePlugin<I, R>) this.getServiceProviderReference()).addTransSortie(sortie);
					return null;
				}
			});
	}

	@Override
	public ArrayList<Transition<I, R>> getTransSorties() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<ArrayList<Transition<I, R>>>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public ArrayList<Transition<I, R>> call() throws Exception {
					return ((PlaceCommunePlugin<I, R>) this.getServiceProviderReference()).getTransSorties();
				}
			});
	}

	@Override
	public void addJeton() throws Exception {
		this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Void call() throws Exception {
					((PlaceCommunePlugin<I, R>) this.getServiceProviderReference()).addJeton();
					return null;
				}
			});
	}

	@Override
	public void retrieveJeton() throws Exception {
		this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Void call() throws Exception {
					((PlaceCommunePlugin<I, R>) this.getServiceProviderReference()).retrieveJeton();
					return null;
				}
			});
	}
}
