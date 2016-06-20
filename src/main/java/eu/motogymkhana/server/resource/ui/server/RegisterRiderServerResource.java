/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.resource.ui.server;

import javax.persistence.EntityManager;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.api.RegisterRiderRequest;
import eu.motogymkhana.server.api.RegisterRiderResult;
import eu.motogymkhana.server.dao.RiderAuthDao;
import eu.motogymkhana.server.model.RiderAuth;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.resource.ui.RegisterRiderResource;

public class RegisterRiderServerResource extends ServerResource implements RegisterRiderResource {

	@Inject
	private RiderAuthDao riderAuthDao;

	@Inject
	private PasswordManager passwordManager;

	@Inject
	private Provider<EntityManager> emp;

	// 3 days
	private long tokenValidity = 259200000l;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post
	public RegisterRiderResult register(RegisterRiderRequest request) {

		RegisterRiderResult result = new RegisterRiderResult();
		result.setResultCode(-1);
		boolean check = false;

		EntityManager em = emp.get();

		em.getTransaction().begin();

		try {

			RiderAuth riderAuth = riderAuthDao.get(request.getEmail());

			if (request.getToken() != null && riderAuth.getToken() != null
					&& tokenStillValid(riderAuth)
					&& request.getToken().equals(riderAuth.getToken())) {

				if (request.getPassword() != null && request.getPassword().length() > 5) {
						riderAuth.setPasswordHash(passwordManager.createHash(request.getPassword()));
						riderAuth.removeToken();
						check = true;
				}
			}

			result.setResultCode(check ? RegisterRiderResult.OK : RegisterRiderResult.NOT_OK);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		return result;
	}

	private boolean tokenStillValid(RiderAuth riderAuth) {
		return (System.currentTimeMillis() - riderAuth.getTimeStamp()) < tokenValidity ;
	}
}
