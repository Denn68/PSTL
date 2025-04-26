package semaphore;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.SemaphoreCI;

public class			SemaphoreServicesOutboundPort
extends		AbstractOutboundPort
implements	SemaphoreCI
{
	private static final long serialVersionUID = 1L;

	/**
	 * create the port.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no more precondition.
	 * post	{@code true}	// no more postcondition.
	 * </pre>
	 *
	 * @param owner			component owner of the port.
	 * @throws Exception	<i>to do</i>.
	 */
	public				SemaphoreServicesOutboundPort(ComponentI owner)
	throws Exception
	{
		super(SemaphoreCI.class, owner);
	}

	/**
	 * create the port.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code true}	// no more precondition.
	 * post	{@code true}	// no more postcondition.
	 * </pre>
	 *
	 * @param uri			URI of the port.
	 * @param owner			component owner of the port.
	 * @throws Exception	<i>to do</i>.
	 */
	public				SemaphoreServicesOutboundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, SemaphoreCI.class, owner);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#acquire()
	 */
	@Override
	public void			acquire(String uri) throws Exception
	{
		((SemaphoreCI)this.getConnector()).acquire(uri);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#acquire(int)
	 */
	@Override
	public void			acquire(String uri, int permits) throws Exception
	{
		((SemaphoreCI)this.getConnector()).acquire(uri, permits);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#availablePermits()
	 */
	@Override
	public int			availablePermits(String uri) throws Exception
	{
		return ((SemaphoreCI)this.getConnector()).availablePermits(uri);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#hasQueuedThreads()
	 */
	@Override
	public boolean		hasQueuedThreads(String uri) throws Exception
	{
		return ((SemaphoreCI)this.getConnector()).hasQueuedThreads(uri);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#release()
	 */
	@Override
	public void			release(String uri) throws Exception
	{
		((SemaphoreCI)this.getConnector()).release(uri);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#release(int)
	 */
	@Override
	public void			release(String uri, int permits) throws Exception
	{
		((SemaphoreCI)this.getConnector()).release(uri, permits);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#tryAcquire()
	 */
	@Override
	public void			tryAcquire(String uri) throws Exception
	{
		((SemaphoreCI)this.getConnector()).tryAcquire(uri);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#tryAcquire(int)
	 */
	@Override
	public void			tryAcquire(String uri, int permits) throws Exception
	{
		((SemaphoreCI)this.getConnector()).tryAcquire(uri, permits);
	}
}
