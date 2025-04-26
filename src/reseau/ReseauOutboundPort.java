package reseau;

import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

import classes.Transition;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.ReseauCI;

public class ReseauOutboundPort<P>
extends AbstractOutboundPort
implements ReseauCI<P>{

	public ReseauOutboundPort(ComponentI owner) throws Exception {
		super(ReseauCI.class, owner);
	}
	
	protected			ReseauOutboundPort(
			Class<? extends RequiredCI> implementedInterface,
			ComponentI owner
			) throws Exception
		{
			super(implementedInterface, owner);
		}

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public String getUri() throws Exception {
		return ((ReseauCI<P>) this.getConnector()).getUri();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<P> getPlaces() throws Exception {
		return ((ReseauCI<P>) this.getConnector()).getPlaces();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Transition> getTransitions() throws Exception {
		return ((ReseauCI<P>) this.getConnector()).getTransitions();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addPlace(P place) throws Exception {
		((ReseauCI<P>) this.getConnector()).addPlace(place);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTransition(Transition transition) throws Exception {
		((ReseauCI<P>) this.getConnector()).addTransition(transition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Transition> update() throws Exception {
		return ((ReseauCI<P>) this.getConnector()).update();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void showReseau() throws Exception {
		((ReseauCI<P>) this.getConnector()).showReseau();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void randomTransition() throws Exception {
		((ReseauCI<P>) this.getConnector()).randomTransition();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void manualTransition(Scanner scanner) throws Exception {
		((ReseauCI<P>) this.getConnector()).manualTransition(scanner);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void activeTransition(Transition tr) throws Exception {
		((ReseauCI<P>) this.getConnector()).activeTransition(tr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void linkEntreePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
			String updatingJetons) throws Exception {
		((ReseauCI<P>) this.getConnector()).linkEntreePlaceCommuneTransition(transition, placeCommune, updatingAvailability, updatingJetons);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void linkSortiePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
			String updatingJetons) throws Exception {
		((ReseauCI<P>) this.getConnector()).linkSortiePlaceCommuneTransition(transition, placeCommune, updatingAvailability, updatingJetons);
	}

	
}
