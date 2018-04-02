/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.round.impl;

import java.util.Collection;

import com.google.inject.Inject;

import eu.motogymkhana.server.dao.RoundDao;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.round.RoundManager;

public class RoundManagerImpl implements RoundManager {

	@Inject
	private RoundDao roundDao;

	@Override
	public Round getCurrentRound(Country country, int season) {

		long yesterday = System.currentTimeMillis() - (24l * 3600l * 1000l);

		Round round = null;

		Collection<Round> rounds = roundDao.getRounds(country, season);

		if (rounds != null && rounds.size() > 0) {
			for (Round r : rounds) {

				if (round == null || round.getDate() < yesterday && round.getDate() > r.getDate()) {
					round = r;
				}
			}
		}

		return round;
	}

	@Override
	public Collection<Round> getRounds(Country country, int season) {
		return roundDao.getRounds(country, season);
	}
}
