/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.api.request;

import eu.motogymkhana.server.model.Times;

/**
 * Created by christine on 15-5-15.
 */
public class UpdateTimesRequest extends GymkhanaRequest {

	private Times times;

	public UpdateTimesRequest(){
	}
	
	public UpdateTimesRequest(Times times) {
		this.times = times;
	}

	public Times getTimes() {
		return times;
	}
}
