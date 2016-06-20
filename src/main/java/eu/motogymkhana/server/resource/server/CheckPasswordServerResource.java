/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.resource.server;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.GymkhanaRequest;
import eu.motogymkhana.server.api.GymkhanaResult;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.resource.CheckPasswordResource;

public class CheckPasswordServerResource extends ServerResource implements CheckPasswordResource {

	@Inject
	private PasswordManager passwordManager;

	@Override
	@Post("json")
	public GymkhanaResult checkPassword(GymkhanaRequest request) {

		GymkhanaResult result = new GymkhanaResult();

		boolean b = passwordManager.checkPassword(request.getCountry(), request.getPassword());
		result.setResultCode(b ? 0 : -1);

		return result;
	}
}
