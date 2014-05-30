package net.geekheads.queue;

import static org.junit.Assert.*;

import org.codehaus.jackson.annotate.JsonProperty;
import org.junit.Before;
import org.junit.Test;

import net.geekheads.queue.JSONSerializer;
import net.geekheads.queue.SerializerException;

/**
 * Unit test to test the {@link JSONSerializer} class
 * 
 * @author Jack Lund
 *
 */
public class JSONSerializerTest {
	private static final String SERIALIZED = "{\"a\":5,\"b\":3.1415,\"c\":\"Foo\"}";
	private static final String SERIALIZED_WITH_MIXIN = "{\"int\":5,\"double\":3.1415,\"string\":\"Foo\"}";
	private JSONSerializer<SerializeMe> serializer = new JSONSerializer<SerializeMe>(SerializeMe.class);
	private SerializeMe s;
	
	private static class SerializeMe {
		private int a;
		private double b;
		private String c;
		
		@SuppressWarnings("unused")
		public int getA() {
			return a;
		}

		public void setA(int a) {
			this.a = a;
		}
		
		@SuppressWarnings("unused")
		public double getB() {
			return b;
		}
		
		public void setB(double b) {
			this.b = b;
		}
		
		@SuppressWarnings("unused")
		public String getC() {
			return c;
		}
		
		public void setC(String c) {
			this.c = c;
		}

		@Override
		public boolean equals(Object obj) {
			if (!obj.getClass().equals(getClass())) return false;
			SerializeMe o = (SerializeMe) obj;
			return a == o.a && b == o.b && c.equals(o.c);
		}
	}

	private static abstract class Mixin {
		@JsonProperty(value = "int")
		public abstract int getA();

		@JsonProperty(value = "double")
		public abstract int getB();
		
		@JsonProperty(value = "string")
		public abstract int getC();
	}
	@Before
	public void setUp() throws Exception {
		s = new SerializeMe();
		s.setA(5);
		s.setB(3.1415);
		s.setC("Foo");
	}

	@Test
	public void testAddMixinAnnotationsClassOfQextendsTClassOfQ() throws SerializerException {
		serializer.addMixinAnnotations(SerializeMe.class, Mixin.class);
		String serialized = serializer.serialize(s);
		assertEquals(SERIALIZED_WITH_MIXIN, serialized);
	}

	@Test
	public void testDeserialize() throws SerializerException {
		SerializeMe deserialized = serializer.deserialize(SERIALIZED);
		assertEquals(deserialized, s);
	}

	@Test
	public void testSerialize() throws SerializerException {
		String serialized = serializer.serialize(s);
		assertEquals(SERIALIZED, serialized);
	}

}
