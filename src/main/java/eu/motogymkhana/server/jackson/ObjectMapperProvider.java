/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
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

	private static ObjectMapper mapper;

	public ObjectMapper get() {
		if (mapper == null) {
			createMapper();
		}
		return mapper;
	}

	private void createMapper() {

		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		// mapper.setSerializationInclusion(Include.NON_EMPTY);
		// mapper.registerModule(new SimpleModule() {
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void setupModule(SetupContext context) {
		// super.setupModule(context);
		// context.addBeanSerializerModifier(new BeanSerializerModifier() {
		// @Override
		// public JsonSerializer<?> modifySerializer(SerializationConfig config,
		// BeanDescription desc, JsonSerializer<?> serializer) {
		// if (Hidable.class.isAssignableFrom(desc.getBeanClass())) {
		// return new HidableSerializer((JsonSerializer<Object>) serializer);
		// }
		// return serializer;
		// }
		// });
		// }
		// });
	}
}
