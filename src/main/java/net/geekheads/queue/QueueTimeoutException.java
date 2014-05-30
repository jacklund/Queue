package net.geekheads.queue;

/**
 * Exception thrown by a {@link Queue} when a timeout is exceeded.
 * 
 * @author Jack Lund
 *
 */
public class QueueTimeoutException extends QueueException {
	private static final long serialVersionUID = -5326486939222707491L;

	public QueueTimeoutException() {
	}

	public QueueTimeoutException(String message) {
		super(message);
	}

	public QueueTimeoutException(Throwable cause) {
		super(cause);
	}

	public QueueTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public QueueTimeoutException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
