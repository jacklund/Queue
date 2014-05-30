package net.geekheads.queue;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonRootName;

import redis.clients.jedis.Jedis;

/**
 * <a href="http://redis.io/">Redis</a> implementation of the {@link Queue}. Uses
 * <a href="https://github.com/xetorthio/jedis">Jedis</a> as the Redis client.
 * 
 * @author Jack Lund
 *
 */
@JsonRootName(value = "redisQueueImpl")
public class RedisQueueImpl extends TimedQueueImpl {
	private String queueName;
	private String hostname;
	private Jedis jedis;

	/**
	 * Constructor.
	 * @param hostName Host name of host Redis server is running on
	 * @param qName Redis key of the "queue"
	 */
	public RedisQueueImpl(String hostName, String qName) {
		hostname = hostName;
		jedis = new Jedis(hostName);
		queueName = qName;
	}
	
	public RedisQueueImpl() {}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String get(long timeout) {
		List<String> keyValue = jedis.brpop((int) timeout, queueName);
		return keyValue.get(1);
	}

	public void put(String value) {
		jedis.lpush(queueName, value);
	}

	@JsonIgnore
	public long getDefaultQueueTimeout() {
		// Note: In Redis, "block indefinitely" is zero
		return 0;
	}

	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
}
