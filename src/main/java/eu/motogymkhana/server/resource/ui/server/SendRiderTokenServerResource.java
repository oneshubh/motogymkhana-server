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

import eu.motogymkhana.server.api.request.TokenRequest;
import eu.motogymkhana.server.api.result.RiderTokenResult;
import eu.motogymkhana.server.dao.RiderAuthDao;
import eu.motogymkhana.server.model.RiderAuth;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.resource.ui.SendRiderTokenResource;

public class SendRiderTokenServerResource extends ServerResource implements SendRiderTokenResource {

	@Inject
	private PasswordManager passwordManager;

	@Inject
	private RiderAuthDao riderAuthDao;

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
	public RiderTokenResult sendToken(TokenRequest request) {

		RiderTokenResult result = new RiderTokenResult();
		result.setResultCode(0);

		if (request.getEmail() == null || request.getToken() == null) {
			result.setResultCode(-1);
			return result;
		}
		
		EntityManager em = emp.get();

		em.getTransaction().begin();

		try {
			
			RiderAuth auth = riderAuthDao.get(request.getEmail());
			
			if(auth == null || !auth.hasToken() || !tokenStillValid(auth)){
				result.setResultCode(-1);
				return result;
			}

			if (request.getToken().equals(auth.getToken())) {
				result.setResultCode(passwordManager.createRiderAccount(request));

			} else {
				result.setResultCode(-1);
			}

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}

		return result;
	}

	private boolean tokenStillValid(RiderAuth riderAuth) {
		return (System.currentTimeMillis() - riderAuth.getTimeStamp()) < tokenValidity;
	}
}
