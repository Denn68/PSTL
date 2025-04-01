package semaphore;

import java.util.concurrent.Semaphore;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.exceptions.PreconditionException;
import interfaces.SemaphoreI;
import interfaces.SemaphoreCI;

@OfferedInterfaces(offered = {SemaphoreCI.class})
//-----------------------------------------------------------------------------
public class			SemaphoreComponent
extends		AbstractComponent
implements	SemaphoreI
{
	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	/** The Java semaphore object to which all calls are delegated.			*/
	protected Semaphore						semaphore;
	/** The inbound port exposing the semaphore services.					*/
	protected SemaphoreServicesInboundPort	ssip;

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * create a semaphore component with the given number of permits as a
	 * passive BCM component.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code permits > 0}
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @param permits		number of permits in the semaphore.
	 * @throws Exception	<i>to do</i>.
	 */
	protected			SemaphoreComponent(
		int permits
		) throws Exception
	{
		super(0, 0);

		assert	permits > 0 : new PreconditionException("permits > 0");
		this.init(permits);
	}

	/**
	 * create a semaphore component with the given reflection inbound port URI
	 * and number of permits as a passive BCM component.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code permits > 0}
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @param reflectionInboundPortURI	URI of the reflection inbound port of the component.
	 * @param permits					number of permits in the semaphore.
	 * @throws Exception				<i>to do</i>.
	 */
	protected			SemaphoreComponent(
		String reflectionInboundPortURI,
		int permits
		) throws Exception
	{
		super(reflectionInboundPortURI, 0, 0);

		assert	permits > 0 : new PreconditionException("permits > 0");
		this.init(permits);
	}

	/**
	 * initialise the semaphore component.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	{@code permits > 0}
	 * post	{@code true}	// no postcondition.
	 * </pre>
	 *
	 * @param permits		number of permits in the semaphore.
	 * @throws Exception	<i>to do</i>.
	 */
	protected void		init(int permits) throws Exception
	{
		this.semaphore = new Semaphore(permits);
		this.ssip = new SemaphoreServicesInboundPort(this);
		this.ssip.publishPort();
	}

	// -------------------------------------------------------------------------
	// Life-cycle methods
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
	 */
	@Override
	public void			shutdown() throws ComponentShutdownException
	{
		try {
			this.ssip.unpublishPort();
			this.removeOfferedInterface(SemaphoreCI.class);
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}

		super.shutdown();
	}

	// -------------------------------------------------------------------------
	// Service methods
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#acquire()
	 */
	@Override
	public void			acquire() throws Exception
	{
		this.semaphore.acquire();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#acquire(int)
	 */
	@Override
	public void			acquire(int permits) throws Exception
	{
		this.semaphore.acquire(permits);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#availablePermits()
	 */
	@Override
	public int			availablePermits() throws Exception
	{
		return this.semaphore.availablePermits();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#hasQueuedThreads()
	 */
	@Override
	public boolean		hasQueuedThreads() throws Exception
	{
		return this.semaphore.hasQueuedThreads();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#release()
	 */
	@Override
	public void			release() throws Exception
	{
		this.semaphore.release();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#release(int)
	 */
	@Override
	public void			release(int permits) throws Exception
	{
		this.semaphore.release(permits);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#tryAcquire()
	 */
	@Override
	public void			tryAcquire() throws Exception
	{
		this.semaphore.tryAcquire();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#tryAcquire(int)
	 */
	@Override
	public void			tryAcquire(int permits) throws Exception
	{
		this.semaphore.tryAcquire(permits);
	}
}
