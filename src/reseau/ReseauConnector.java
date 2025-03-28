package reseau;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import classes.Transition;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.ReseauCI;

public class ReseauConnector<I, R, P>
extends AbstractConnector
implements ReseauCI<I, R, P>{

	@SuppressWarnings("unchecked")
	@Override
	public String getUri() throws Exception {
		return ((ReseauCI<I, R, P>)this.offering).getUri();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<P> getPlaces() throws Exception {
		return ((ReseauCI<I, R, P>)this.offering).getPlaces();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Transition<I, R>> getTransitions() throws Exception {
		return ((ReseauCI<I, R, P>)this.offering).getTransitions();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addPlace(P place) throws Exception {
		((ReseauCI<I, R, P>)this.offering).addPlace(place);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTransition(Transition<I, R> transition) throws Exception {
		((ReseauCI<I, R, P>)this.offering).addTransition(transition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Transition<I, R>> update() throws Exception {
		return ((ReseauCI<I, R, P>)this.offering).update();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void showReseau() throws Exception {
		((ReseauCI<I, R, P>)this.offering).showReseau();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void randomTransition() throws Exception {
		((ReseauCI<I, R, P>)this.offering).randomTransition();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void manualTransition(Scanner scanner) throws Exception {
		((ReseauCI<I, R, P>)this.offering).manualTransition(scanner);
	}

	
}
