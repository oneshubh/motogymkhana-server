/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.resource.server;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.api.UpdateRiderRequest;
import eu.motogymkhana.server.api.UpdateRiderResponse;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.resource.DeleteRiderResource;
import eu.motogymkhana.server.resource.UpdateRiderResource;

public class DeleteRiderServerResource extends ServerResource implements DeleteRiderResource {

	@Inject
	private RiderDao riderDao;
	
	@Inject
	private PasswordManager pwManager;

	@Inject
	private Provider<EntityManager> emp;
	
	@InjectLogger
	private Log log;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post("json")
	public UpdateRiderResponse deleteRider(UpdateRiderRequest request) {

		UpdateRiderResponse response = new UpdateRiderResponse();
		
		if(!pwManager.checkPassword(request.getCountry(), request.getPassword())){	
			response.setStatus(404);
			return response;
		}

		EntityManager em = emp.get();

		em.getTransaction().begin();

		try {

			int result = riderDao.deleteRider(request.getRider());
			log.debug("delete rider result " + result);

			response.setStatus(result);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}

		return response;
	}

}
