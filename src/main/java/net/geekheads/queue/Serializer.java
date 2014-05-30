package net.geekheads.queue;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

/**
 * Serializer/Deserializer for message data
 * 
 * @author Jack Lund
 *
 */
@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="type")
public interface Serializer<T> {
	/**
	 * Deserialize a String into a T
	 * 
	 * @param messageData string data
	 * 
	 * @return an instance of the given object
	 * @throws SerializerException if there is an error
	 */
	T deserialize(String messageData) throws SerializerException;
	
	/**
	 * Serialize a T into a String
	 * 
	 * @param message the message data as an object
	 * @return the serialized string
	 * @throws SerializerException if there is an error doing the serialization
	 */
	String serialize(T message) throws SerializerException;
}
