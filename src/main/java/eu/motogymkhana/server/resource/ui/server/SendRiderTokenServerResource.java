/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.resource.ui.server;

import java.util.List;

import javax.persistence.EntityManager;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.api.GymkhanaRequest;
import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.api.TokenRequest;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.dao.SettingsDao;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.resource.ui.SendRiderTokenResource;
import eu.motogymkhana.server.resource.ui.ShowRidersResource;
import eu.motogymkhana.server.settings.Settings;
import eu.motogymkhana.server.text.TextManager;

public class SendRiderTokenServerResource  extends ServerResource implements SendRiderTokenResource{
	
	@Inject
	private RiderDao riderDao;
	
	@Inject
	private SettingsDao settingsDao;

	@Inject
	private TextManager textManager;
	
	@Inject
	private Provider<EntityManager> emp;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post
	public ListRidersResult sendToken(TokenRequest request) {

		ListRidersResult result = new ListRidersResult();
		result.setResult(-1);
		result.setText(textManager.getText());

		EntityManager em = emp.get();

		em.getTransaction().begin();
		
		try {
			List<Rider> riders = riderDao.getRiders(request.getCountry(),request.getSeason());
			
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
