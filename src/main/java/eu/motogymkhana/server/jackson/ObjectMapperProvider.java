/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
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
