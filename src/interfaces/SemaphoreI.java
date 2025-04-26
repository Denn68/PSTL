package interfaces;

public interface SemaphoreI {
	public void			acquire(String uri) throws Exception;
	public void			acquire(String uri, int permits) throws Exception;
	public int			availablePermits(String uri) throws Exception;
	public boolean		hasQueuedThreads(String uri) throws Exception;
	public void			release(String uri) throws Exception;
	public void			release(String uri, int permits) throws Exception;
	public void			tryAcquire(String uri) throws Exception;
	public void			tryAcquire(String uri, int permits) throws Exception;
}
