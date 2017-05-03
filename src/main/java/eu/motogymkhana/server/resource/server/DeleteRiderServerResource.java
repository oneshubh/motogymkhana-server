/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.resource.server;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.request.UpdateRiderRequest;
import eu.motogymkhana.server.api.response.UpdateRegistrationResponse;
import eu.motogymkhana.server.dao.RiderAuthDao;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.persist.MyEntityManager;
import eu.motogymkhana.server.resource.DeleteRiderResource;

public class DeleteRiderServerResource extends ServerResource implements DeleteRiderResource {

	@Inject
	private RiderDao riderDao;

	@Inject
	private RiderAuthDao riderAuthDao;

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
	public UpdateRegistrationResponse deleteRider(UpdateRiderRequest request) {

		UpdateRegistrationResponse response = new UpdateRegistrationResponse();

		if (request.getRider() == null) {
			response.setStatus(-1);
			return response;
		}

		if (!pwManager.checkPassword(request.getCountry(), request.getPassword())) {
			response.setStatus(404);
			return response;
		}

		EntityManager em = emp.getEM();
		em.clear();
		int result = -1;

		em.getTransaction().begin();

		try {

			Rider existingRider = riderDao.getRiderForNumber(request.getCountry(),
					request.getSeason(), request.getRider().getRiderNumber());

			if (existingRider != null) {
				if (existingRider.hasEmail()) {
					try {
						riderAuthDao.delete(existingRider.getEmail().toString());
					} catch (NoResultException nre) {
						log.info("rider " + existingRider.getEmail() + " did not have auth object");
					}
				}

				result = riderDao.deleteRider(existingRider);
				log.debug("delete rider result " + result);
			}

			response.setStatus(result);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}

		return response;
	}

}
