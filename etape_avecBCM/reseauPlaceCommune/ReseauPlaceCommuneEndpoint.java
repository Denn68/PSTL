package reseauPlaceCommune;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.endpoints.BCMEndPoint;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.ImplementationInvariantException;
import fr.sorbonne_u.exceptions.InvariantException;
import fr.sorbonne_u.exceptions.PostconditionException;
import fr.sorbonne_u.exceptions.PreconditionException;
import interfaces.ReseauPlaceCommuneCI;

@SuppressWarnings("rawtypes")
public class ReseauPlaceCommuneEndpoint 
extends BCMEndPoint<ReseauPlaceCommuneCI>{

	public ReseauPlaceCommuneEndpoint() {
		super(ReseauPlaceCommuneCI.class, ReseauPlaceCommuneCI.class);
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

		ReseauPlaceCommuneInboundPort p =
				new ReseauPlaceCommuneInboundPort(c, inboundPortURI);
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
		assert	ReseauPlaceCommuneEndpoint.implementationInvariants(this) :
				new ImplementationInvariantException(
						"PlaceCommuneEndpoint.implementationInvariants(this)");
		assert	ReseauPlaceCommuneEndpoint.invariants(this) :
				new InvariantException("PlaceCommuneEndpoint.invariants(this)");
		
		return p;
	}

	@Override
	protected ReseauPlaceCommuneOutboundPort makeOutboundPort(AbstractComponent c, String inboundPortURI)
			throws Exception {
		// Preconditions checking
				assert	c != null : new PreconditionException("c != null");

				ReseauPlaceCommuneOutboundPort p =
						new ReseauPlaceCommuneOutboundPort(c);
				p.publishPort();
				c.doPortConnection(
						p.getPortURI(),
						inboundPortURI,
						ReseauPlaceCommuneConnector.class.getCanonicalName());

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
