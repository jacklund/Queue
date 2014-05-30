package net.geekheads.queue;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Serializer which parses a JSON string into an object of type <T>
 * 
 * @author Jack Lund
 *
 * @param <T> type to be returned
 */
public class JSONSerializer<T> implements Serializer<T> {
	protected ObjectMapper mapper = new ObjectMapper();
	protected Class<T> type;

	/**
	 * Constructor.
	 * 
	 * @param t the class which this will be serializing/deserializing
	 */
	public JSONSerializer(Class<T> t) {
		type = t;
	}
	
	/**
	 * Add a <a href="http://wiki.fasterxml.com/JacksonMixInAnnotations">Mixin Annotation</a>
	 * class to the JSON parser. Applies the mixin to the class <T>
	 * 
	 * @param mixinClass type of the mixin class
	 */
	public void addMixinAnnotations(Class<?> mixinClass) {
		addMixinAnnotations(type, mixinClass);
	}
	
	/**
	 * Add a <a href="http://wiki.fasterxml.com/JacksonMixInAnnotations">Mixin Annotation</a>
	 * class to the JSON parser. Applies the mixin to the subclass of <T> passed in.
	 * 
	 * @param theType the type to apply the mixin class to
	 * @param mixinClass type of the mixin class
	 */
	public void addMixinAnnotations(Class<? extends T> theType, Class<?> mixinClass) {
		mapper.getSerializationConfig().addMixInAnnotations(theType, mixinClass);
		mapper.getDeserializationConfig().addMixInAnnotations(theType, mixinClass);
	}

	public T deserialize(String messageData) throws SerializerException {
		try {
			return mapper.readValue(messageData, type);
		} catch (IOException e) {
			throw new SerializerException(e);
		}
	}

	public String serialize(T message) throws SerializerException {
		try {
			return mapper.writeValueAsString(message);
		} catch (IOException e) {
			throw new SerializerException(e);
		}
	}
}
