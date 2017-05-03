/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.resource.server;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.request.GymkhanaRequest;
import eu.motogymkhana.server.api.result.ListRidersResult;
import eu.motogymkhana.server.conversion.ConvertRegistration;
import eu.motogymkhana.server.dao.RegistrationDao;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.persist.MyEntityManager;
import eu.motogymkhana.server.resource.GetAllRidersResource;
import eu.motogymkhana.server.text.TextManager;

public class GetAllRidersServerResource extends ServerResource implements GetAllRidersResource {

	@Inject
	private RiderDao riderDao;
	
	@Inject
	private RegistrationDao registrationDao;

	@Inject
	private ConvertRegistration convert;

	@Inject
	private TextManager textManager;

	@InjectLogger
	private Log log;

	@Inject
	private MyEntityManager emp;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post
	public ListRidersResult getRiders(GymkhanaRequest request) {
		
		if (registrationDao.isEmpty()) {
			convert.initRegistrations();
		}

		ListRidersResult result = new ListRidersResult();
		result.setResult(-1);
		result.setText(textManager.getText());

		if (request == null) {
			return result;
		}

		EntityManager em = emp.getEM();
		em.clear();

		em.getTransaction().begin();

		try {

			List<Rider> riders = riderDao.getAllRiders();
			result.setRiders(riders);

			result.setResult(ListRidersResult.OK);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		return result;
	}
}
