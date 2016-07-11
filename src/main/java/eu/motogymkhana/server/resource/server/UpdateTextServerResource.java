/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.resource.server;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.request.UpdateTextRequest;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.resource.UpdateTextResource;
import eu.motogymkhana.server.text.TextManager;

public class UpdateTextServerResource extends ServerResource implements UpdateTextResource {

	@Inject
	private TextManager textManager;

	@Inject
	private PasswordManager pwManager;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post("json")
	public void updateText(UpdateTextRequest request) {

		if (pwManager.checkPassword(request.getCountry(), request.getPassword())) {
			textManager.setText(request.getText());
		}
	}
}
