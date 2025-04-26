package interfaces;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface		SemaphoreCI
extends		OfferedCI,
			RequiredCI,
			SemaphoreI
{
	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#acquire()
	 */
	@Override
	public void			acquire(String uri) throws Exception;

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#acquire(int)
	 */
	@Override
	public void			acquire(String uri, int permits) throws Exception;

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#availablePermits()
	 */
	@Override
	public int			availablePermits(String uri) throws Exception;

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#hasQueuedThreads()
	 */
	@Override
	public boolean		hasQueuedThreads(String uri) throws Exception;

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#release()
	 */
	@Override
	public void			release(String uri) throws Exception;

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#release(int)
	 */
	@Override
	public void			release(String uri, int permits) throws Exception;

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#tryAcquire()
	 */
	@Override
	public boolean			tryAcquire(String uri) throws Exception;

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#tryAcquire(int)
	 */
	@Override
	public boolean			tryAcquire(String uri, int permits) throws Exception;
}
