package net.geekheads.queue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Base class for {@link QueueImpl}s which don't have their own timeout available, and which
 * need to explicitly set a timeout. Uses an {@link ExecutorService} and a {@link Future} to
 * simulate the timeout.
 * 
 * @author Jack Lund
 *
 */
public abstract class TimedQueueImpl implements QueueImpl {
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Future<?> future;
	
	public void put(final String value, long timeout) throws QueueTimeoutException, QueueException {
		if (timeout == BLOCK_INDEFINITELY) {
			put(value);
		} else {
			future = executor.submit(new Runnable() {
				public void run() {
					put(value);
				}});
			try {
				future.get(timeout, TimeUnit.SECONDS);
			} catch (TimeoutException e) {
				throw new QueueTimeoutException(e);
			} catch (InterruptedException e) {
				throw new QueueException(e);
			} catch (ExecutionException e) {
				throw new QueueException(e.getCause());
			}
		}
	}
}
