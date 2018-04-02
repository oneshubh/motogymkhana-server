/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;

import eu.motogymkhana.server.dao.TimesDao;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Times;
import eu.motogymkhana.server.persist.MyEntityManager;

public class TimesDaoImpl implements TimesDao {

	private MyEntityManager emp;

	@Inject
	public TimesDaoImpl(MyEntityManager emp) {
		this.emp = emp;
	}

	@Override
	public int update(Times times) {

		EntityManager em = emp.getEM();

		try {

			if (times.getRider().hasId()) {
				em.merge(times.getRider());
			} else {
				em.persist(times.getRider());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	@Override
	public List<Times> getTimes(Country country, int season, long date) {

		EntityManager em = emp.getEM();

		List<Times> result = new ArrayList<Times>();

		TypedQuery<Times> query = em.createQuery(
				"select a from " + Times.class.getSimpleName()
						+ " a where a.country = :country and a.season = :season and a.date = :date",
				Times.class);

		try {
			result = query.setParameter("country", country).setParameter("season", season)
					.setParameter("date", date).getResultList();

		} catch (NoResultException nre) {
		}

		return result;
	}

	@Override
	public List<Times> getTimesForRiderId(int id) {

		EntityManager em = emp.getEM();

		List<Times> result = new ArrayList<Times>();

		TypedQuery<Times> query = em.createQuery(
				"select a from " + Times.class.getSimpleName() + " a where a.rider.id = :riderId",
				Times.class);

		try {
			result = query.setParameter("riderId", id).getResultList();

		} catch (NoResultException nre) {
		}
		return result;

	}
}
