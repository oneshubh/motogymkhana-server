/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.model.Round;

/**
 * Created by christine on 15-5-15.
 */
public class UpdateRoundRequest extends GymkhanaRequest {

	@JsonProperty("round")
	private Round round;

	public UpdateRoundRequest(Round round) {
		this.round = round;
	}

	public Round getRound() {
		return round;
	}
}
