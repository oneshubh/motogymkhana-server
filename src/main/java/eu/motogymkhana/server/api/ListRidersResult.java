/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.api;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.settings.Settings;

/**
 * wrapper for sending a collection of riders back to the UI
 * 
 * @author christine
 * 
 */
public class ListRidersResult extends GymkhanaResult {

	public static final int OK = 0;

	private int result;
	
	@JsonProperty("riders")
	private Collection<Rider> riders;
	
	@JsonProperty("settings")
	private Settings settings;

	@JsonProperty("text")
	private String text;

	public Collection<Rider> getRiders() {
		return riders;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setRiders(Collection<Rider> riders) {
		this.riders = riders;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}
}
