/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.persist;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.PersistService;

/**
 * http://code.google.com/p/google-guice/wiki/JPA
 * 
 * @author christine
 * 
 */
public class PersistenceInitializer {

	@Inject
	PersistenceInitializer(PersistService service, Provider<EntityManager> emp) {

		service.start();
	}
}
