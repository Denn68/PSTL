package semaphore;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.SemaphoreCI;
import interfaces.SemaphoreI;

public class			SemaphoreServicesInboundPort
extends		AbstractInboundPort
implements	SemaphoreCI
{
	private static final long serialVersionUID = 1L;

	/**
	 * create the port.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code owner != null and owner instanceof SemaphoreI}
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @param owner			component owner of the port.
	 * @throws Exception	<i>to do</i>.
	 */
	public				SemaphoreServicesInboundPort(ComponentI owner)
	throws Exception
	{
		super(SemaphoreCI.class, owner);

		assert	owner != null && owner instanceof SemaphoreI;
	}

	/**
	 * create the port.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code owner != null and owner instanceof SemaphoreI}
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @param uri			URI of the port.
	 * @param owner			component owner of the port.
	 * @throws Exception	<i>to do</i>.
	 */
	public				SemaphoreServicesInboundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, SemaphoreCI.class, owner);

		assert	owner != null && owner instanceof SemaphoreI;
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#acquire()
	 */
	@Override
	public void			acquire() throws Exception
	{
		this.owner.handleRequest(
			new AbstractComponent.AbstractService<Void>() {
				@Override
				public Void call() throws Exception {
					((SemaphoreI)this.getServiceOwner()).acquire();
					return null;
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#acquire(int)
	 */
	@Override
	public void			acquire(int permits) throws Exception
	{
		this.owner.handleRequest(
			new AbstractComponent.AbstractService<Void>() {
				@Override
				public Void call() throws Exception {
					((SemaphoreI)this.getServiceOwner()).acquire(permits);
					return null;
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#availablePermits()
	 */
	@Override
	public int			availablePermits() throws Exception
	{
		return this.owner.handleRequest(
			new AbstractComponent.AbstractService<Integer>() {
				@Override
				public Integer call() throws Exception {
					return ((SemaphoreI)this.getServiceOwner()).
															availablePermits();
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#hasQueuedThreads()
	 */
	@Override
	public boolean		hasQueuedThreads() throws Exception
	{
		return this.owner.handleRequest(
			new AbstractComponent.AbstractService<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ((SemaphoreI)this.getServiceOwner()).
															hasQueuedThreads();
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#release()
	 */
	@Override
	public void			release() throws Exception
	{
		this.owner.handleRequest(
			new AbstractComponent.AbstractService<Void>() {
				@Override
				public Void call() throws Exception {
					((SemaphoreI)this.getServiceOwner()).release();
					return null;
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#release(int)
	 */
	@Override
	public void			release(int permits) throws Exception
	{
		this.owner.handleRequest(
			new AbstractComponent.AbstractService<Void>() {
				@Override
				public Void call() throws Exception {
					((SemaphoreI)this.getServiceOwner()).release(permits);
					return null;
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#tryAcquire()
	 */
	@Override
	public void			tryAcquire() throws Exception
	{
		this.owner.handleRequest(
			new AbstractComponent.AbstractService<Void>() {
				@Override
				public Void call() throws Exception {
					((SemaphoreI)this.getServiceOwner()).tryAcquire();
					return null;
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#tryAcquire(int)
	 */
	@Override
	public void			tryAcquire(int permits) throws Exception
	{
		this.owner.handleRequest(
			new AbstractComponent.AbstractService<Void>() {
				@Override
				public Void call() throws Exception {
					((SemaphoreI)this.getServiceOwner()).tryAcquire(permits);
					return null;
				}
			});
	}
}
