/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.model;

import java.util.Comparator;

public class RiderComparator implements Comparator<Rider> {

	@Override
	public int compare(Rider rider1, Rider rider2) {
		return rider1.getBestTime() - rider2.getBestTime();
	}
}
