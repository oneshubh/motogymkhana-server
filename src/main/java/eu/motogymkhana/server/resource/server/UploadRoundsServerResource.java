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
import com.google.inject.Provider;

import eu.motogymkhana.server.api.UploadRoundsRequest;
import eu.motogymkhana.server.api.UploadRoundsResponse;
import eu.motogymkhana.server.dao.RoundDao;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.resource.UploadRoundsResource;

public class UploadRoundsServerResource extends ServerResource implements UploadRoundsResource {

	@Inject
	private RoundDao roundDao;
	
	@Inject
	private PasswordManager pwManager;

	@Inject
	private Provider<EntityManager> emp;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post("json")
	public UploadRoundsResponse uploadRounds(UploadRoundsRequest request) {

		UploadRoundsResponse response = new UploadRoundsResponse();
		
		if(!pwManager.checkPassword(request.getCountry(), request.getPassword())){	
			response.setStatus(404);
			return response;
		}

		EntityManager em = emp.get();

		em.getTransaction().begin();

		try {

			response.setStatus(roundDao.storeRounds(request.getRounds()));
			
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}

		return response;
	}

}
