/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.api.request;

import eu.motogymkhana.server.model.Rider;

/**
 * Created by christine on 15-5-15.
 */
public class UpdateRiderRequest extends GymkhanaRequest {

	private Rider rider;
	private String email;

	public UpdateRiderRequest(){
		
	}
	
	public UpdateRiderRequest(Rider rider) {
		this.rider = rider;
	}

	public Rider getRider() {
		return rider;
	}

	public String getEmail() {
		return email;
	}
}
