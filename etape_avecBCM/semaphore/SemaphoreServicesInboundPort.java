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
	public void			acquire(String uri) throws Exception
	{
		this.owner.handleRequest(
			new AbstractComponent.AbstractService<Void>() {
				@Override
				public Void call() throws Exception {
					((SemaphoreI)this.getServiceOwner()).acquire(uri);
					return null;
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#acquire(int)
	 */
	@Override
	public void			acquire(String uri, int permits) throws Exception
	{
		this.owner.handleRequest(
			new AbstractComponent.AbstractService<Void>() {
				@Override
				public Void call() throws Exception {
					((SemaphoreI)this.getServiceOwner()).acquire(uri, permits);
					return null;
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#availablePermits()
	 */
	@Override
	public int			availablePermits(String uri) throws Exception
	{
		return this.owner.handleRequest(
			new AbstractComponent.AbstractService<Integer>() {
				@Override
				public Integer call() throws Exception {
					return ((SemaphoreI)this.getServiceOwner()).
															availablePermits(uri);
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#hasQueuedThreads()
	 */
	@Override
	public boolean		hasQueuedThreads(String uri) throws Exception
	{
		return this.owner.handleRequest(
			new AbstractComponent.AbstractService<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ((SemaphoreI)this.getServiceOwner()).
															hasQueuedThreads(uri);
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#release()
	 */
	@Override
	public void			release(String uri) throws Exception
	{
		this.owner.handleRequest(
			new AbstractComponent.AbstractService<Void>() {
				@Override
				public Void call() throws Exception {
					((SemaphoreI)this.getServiceOwner()).release(uri);
					return null;
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#release(int)
	 */
	@Override
	public void			release(String uri, int permits) throws Exception
	{
		this.owner.handleRequest(
			new AbstractComponent.AbstractService<Void>() {
				@Override
				public Void call() throws Exception {
					((SemaphoreI)this.getServiceOwner()).release(uri, permits);
					return null;
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#tryAcquire()
	 */
	@Override
	public boolean			tryAcquire(String uri) throws Exception
	{
		return this.owner.handleRequest(
			new AbstractComponent.AbstractService<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ((SemaphoreI)this.getServiceOwner()).tryAcquire(uri);
				}
			});
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.interfaces.SemaphoreServicesCI#tryAcquire(int)
	 */
	@Override
	public boolean			tryAcquire(String uri, int permits) throws Exception
	{
		return this.owner.handleRequest(
			new AbstractComponent.AbstractService<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ((SemaphoreI)this.getServiceOwner()).tryAcquire(uri, permits);
				}
			});
	}
}
