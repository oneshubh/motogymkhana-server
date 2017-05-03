/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.resource.server;

import javax.persistence.EntityManager;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.request.UpdateRegistrationRequest;
import eu.motogymkhana.server.api.response.UpdateRegistrationResponse;
import eu.motogymkhana.server.dao.RegistrationDao;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.persist.MyEntityManager;
import eu.motogymkhana.server.resource.UpdateRegistrationResource;

public class UpdateRegistrationServerResource extends ServerResource implements UpdateRegistrationResource {

	@Inject
	private RegistrationDao registrationDao;
	
	@Inject
	private PasswordManager pwManager;

	@Inject
	private MyEntityManager emp;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post("json")
	public UpdateRegistrationResponse updateRegistration(UpdateRegistrationRequest request) {

		UpdateRegistrationResponse response = new UpdateRegistrationResponse();
		
		if(!pwManager.checkPassword(request.getCountry(), request.getPassword())){	
			response.setStatus(404);
			return response;
		}

		EntityManager em = emp.getEM();
		em.clear();

		em.getTransaction().begin();

		try {

			int result = registrationDao.updateRegistration(request.getRegistration());

			response.setStatus(result);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}

		return response;
	}

}
