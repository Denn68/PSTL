package reseau;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.endpoints.BCMEndPoint;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.ImplementationInvariantException;
import fr.sorbonne_u.exceptions.InvariantException;
import fr.sorbonne_u.exceptions.PostconditionException;
import fr.sorbonne_u.exceptions.PreconditionException;
import interfaces.ReseauCI;

@SuppressWarnings("rawtypes")
public class ReseauEndpoint
extends BCMEndPoint<ReseauCI>{

	public ReseauEndpoint() {
		super(ReseauCI.class, ReseauCI.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected AbstractInboundPort makeInboundPort(AbstractComponent c, String inboundPortURI) throws Exception {
		// Preconditions checking
		assert	c != null : new PreconditionException("c != null");
		assert	inboundPortURI != null && !inboundPortURI.isEmpty() :
				new PreconditionException(
						"inboundPortURI != null && !inboundPortURI.isEmpty()");

		ReseauInboundPortForPlugin p =
				new ReseauInboundPortForPlugin(c, this.inboundPortURI);
		p.publishPort();

		// Postconditions checking
		assert	p != null && p.isPublished() :
				new PostconditionException(
						"return != null && return.isPublished()");
		assert	((AbstractPort)p).getPortURI().equals(inboundPortURI) :
				new PostconditionException(
						"((AbstractPort)return).getPortURI().equals(inboundPortURI)");
		assert	getServerSideInterface().isAssignableFrom(p.getClass()) :
				new PostconditionException(
						"getOfferedComponentInterface()."
						+ "isAssignableFrom(return.getClass())");
		// Invariant checking
		assert	ReseauEndpoint.implementationInvariants(this) :
				new ImplementationInvariantException(
						"ReseauEndpoint.implementationInvariants(this)");
		assert	ReseauEndpoint.invariants(this) :
				new InvariantException("ReseauEndpoint.invariants(this)");
		
		return p;
	}

	@Override
	protected ReseauCI makeOutboundPort(AbstractComponent c, String inboundPortURI)
			throws Exception {
		// Preconditions checking
				assert	c != null : new PreconditionException("c != null");

				ReseauOutboundPort p =
						new ReseauOutboundPort(c);
				p.publishPort();
				c.doPortConnection(
						p.getPortURI(),
						this.inboundPortURI,
						ReseauOutboundPort.class.getCanonicalName());

				// Postconditions checking
				assert	p != null && p.isPublished() && p.connected() :
						new PostconditionException(
								"return != null && return.isPublished() && "
								+ "return.connected()");
				assert	((AbstractPort)p).getServerPortURI().equals(getInboundPortURI()) :
						new PostconditionException(
								"((AbstractPort)return).getServerPortURI()."
								+ "equals(getInboundPortURI())");
				assert	getClientSideInterface().isAssignableFrom(p.getClass()) :
						new PostconditionException(
								"getImplementedInterface().isAssignableFrom("
								+ "return.getClass())");
				
				// Invariant checking
				assert	implementationInvariants(this) :
						new ImplementationInvariantException(
								"implementationInvariants(this)");
				assert	invariants(this) : new InvariantException("invariants(this)");
				
				return p;
	}

}
