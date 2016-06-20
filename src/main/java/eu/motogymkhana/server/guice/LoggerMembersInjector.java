/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.guice;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.MembersInjector;

class LoggerMembersInjector<T> implements MembersInjector<T> {
	private final Field field;
	private final Log logger;

	LoggerMembersInjector(Field field) {
		this.field = field;
		this.logger = LogFactory.getLog(field.getDeclaringClass());
		field.setAccessible(true);
	}

	public void injectMembers(T t) {
		try {
			field.set(t, logger);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
