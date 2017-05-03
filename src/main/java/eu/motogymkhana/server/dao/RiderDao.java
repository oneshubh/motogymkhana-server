/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.dao;

import java.util.List;

import eu.motogymkhana.server.api.response.UploadRidersResponse;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Round;

public interface RiderDao {

	Rider updateRider(Rider rider);

	int uploadRiders(Country country, int season, List<Rider> riders);

	List<Rider> getRiders(Country country, int year);

	int deleteRider(Rider rider);

	int updateRiders(List<Rider> riders);

	List<Rider> getRegisteredRidersForDate(Round round);

	Rider getRiderForNumber(Country country, int season, int riderNumber);

	Rider getRiderByEmail(String email);

	List<Rider> getAllRiders();

	Rider getRider(Rider rider);

	Rider getRegisteredRiderByName(Rider rider);

	void remove(Rider rider);
}
