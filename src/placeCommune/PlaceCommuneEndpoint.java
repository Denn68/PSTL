package placeCommune;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.endpoints.BCMEndPoint;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.exceptions.ImplementationInvariantException;
import fr.sorbonne_u.exceptions.InvariantException;
import fr.sorbonne_u.exceptions.PostconditionException;
import fr.sorbonne_u.exceptions.PreconditionException;
import interfaces.PlaceCI;

@SuppressWarnings("rawtypes")
public class PlaceCommuneEndpoint 
extends BCMEndPoint<PlaceCI>{

	public PlaceCommuneEndpoint() {
		super(PlaceCI.class, PlaceCI.class);
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

		PlaceCommuneInboundPort p =
				new PlaceCommuneInboundPort(c, this.inboundPortURI);
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
		assert	PlaceCommuneEndpoint.implementationInvariants(this) :
				new ImplementationInvariantException(
						"PlaceCommuneEndpoint.implementationInvariants(this)");
		assert	PlaceCommuneEndpoint.invariants(this) :
				new InvariantException("PlaceCommuneEndpoint.invariants(this)");
		
		return p;
	}

	@Override
	protected PlaceCommuneOutboundPort makeOutboundPort(AbstractComponent c, String inboundPortURI)
			throws Exception {
		// Preconditions checking
				assert	c != null : new PreconditionException("c != null");

				PlaceCommuneOutboundPort p =
						new PlaceCommuneOutboundPort(c);
				p.publishPort();
				c.doPortConnection(
						p.getPortURI(),
						this.inboundPortURI,
						PlaceCommuneConnector.class.getCanonicalName());

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
