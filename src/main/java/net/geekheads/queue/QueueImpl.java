package net.geekheads.queue;

/**
 * Queue implementation interface. Implementations should subclass this class, and then they are
 * passed into the constructor for the {@link Queue} class.
 * 
 * @author Jack Lund
 *
 */
public interface QueueImpl {
	public static final long BLOCK_INDEFINITELY = -1;

	long getDefaultQueueTimeout();
	
	/**
	 * Return the next string from the queue, or timeout after a certain interval.
	 * This call should block the given number of seconds. If nothing is on the queue after that time,
	 * it should return null.
	 * 
	 * @param timeout the timeout in seconds. If timeout is {@link #BLOCK_INDEFINITELY}, then
	 * block indefinitely.
	 * 
	 * @return the string, or null if the timeout expired
	 * @throws QueueException if there's an error retrieving the item from the queue
	 */
	String get(long timeout) throws QueueException;
	
	/**
	 * Put a string onto the queue, within the given number of seconds. If the string can't be put
	 * onto the queue in that time, throw an exception.
	 * 
	 * @param value the string value
	 * @param timeout the timeout in seconds. If timeout is {@link #BLOCK_INDEFINITELY}, then
	 * block indefinitely.
	 * @throws QueueTimeoutException if the string can't be put on the queue within {@code timeout}
	 * seconds
	 * @throws QueueException if the implementation throws an exception. The underlying exception
	 * can be retrieved by calling {@code QueueException#getCause()}.
	 */
	void put(String value, long timeout) throws QueueTimeoutException, QueueException;
	
	/**
	 * Put a string on the queue, potentially blocking indefinitely. This is the same as calling
	 * {@link #put(String, int)} with a timeout value of {@link #BLOCK_INDEFINITELY}.
	 * 
	 * @param value the string value
	 */
	void put(String value);
	
	/**
	 * Shut down the queue. May be a no-op.
	 */
	void shutdown();
}
