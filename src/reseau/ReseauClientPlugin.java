package reseau;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import classes.Place;
import classes.Transition;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ConnectionException;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;
import fr.sorbonne_u.components.reflection.interfaces.ReflectionCI;
import fr.sorbonne_u.components.reflection.ports.ReflectionOutboundPort;
import interfaces.ReseauCI;
import interfaces.ReseauPlaceCommuneCI;
import reseauPlaceCommune.ReseauPlaceCommuneEndpoint;
import test.CVM;
import interfaces.ReseauI;

public class ReseauClientPlugin<P>
extends 	AbstractPlugin{
	private static final long serialVersionUID = 1L;

	protected ReseauOutboundPort<P>	rop;

	@Override
	public void			installOn(ComponentI owner) throws Exception
	{
		super.installOn(owner);
		
		this.addRequiredInterface(ReseauCI.class);
		this.rop = new ReseauOutboundPort<P>(this.getOwner());
		this.rop.publishPort();
	}

	@Override
	public void			initialise() throws Exception
	{
		this.addRequiredInterface(ReflectionCI.class);
		ReflectionOutboundPort rop = new ReflectionOutboundPort(this.getOwner());
		rop.publishPort();
		this.getOwner().doPortConnection(
				rop.getPortURI(),
				CVM.RESEAU_COMPONENT_RIBP_URI,
				ReflectionConnector.class.getCanonicalName());
		String[] uris = rop.findPortURIsFromInterface(ReseauCI.class);
		assert	uris != null && uris.length == 1;

		this.getOwner().doPortDisconnection(rop.getPortURI());
		rop.unpublishPort();
		rop.destroyPort();
		this.removeRequiredInterface(ReflectionCI.class);

		// connect the outbound port.
		this.getOwner().doPortConnection(
				this.rop.getPortURI(),
				uris[0],
				ReseauConnector.class.getCanonicalName());

		super.initialise();
	}


	@Override
	public void			finalise() throws Exception
	{
		this.getOwner().doPortDisconnection(this.rop.getPortURI());
	}


	@Override
	public void			uninstall() throws Exception
	{
		this.rop.unpublishPort();
		this.rop.destroyPort();
		this.removeRequiredInterface(ReseauCI.class);
	}


	public ReseauCI<P> getReseauServicesReference() {
		return this.rop;
	}
	
}
