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

import eu.motogymkhana.server.api.request.SigninRiderRequest;
import eu.motogymkhana.server.api.result.RegisterRiderResult;
import eu.motogymkhana.server.api.result.SigninRiderResult;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.resource.ui.SigninRiderResource;

public class SigninRiderServerResource extends ServerResource implements SigninRiderResource {

	@Inject
	private PasswordManager passwordManager;

	@Inject
	private Provider<EntityManager> emp;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post
	public SigninRiderResult signin(SigninRiderRequest request) {

		SigninRiderResult result = new SigninRiderResult();
		result.setResultCode(-1);
		boolean check = false;

		EntityManager em = emp.get();

		em.getTransaction().begin();

		try {

			check = passwordManager.checkRiderPassword(request.getEmail(), request.getPassword());

			result.setResultCode(check ? RegisterRiderResult.OK : RegisterRiderResult.NOT_OK);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}

		return result;
	}
}
