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
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.request.UpdateRiderRequest;
import eu.motogymkhana.server.api.response.UpdateRiderResponse;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Registration;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Times;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.persist.MyEntityManager;
import eu.motogymkhana.server.resource.UpdateRiderResource;

public class UpdateRiderServerResource extends ServerResource implements UpdateRiderResource {

	@Inject
	private RiderDao riderDao;

	@Inject
	private PasswordManager pwManager;

	@Inject
	private MyEntityManager emp;

	@InjectLogger
	private Log log;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post("json")
	public UpdateRiderResponse updateRider(UpdateRiderRequest request) {

		UpdateRiderResponse response = new UpdateRiderResponse();

		if (!pwManager.checkPassword(request.getCountry(), request.getPassword())) {
			response.setStatus(404);
			return response;
		}

		EntityManager em = emp.getEM();
		em.clear();

		em.getTransaction().begin();

		log.debug("update rider " + request.getRider().getFullName());
		
		try {

			Rider resultRider = riderDao.updateRider(request.getRider());

			response.setStatus(0);
			response.setRider(resultRider);

			em.getTransaction().commit();

			log.debug("rider updated: " + resultRider.getFullName());

		} catch (Exception e) {
			e.printStackTrace();
			try {
				em.getTransaction().rollback();
			} catch (Exception ee) {

			}
		}

		return response;
	}

}
