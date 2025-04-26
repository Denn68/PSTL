package semaphore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
	protected Map<String, Semaphore>						semaphoreMap;
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
		//this.init(permits);
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
		String semAvailabilityUri,
		ArrayList<String> semJetonUriList
		) throws Exception
	{
		super(reflectionInboundPortURI, 0, 0);
		
		this.semaphoreMap = new HashMap<String, Semaphore>();

		//assert	permits > 0 : new PreconditionException("permits > 0");
		this.init(/*permits*/semAvailabilityUri, semJetonUriList);
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
	protected void		init(/*int permits*/
							String semAvailabilityUri,
							ArrayList<String> semJetonUriList) throws Exception
	{
		this.semaphoreMap.put(semAvailabilityUri, new Semaphore(0));
		for (String sem : semJetonUriList) {
			this.semaphoreMap.put(sem, new Semaphore(1));
		}
		
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
	public void			acquire(String uri) throws Exception
	{
		this.semaphoreMap.get(uri).acquire();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#acquire(int)
	 */
	@Override
	public void			acquire(String uri, int permits) throws Exception
	{
		this.semaphoreMap.get(uri).acquire(permits);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#availablePermits()
	 */
	@Override
	public int			availablePermits(String uri) throws Exception
	{
		return this.semaphoreMap.get(uri).availablePermits();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#hasQueuedThreads()
	 */
	@Override
	public boolean		hasQueuedThreads(String uri) throws Exception
	{
		return this.semaphoreMap.get(uri).hasQueuedThreads();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#release()
	 */
	@Override
	public void			release(String uri) throws Exception
	{
		this.semaphoreMap.get(uri).release();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#release(int)
	 */
	@Override
	public void			release(String uri, int permits) throws Exception
	{
		this.semaphoreMap.get(uri).release(permits);
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#tryAcquire()
	 */
	@Override
	public boolean			tryAcquire(String uri) throws Exception
	{
		return this.semaphoreMap.get(uri).tryAcquire();
	}

	/**
	 * @see fr.sorbonne_u.components.ext.sync.components.SemaphoreI#tryAcquire(int)
	 */
	@Override
	public boolean			tryAcquire(String uri, int permits) throws Exception
	{
		return this.semaphoreMap.get(uri).tryAcquire(permits);
	}
}
