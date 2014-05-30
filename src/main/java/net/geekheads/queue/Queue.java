package net.geekheads.queue;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * Queue class. The queue is assumed to contain strings,
 * which are converted to objects of class T using the passed-in {@link Serializer}.
 * <p>
 * The actual queue implementation is contained in a subclass of {@link QueueImpl}.
 * 
 * @author Jack Lund
 *
 */
public class Queue<T> {
	public static final long BLOCK_INDEFINITELY = QueueImpl.BLOCK_INDEFINITELY;
	
	protected Serializer<T> serializer;
	protected QueueImpl impl;
	
	/**
	 * Constructor.
	 * @param impl the {@link QueueImpl} instance to implement the queue
	 * @param s the {@link Serializer} to use to convert the strings from the
	 * queue to instances of T
	 */
	public Queue(QueueImpl impl, Serializer<T> s) {
		serializer = s;
		this.impl = impl;
	}

	/**
	 * Retrieve a message from the queue.
	 * 
	 * @param timeout if no message is on the queue after timeout seconds, return null. A timeout
	 * value of {@link BLOCK_INDEFINITELY} means to block indefinitely
	 * 
	 * @return the message
	 * @throws QueueException 
	 */
	public T get(long timeout) throws QueueException, SerializerException {
		return serializer.deserialize(impl.get(timeout));
	}

	/**
	 * Put a message on the queue.
	 * 
	 * @param message the message
	 * @param timeout if {@code timeout} seconds elapse and the message hasn't been put on the queue,
	 * throw a {@code QueueTimeoutException}. A timeout value of {@link BLOCK_INDEFINITELY} means
	 * to block indefinitely
	 * @throws QueueTimeoutException if the message can't be put on the queue in the timeout specified
	 * @throws QueueException if the underlying queue implementation throws an exception. The underlying
	 * exception can be retrieved by calling {@link QueueException#getCause()}
	 */
	public void put(T message, long timeout) throws QueueTimeoutException, QueueException {
		try {
			impl.put(serializer.serialize(message), timeout);
		} catch (SerializerException e) {
			throw new QueueException(e);
		}
	}
	
	/**
	 * Put a message on the queue.
	 * 
	 * @param message the message
	 * @throws QueueException if the underlying queue implementation throws an exception. The underlying
	 * exception can be retrieved by calling {@link QueueException#getCause()}
	 */
	public void put(T message) throws QueueException {
		try {
			impl.put(serializer.serialize(message));
		} catch (SerializerException e) {
			throw new QueueException(e);
		}
	}

	/**
	 * Return the {@link QueueImpl} instance used to construct this object.
	 * 
	 * @return the queue impl
	 */
	public QueueImpl getQueueImpl() {
		return impl;
	}
	
	public Serializer<T> getSerializer() {
		return serializer;
	}

	/**
	 * Shut down the queue. Should always be called when finished with the queue.
	 */
	public void shutdown() {
		impl.shutdown();
	}
	
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		RedisQueueImpl impl = new RedisQueueImpl();
		Queue<String> queue = new Queue<String>(impl, new StringSerializer());
		impl.setHostname("foo");
		impl.setQueueName("bar");
		try {
			System.out.println(mapper.writeValueAsString(queue));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
