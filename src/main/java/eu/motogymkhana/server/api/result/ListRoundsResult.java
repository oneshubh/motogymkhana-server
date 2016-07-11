/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.api.result;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Round;

/**
 * wrapper for sending a collection of riders back to the UI
 * 
 * @author christine
 * 
 */
public class ListRoundsResult extends GymkhanaResult {

	@JsonProperty("rounds")
	private Collection<Round> rounds;

	public Collection<Round> getRounds() {
		return rounds;
	}

	public void setRounds(Collection<Round> rounds) {
		this.rounds = rounds;
	}

}
