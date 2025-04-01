package reseau;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import classes.Transition;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.ReseauCI;

public class ReseauInboundPortForPlugin<P>
extends		AbstractInboundPort
implements	ReseauCI<P>{
	private static final long serialVersionUID = 1L;

	public				ReseauInboundPortForPlugin(
		ComponentI owner,
		String pluginURI
		) throws Exception
	{
		super(ReseauCI.class, owner, pluginURI, null);

		assert	owner.isInstalled(pluginURI);
	}

	public				ReseauInboundPortForPlugin(
		String uri,
		ComponentI owner,
		String pluginURI
		) throws Exception
	{
		super(uri, ReseauCI.class, owner, pluginURI, null);

		assert	owner.isInstalled(pluginURI);
	}

	@Override
	public String getUri() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<String>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public String call() throws Exception {
					return ((ReseauPlugin<P>) this.getServiceProviderReference()).getUri();
				}
			});
	}

	@Override
	public ArrayList<P> getPlaces() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<ArrayList<P>>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public ArrayList<P> call() throws Exception {
					return ((ReseauPlugin<P>) this.getServiceProviderReference()).getPlaces();
				}
			});
	}

	@Override
	public ArrayList<Transition> getTransitions() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<ArrayList<Transition>>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public ArrayList<Transition> call() throws Exception {
					return ((ReseauPlugin<P>) this.getServiceProviderReference()).getTransitions();
				}
			});
	}

	@Override
	public void addPlace(P place) throws Exception {
		this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Void call() throws Exception {
					((ReseauPlugin<P>) this.getServiceProviderReference()).addPlace(place);
					return null;
				}
			});
		
	}

	@Override
	public void addTransition(Transition transition) throws Exception {
		this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Void call() throws Exception {
					((ReseauPlugin<P>) this.getServiceProviderReference()).addTransition(transition);
					return null;
				}
			});
	}

	@Override
	public Set<Transition> update() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Set<Transition>>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Set<Transition> call() throws Exception {
					return ((ReseauPlugin<P>) this.getServiceProviderReference()).update();
				}
			});
	}

	@Override
	public void showReseau() throws Exception {
		this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Void call() throws Exception {
					((ReseauPlugin<P>) this.getServiceProviderReference()).showReseau();
					return null;
				}
			});
	}

	@Override
	public void randomTransition() throws Exception {
		this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Void call() throws Exception {
					((ReseauPlugin<P>) this.getServiceProviderReference()).randomTransition();
					return null;
				}
			});
	}

	@Override
	public void manualTransition(Scanner scanner) throws Exception {
		this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Void call() throws Exception {
					((ReseauPlugin<P>) this.getServiceProviderReference()).manualTransition(scanner);
					return null;
				}
			});
	}

	@Override
	public void linkPlacesTransition(ArrayList<P> entrees, String t, ArrayList<P> sorties) throws Exception {
		this.getOwner().handleRequest(
				new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
					@SuppressWarnings("unchecked")
					@Override
					public Void call() throws Exception {
						((ReseauPlugin<P>) this.getServiceProviderReference()).linkPlacesTransition(entrees, t, sorties);
						return null;
					}
				});
	}

}
