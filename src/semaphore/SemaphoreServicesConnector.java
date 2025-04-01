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
	public void			acquire() throws Exception
	{
		((SemaphoreCI)this.offering).acquire();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#acquire(int)
	 */
	@Override
	public void			acquire(int permits) throws Exception
	{
		((SemaphoreCI)this.offering).acquire(permits);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#availablePermits()
	 */
	@Override
	public int			availablePermits() throws Exception
	{
		return ((SemaphoreCI)this.offering).availablePermits();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#hasQueuedThreads()
	 */
	@Override
	public boolean		hasQueuedThreads() throws Exception
	{
		return ((SemaphoreCI)this.offering).hasQueuedThreads();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#release()
	 */
	@Override
	public void			release() throws Exception
	{
		((SemaphoreCI)this.offering).release();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#release(int)
	 */
	@Override
	public void			release(int permits) throws Exception
	{
		((SemaphoreCI)this.offering).release(permits);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#tryAcquire()
	 */
	@Override
	public void			tryAcquire() throws Exception
	{
		((SemaphoreCI)this.offering).tryAcquire();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreCI#tryAcquire(int)
	 */
	@Override
	public void			tryAcquire(int permits) throws Exception
	{
		((SemaphoreCI)this.offering).tryAcquire(permits);
	}
}