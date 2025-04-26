package reseau;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

import classes.Transition;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.ReseauCI;
import interfaces.ReseauI;

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
		
		if (!(owner instanceof ReseauI)) {
	        throw new Exception("Le composant " + owner.getClass().getName() +
	                            " doit implémenter ReseauI.");
	    }

		assert	owner.isInstalled(pluginURI);
	}

	public				ReseauInboundPortForPlugin(
		String uri,
		ComponentI owner,
		String pluginURI
		) throws Exception
	{
		super(uri, ReseauCI.class, owner, pluginURI, null);
		
		if (!(owner instanceof ReseauI)) {
	        throw new Exception("Le composant " + owner.getClass().getName() +
	                            " doit implémenter ReseauI.");
	    }

		assert	owner.isInstalled(pluginURI);
	}

	@Override
	public String getUri() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<String>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public String call() throws Exception {
					return ((ReseauI<P>) this.getServiceProviderReference()).getUri();
				}
			});
	}

	@Override
	public Collection<P> getPlaces() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Collection<P>>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Collection<P> call() throws Exception {
					return ((ReseauI<P>) this.getServiceProviderReference()).getPlaces();
				}
			});
	}

	@Override
	public Collection<Transition> getTransitions() throws Exception {
		return this.getOwner().handleRequest(
			new AbstractComponent.AbstractService<Collection<Transition>>(this.getPluginURI()) {
				@SuppressWarnings("unchecked")
				@Override
				public Collection<Transition> call() throws Exception {
					return ((ReseauI<P>) this.getServiceProviderReference()).getTransitions();
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
					((ReseauI<P>) this.getServiceProviderReference()).addPlace(place);
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
					((ReseauI<P>) this.getServiceProviderReference()).addTransition(transition);
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
					return ((ReseauI<P>) this.getServiceProviderReference()).update();
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
					((ReseauI<P>) this.getServiceProviderReference()).showReseau();
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
					((ReseauI<P>) this.getServiceProviderReference()).randomTransition();
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
					((ReseauI<P>) this.getServiceProviderReference()).manualTransition(scanner);
					return null;
				}
			});
	}

	@Override
	public void activeTransition(Transition tr) throws Exception {
		this.getOwner().handleRequest(
				new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
					@SuppressWarnings("unchecked")
					@Override
					public Void call() throws Exception {
						((ReseauI<P>) this.getServiceProviderReference()).activeTransition(tr);
						return null;
					}
				});
	}

	@Override
	public void linkEntreePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
			String updatingJetons) throws Exception {
		this.getOwner().handleRequest(
				new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
					@SuppressWarnings("unchecked")
					@Override
					public Void call() throws Exception {
						((ReseauI<P>) this.getServiceProviderReference())
						.linkEntreePlaceCommuneTransition(
								transition,
								placeCommune,
								updatingAvailability,
								updatingJetons);
						return null;
					}
				});
	}

	@Override
	public void linkSortiePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
			String updatingJetons) throws Exception {
		this.getOwner().handleRequest(
				new AbstractComponent.AbstractService<Void>(this.getPluginURI()) {
					@SuppressWarnings("unchecked")
					@Override
					public Void call() throws Exception {
						((ReseauI<P>) this.getServiceProviderReference())
						.linkSortiePlaceCommuneTransition(
								transition,
								placeCommune,
								updatingAvailability,
								updatingJetons);
						return null;
					}
				});
	}

}
