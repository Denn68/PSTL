package reseau;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

import classes.Transition;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.ReseauCI;

public class ReseauConnector<P>
extends AbstractConnector
implements ReseauCI<P>{

	@SuppressWarnings("unchecked")
	@Override
	public String getUri() throws Exception {
		return ((ReseauCI<P>)this.offering).getUri();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<P> getPlaces() throws Exception {
		return ((ReseauCI<P>)this.offering).getPlaces();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Transition> getTransitions() throws Exception {
		return ((ReseauCI<P>)this.offering).getTransitions();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addPlace(P place) throws Exception {
		((ReseauCI<P>)this.offering).addPlace(place);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTransition(Transition transition) throws Exception {
		((ReseauCI<P>)this.offering).addTransition(transition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Transition> update() throws Exception {
		return ((ReseauCI<P>)this.offering).update();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void showReseau() throws Exception {
		((ReseauCI<P>)this.offering).showReseau();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void randomTransition() throws Exception {
		((ReseauCI<P>)this.offering).randomTransition();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void manualTransition(Scanner scanner) throws Exception {
		((ReseauCI<P>)this.offering).manualTransition(scanner);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void activeTransition(Transition tr) throws Exception {
		((ReseauCI<P>)this.offering).activeTransition(tr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void linkEntreePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
			String updatingJetons) throws Exception {
		((ReseauCI<P>)this.offering).linkEntreePlaceCommuneTransition(transition, placeCommune, updatingAvailability, updatingJetons);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void linkSortiePlaceCommuneTransition(String transition, String placeCommune, String updatingAvailability,
			String updatingJetons) throws Exception {
		((ReseauCI<P>)this.offering).linkSortiePlaceCommuneTransition(transition, placeCommune, updatingAvailability, updatingJetons);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateTransitionsActivable(String uri, ArrayList<String> transSorties, boolean transitionsState)
			throws Exception {
		((ReseauCI<P>)this.offering).updateTransitionsActivable(uri, transSorties, transitionsState);
	}

	
}
