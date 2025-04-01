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
	public void			acquire() throws Exception
	{
		((SemaphoreCI)this.getConnector()).acquire();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#acquire(int)
	 */
	@Override
	public void			acquire(int permits) throws Exception
	{
		((SemaphoreCI)this.getConnector()).acquire(permits);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#availablePermits()
	 */
	@Override
	public int			availablePermits() throws Exception
	{
		return ((SemaphoreCI)this.getConnector()).availablePermits();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#hasQueuedThreads()
	 */
	@Override
	public boolean		hasQueuedThreads() throws Exception
	{
		return ((SemaphoreCI)this.getConnector()).hasQueuedThreads();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#release()
	 */
	@Override
	public void			release() throws Exception
	{
		((SemaphoreCI)this.getConnector()).release();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#release(int)
	 */
	@Override
	public void			release(int permits) throws Exception
	{
		((SemaphoreCI)this.getConnector()).release(permits);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#tryAcquire()
	 */
	@Override
	public void			tryAcquire() throws Exception
	{
		((SemaphoreCI)this.getConnector()).tryAcquire();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#tryAcquire(int)
	 */
	@Override
	public void			tryAcquire(int permits) throws Exception
	{
		((SemaphoreCI)this.getConnector()).tryAcquire(permits);
	}
}
