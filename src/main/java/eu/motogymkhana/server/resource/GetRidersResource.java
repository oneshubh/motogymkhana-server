/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.GymkhanaRequest;
import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.model.Country;


public interface GetRidersResource {

	public ListRidersResult getRiders(GymkhanaRequest request);
}
