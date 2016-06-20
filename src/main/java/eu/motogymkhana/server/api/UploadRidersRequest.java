/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.api;

import java.util.List;

import eu.motogymkhana.server.model.Rider;

public class UploadRidersRequest extends GymkhanaRequest {

	private List<Rider> riders;

	public UploadRidersRequest() {
	}

	public UploadRidersRequest(List<Rider> riders) {
		this.riders = riders;
	}

	public List<Rider> getRiders() {
		return riders;
	}

	public void setRiders(List<Rider> riders) {
		this.riders = riders;
	}

}
