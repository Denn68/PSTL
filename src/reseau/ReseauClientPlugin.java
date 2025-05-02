package reseau;

import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;
import fr.sorbonne_u.components.reflection.interfaces.ReflectionCI;
import fr.sorbonne_u.components.reflection.ports.ReflectionOutboundPort;
import interfaces.ReseauCI;

@RequiredInterfaces(required = { ReseauCI.class})
public class ReseauClientPlugin<P>
extends 	AbstractPlugin{
	private static final long serialVersionUID = 1L;

	protected ReseauOutboundPort<P>	rop;
	private String ripbUri;

	protected			ReseauClientPlugin(String ripbUri) throws Exception
	{
		this.ripbUri = ripbUri;
	}
	
	@Override
	public void			installOn(ComponentI owner) throws Exception
	{
		super.installOn(owner);
		
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
				this.ripbUri,
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
