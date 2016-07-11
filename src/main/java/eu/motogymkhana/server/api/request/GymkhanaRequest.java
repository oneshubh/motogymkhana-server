/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.api.request;

import eu.motogymkhana.server.model.Country;

public class GymkhanaRequest {

	private Country country;
	private int season;
	private String password;

	public GymkhanaRequest() {
	}
	
	public GymkhanaRequest(Country country, int season) {
		this.country = country;
		this.season = season;
	}

	public Country getCountry() {
		return country;
	}

	public String getPassword() {
		return password;
	}

	public int getSeason() {
		return season;
	}
}
