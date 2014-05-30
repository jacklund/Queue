# Queue Project
This project is a simple queue interface which can be used with a variety of implementations. The items which are queued are strings, which are serialized/deserialized using a pluggable ``Serializer`` class.
## Example
    :::java
    // Use REDIS for queue
    RedisQueueImpl impl = new RedisQueueImpl();
	impl.setHostname("foo");
	impl.setQueueName("bar");
    Queue<String> queue = new Queue<String>(
      impl,
      new StringSerializer()); // No-op serializer
    String msg = queue.get();
    // Do something with the message
    String newMsg = createMessage();
    queue.put(newMsg);
