package net.geekheads.queue;

import static org.junit.Assert.*;
import net.geekheads.queue.QueueException;
import net.geekheads.queue.QueueTimeoutException;
import net.geekheads.queue.TimedQueueImpl;

import org.junit.Test;

/**
 * Unit test for {@link TimedQueueImpl}. Tests that the timeout implementation
 * works as advertised.
 * 
 * @author Jack Lund
 *
 */
public class TimedQueueImplTest {
	private static final int TIMEOUT = 3;

	private static class MyQueueImpl extends TimedQueueImpl {

		public long getDefaultQueueTimeout() {
			// TODO Auto-generated method stub
			return 0;
		}

		public String get(long timeout) throws QueueException {
			// TODO Auto-generated method stub
			return null;
		}

		public void put(String value) {
			try {
				// Make sure put times out
				Thread.sleep((TIMEOUT + 2) * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void shutdown() {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Test
	public void testPut() throws QueueException {
		MyQueueImpl queue = new MyQueueImpl();
		long start = System.currentTimeMillis();
		try {
			queue.put("Foo", TIMEOUT);
			fail("Shouldn't have gotten here");
		} catch (QueueTimeoutException e) {
			long stop = System.currentTimeMillis();
			// Allow for a 30 millisecond "fudge factor"
			assertEquals(TIMEOUT * 1000, stop - start, 30);
		}
	}

}
