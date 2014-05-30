package net.geekheads.queue;

/**
 * Exception class for {@link Queue}s.
 * 
 * @author Jack Lund
 *
 */
public class QueueException extends Exception {
	private static final long serialVersionUID = 4742190308184165870L;

	public QueueException() {
	}

	public QueueException(String message) {
		super(message);
	}

	public QueueException(Throwable cause) {
		super(cause);
	}

	public QueueException(String message, Throwable cause) {
		super(message, cause);
	}

	public QueueException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
