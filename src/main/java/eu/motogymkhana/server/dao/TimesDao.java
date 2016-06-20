/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.dao;

import java.util.List;

import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Times;

public interface TimesDao {

	int update(Times times);

	List<Times> getTimes(Country country, int season, long date);
}
