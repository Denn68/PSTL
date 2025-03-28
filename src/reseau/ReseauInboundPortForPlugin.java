package reseau;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import classes.Transition;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.ReseauCI;

public class ReseauInboundPortForPlugin<I, R, P>
extends		AbstractInboundPort
implements	ReseauCI<I, R, P>{
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
					return ((ReseauPlugin<I, R, P>) this.getServiceProviderReference()).getUri();
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
					return ((ReseauPlugin<I, R, P>) this.getServiceProviderReference()).getPlaces();
				}
			});
	}

	@Override
	public ArrayList<Transition<I, R>> getTransitions() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<ArrayList<Transition<I, R>>>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public ArrayList<Transition<I, R>> call() throws Exception {
					return ((ReseauPlugin<I, R, P>) this.getServiceProviderReference()).getTransitions();
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
					((ReseauPlugin<I, R, P>) this.getServiceProviderReference()).addPlace(place);
					return null;
				}
			});
		
	}

	@Override
	public void addTransition(Transition<I, R> transition) throws Exception {
		this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Void call() throws Exception {
					((ReseauPlugin<I, R, P>) this.getServiceProviderReference()).addTransition(transition);
					return null;
				}
			});
	}

	@Override
	public Set<Transition<I, R>> update() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Set<Transition<I, R>>>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Set<Transition<I, R>> call() throws Exception {
					return ((ReseauPlugin<I, R, P>) this.getServiceProviderReference()).update();
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
					((ReseauPlugin<I, R, P>) this.getServiceProviderReference()).showReseau();
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
					((ReseauPlugin<I, R, P>) this.getServiceProviderReference()).randomTransition();
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
					((ReseauPlugin<I, R, P>) this.getServiceProviderReference()).manualTransition(scanner);
					return null;
				}
			});
	}

}
