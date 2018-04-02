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

import eu.motogymkhana.server.api.request.GymkhanaRequest;
import eu.motogymkhana.server.api.result.SettingsResult;
import eu.motogymkhana.server.dao.SettingsDao;
import eu.motogymkhana.server.persist.MyEntityManager;
import eu.motogymkhana.server.resource.GetSettingsResource;
import eu.motogymkhana.server.settings.Settings;

public class GetSettingsServerResource extends ServerResource implements GetSettingsResource {

	@Inject
	private SettingsDao settingsDao;

	@Inject
	private MyEntityManager emp;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post
	public SettingsResult getSettings(GymkhanaRequest request) {

		SettingsResult result = new SettingsResult();
		result.setResultCode(0);
		Settings settings = null;
		
		EntityManager em = emp.getEM();
		em.clear();

		em.getTransaction().begin();

		try {

			settings = settingsDao.getSettings(request.getCountry(), request.getSeason());
			result.setResultCode(200);
			em.getTransaction().commit();

		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception ee) {

			}
		}

		result.setSettings(settings);
		return result;
	}
}
