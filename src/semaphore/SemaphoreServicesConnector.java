package semaphore;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.SemaphoreCI;

public class			SemaphoreServicesConnector
extends		AbstractConnector
implements	SemaphoreCI
{
	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#acquire()
	 */
	@Override
	public void			acquire(String uri) throws Exception
	{
		((SemaphoreCI)this.offering).acquire(uri);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#acquire(int)
	 */
	@Override
	public void			acquire(String uri, int permits) throws Exception
	{
		((SemaphoreCI)this.offering).acquire(uri, permits);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#availablePermits()
	 */
	@Override
	public int			availablePermits(String uri) throws Exception
	{
		return ((SemaphoreCI)this.offering).availablePermits(uri);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#hasQueuedThreads()
	 */
	@Override
	public boolean		hasQueuedThreads(String uri) throws Exception
	{
		return ((SemaphoreCI)this.offering).hasQueuedThreads(uri);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#release()
	 */
	@Override
	public void			release(String uri) throws Exception
	{
		((SemaphoreCI)this.offering).release(uri);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#release(int)
	 */
	@Override
	public void			release(String uri, int permits) throws Exception
	{
		((SemaphoreCI)this.offering).release(uri, permits);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#tryAcquire()
	 */
	@Override
	public void			tryAcquire(String uri) throws Exception
	{
		((SemaphoreCI)this.offering).tryAcquire(uri);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#tryAcquire(int)
	 */
	@Override
	public void			tryAcquire(String uri, int permits) throws Exception
	{
		((SemaphoreCI)this.offering).tryAcquire(uri, permits);
	}
}