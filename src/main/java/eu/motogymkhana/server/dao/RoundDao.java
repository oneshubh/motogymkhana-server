/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.dao;

import java.util.Collection;

import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Round;

public interface RoundDao {

	int storeRounds(Collection<Round> rounds);

	int store(Round round);

	Collection<Round> getRounds(Country country, int season);
}
