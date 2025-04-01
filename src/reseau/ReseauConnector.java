package reseau;

import java.util.ArrayList;
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
	public ArrayList<P> getPlaces() throws Exception {
		return ((ReseauCI<P>)this.offering).getPlaces();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Transition> getTransitions() throws Exception {
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
	public void linkPlacesTransition(ArrayList<P> entrees, String t, ArrayList<P> sorties) throws Exception {
		((ReseauCI<P>)this.offering).linkPlacesTransition(entrees, t, sorties);
	}

	
}
