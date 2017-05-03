/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.resource.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import eu.motogymkhana.server.dao.PasswordDao;
import eu.motogymkhana.server.dao.RegistrationDao;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.dao.SettingsDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Registration;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Times;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.persist.MyEntityManager;
import eu.motogymkhana.server.resource.GetRidersResource;
import eu.motogymkhana.server.settings.Settings;
import eu.motogymkhana.server.text.TextManager;

public class GetRidersServerResource extends ServerResource implements GetRidersResource {
	private static DateFormat dateFormat = new SimpleDateFormat("hh:mm:ssSS");

	@Inject
	private RiderDao riderDao;

	@Inject
	private RegistrationDao registrationDao;

	@Inject
	private ConvertRegistration convert;

	@Inject
	private SettingsDao settingsDao;

	@Inject
	private TextManager textManager;

	@Inject
	private PasswordManager passwordManager;

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

		boolean isAdmin = false;
		EntityManager em = emp.getEM();
		em.clear();

		if (request.hasPassword()) {
			isAdmin = passwordManager.checkPassword(request.getCountry(), (request.getPassword()));
		}

		if (registrationDao.isEmpty()) {

			try {

				em.getTransaction().begin();
				convert.initRegistrations();
				em.getTransaction().commit();

				em.clear();

				em.getTransaction().begin();
				convert.removeUnregisteredRiders();
				em.getTransaction().commit();

			} catch (Exception e) {
				e.printStackTrace();
				em.getTransaction().rollback();
			}
		}

		ListRidersResult result = new ListRidersResult();
		result.setResult(-1);
		result.setText(textManager.getText());

		em.getTransaction().begin();

		try {

			List<Rider> riders = riderDao.getRiders(request.getCountry(), request.getSeason());

			result.setRiders(riders);

			Settings settings = settingsDao.getSettings(request.getCountry(), request.getSeason());
			result.setSettings(settings);

			result.setResult(ListRidersResult.OK);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		return result;
	}
}
