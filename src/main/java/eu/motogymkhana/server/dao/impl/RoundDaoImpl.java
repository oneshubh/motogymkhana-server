/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.dao.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;

import eu.motogymkhana.server.dao.RoundDao;
import eu.motogymkhana.server.dao.TimesDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.model.Times;
import eu.motogymkhana.server.persist.MyEntityManager;

public class RoundDaoImpl implements RoundDao {

	@InjectLogger
	private Log log;

	private MyEntityManager emp;
	private TimesDao timesDao;

	@Inject
	public RoundDaoImpl(MyEntityManager emp, TimesDao timesDao) {
		this.emp = emp;
		this.timesDao = timesDao;
	}

	@Override
	public int storeRounds(Collection<Round> rounds) {

		if (rounds != null && rounds.size() > 0) {
			Round r = rounds.iterator().next();
			final Collection<Round> existingRounds = getRounds(r.getCountry(), r.getSeason());

			Iterator<Round> iterator = rounds.iterator();

			try {

				while (iterator.hasNext()) {
					Round round = iterator.next();
					store(round);
					if (existingRounds.contains(round)) {
						existingRounds.remove(round);
					}
				}
			} catch (Exception e) {
				return -1;
			}

			EntityManager em = emp.getEM();

			for (Round roundToDelete : existingRounds) {
				removeTimes(roundToDelete.getCountry(), roundToDelete.getSeason(), roundToDelete.getDate());
				em.remove(roundToDelete);
			}
		}

		return 0;
	}

	private void removeTimes(Country country, int season, long date) {
		List<Times> list = timesDao.getTimes(country, season, date);
		if (list != null) {
			EntityManager em = emp.getEM();
			for (Times t : list) {
				em.remove(t);
			}
		}
	}

	@Override
	public int store(Round round) {

		EntityManager em = emp.getEM();

		try {

			TypedQuery<Round> query = emp
					.getEM()
					.createQuery(
							"select a from "
									+ Round.class.getSimpleName()
									+ " a where a.country = :country and a.season = :season and a.date = :date",
							Round.class);
			Round existingRound = null;

			try {
				existingRound = query.setParameter("date", round.getDate())
						.setParameter("country", round.getCountry())
						.setParameter("season", round.getSeason()).getSingleResult();

			} catch (NoResultException nre) {
			}

			if (existingRound == null) {

				em.persist(round);

			} else {

				if (round.newerThan(existingRound)) {

					existingRound.merge(round);
					em.merge(existingRound);
					round.set_id(existingRound.get_id());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;

	}

	@Override
	public Collection<Round> getRounds(Country country, int season) {

		EntityManager em = emp.getEM();

		TypedQuery<Round> query = em.createQuery("select a from " + Round.class.getSimpleName()
				+ " a where a.season = :season and a.country = :country", Round.class);

		List<Round> rounds = query.setParameter("country", country).setParameter("season", season)
				.getResultList();

		return rounds;
	}
}
