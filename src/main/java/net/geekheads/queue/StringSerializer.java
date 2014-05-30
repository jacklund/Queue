package net.geekheads.queue;

/**
 * {@link Serializer} instance for Strings. Simply returns the given string on serialization
 * and deserialization.
 * 
 * @author Jack Lund
 *
 */
public class StringSerializer implements Serializer<String> {

	public String deserialize(String messageData) throws SerializerException {
		return messageData;
	}

	public String serialize(String message) throws SerializerException {
		return message;
	}

}
