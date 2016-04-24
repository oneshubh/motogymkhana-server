package eu.motogymkhana.server.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Provider;

/**
 * Provider for jackson object mapper so we can inject the mapper.
 * 
 * @author christine
 * 
 */
public class ObjectMapperProvider implements Provider<ObjectMapper> {

	private static ObjectMapper mapper = new ObjectMapper() {
		{
			configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
	};

	public ObjectMapper get() {
		return mapper;
	}
}
