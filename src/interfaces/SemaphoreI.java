package interfaces;

public interface SemaphoreI {
	public void			acquire() throws Exception;
	public void			acquire(int permits) throws Exception;
	public int			availablePermits() throws Exception;
	public boolean		hasQueuedThreads() throws Exception;
	public void			release() throws Exception;
	public void			release(int permits) throws Exception;
	public void			tryAcquire() throws Exception;
	public void			tryAcquire(int permits) throws Exception;
}
