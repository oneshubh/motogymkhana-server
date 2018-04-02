/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
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

import eu.motogymkhana.server.api.request.UploadSettingsRequest;
import eu.motogymkhana.server.api.response.UpdateSettingsResponse;
import eu.motogymkhana.server.dao.SettingsDao;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.persist.MyEntityManager;
import eu.motogymkhana.server.resource.UpdateSettingsResource;

public class UpdateSettingsServerResource extends ServerResource implements UpdateSettingsResource {

	@Inject
	private MyEntityManager emp;

	@Inject
	private SettingsDao settingsDao;
	
	@Inject
	private PasswordManager pwManager;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post
	public UpdateSettingsResponse updateSettings(UploadSettingsRequest request) {
		
		UpdateSettingsResponse response = new UpdateSettingsResponse();

		if(!pwManager.checkPassword(request.getCountry(), request.getPassword())){	
			response.setStatus(404);
			return response;
		}

		EntityManager em = emp.getEM();
		em.clear();
		em.getTransaction().begin();

		try {
			
			settingsDao.store(request.getSettings());
			em.getTransaction().commit();
			response.setStatus(200);
			
		} catch (Exception e) {
			response.setStatus(500);
			try {
				em.getTransaction().rollback();
			} catch (Exception ee) {
			}
		}
		
		return response;
	}
}
