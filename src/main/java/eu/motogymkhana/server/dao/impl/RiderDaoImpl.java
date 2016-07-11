/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
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

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.model.Times;

public class RiderDaoImpl implements RiderDao {

	@InjectLogger
	private Log log;

	private Provider<EntityManager> emp;

	@Inject
	public RiderDaoImpl(Provider<EntityManager> emp) {
		this.emp = emp;
	}

	@Override
	public int updateRider(Rider rider) {

		EntityManager em = emp.get();
		Rider existingRider = null;

		try {

			TypedQuery<Rider> query = em
					.createQuery(
							"select a from "
									+ Rider.class.getSimpleName()
									+ " a where a.riderNumber = :number and a.country = :country and a.season = :season",
							Rider.class);

			try {
				existingRider = query.setParameter("country", rider.getCountry())
						.setParameter("season", rider.getSeason())
						.setParameter("number", rider.getRiderNumber()).getSingleResult();

			} catch (NoResultException nre) {
			}

			if (existingRider == null) {

				em.persist(rider);
				for (Times t : rider.getTimes()) {
					t.setRider(rider);
				}

			} else {

				existingRider.merge(rider);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	@Override
	public int uploadRiders(Country country, int season, List<Rider> riders) {

		EntityManager em = emp.get();

		try {

			for (Rider rider : riders) {

				Rider r = getRiderForNumber(country, season, rider.getRiderNumber());

				if (r != null) {
					em.remove(r);
				}

				for (Times t : rider.getTimes()) {
					t.setRider(rider);
				}

				em.persist(rider);
			}

			return 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public int updateRiders(List<Rider> riders) {

		try {

			for (Rider rider : riders) {

				updateRider(rider);
			}

			return 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public Rider getRiderForNumber(Country country, int season, int riderNumber) {

		EntityManager em = emp.get();
		Rider existingRider = null;

		TypedQuery<Rider> query = em
				.createQuery(
						"select a from "
								+ Rider.class.getSimpleName()
								+ " a where a.riderNumber = :number and a.country = :country and a.season = :season",
						Rider.class);

		try {
			existingRider = query.setParameter("country", country).setParameter("season", season)
					.setParameter("number", riderNumber).getSingleResult();

		} catch (NoResultException nre) {
		}

		return existingRider;
	}

	@Override
	public Rider getRiderByEmail(Country country, int season, String email) {

		EntityManager em = emp.get();
		Rider existingRider = null;

		TypedQuery<Rider> query = em.createQuery("select a from " + Rider.class.getSimpleName()
				+ " a where a.email = :email and a.country = :country and a.season = :season",
				Rider.class);

		try {
			existingRider = query.setParameter("email", email).setParameter("country", country)
					.setParameter("season", season).getSingleResult();
		} catch (NoResultException nre) {
		}

		return existingRider;
	}

	@Override
	public List<Rider> getRiders(Country country, int year) {

		EntityManager em = emp.get();

		TypedQuery<Rider> query = em.createQuery("select a from " + Rider.class.getSimpleName()
				+ " a where a.country = :country and a.season = :season", Rider.class);

		List<Rider> riders = query.setParameter("country", country).setParameter("season", year)
				.getResultList();

		List<Rider> result = new ArrayList<Rider>();

		for (Rider r : riders) {
			em.refresh(r);
			result.add(r);
		}

		return result;

	}

	@Override
	public int deleteRider(Rider rider) {

		log.debug("Delete rider " + rider.getRiderNumber() + " " + rider.getFullName());

		EntityManager em = emp.get();

		try {

			TypedQuery<Rider> query = emp
					.get()
					.createQuery(
							"select a from "
									+ Rider.class.getSimpleName()
									+ " a where a.riderNumber = :number and a.country = :country and a.season = :season",
							Rider.class);
			Rider existingRider = null;

			try {
				existingRider = query.setParameter("number", rider.getRiderNumber())
						.setParameter("country", rider.getCountry())
						.setParameter("season", rider.getSeason()).getSingleResult();

			} catch (NoResultException nre) {
			}
			log.debug("Existing rider " + existingRider == null ? "null" : existingRider
					.getFullName());

			if (existingRider != null) {
				if (existingRider.hasTimes()) {
					for (Times times : existingRider.getTimes()) {
						em.remove(times);
					}
				}

				em.remove(existingRider);
				log.debug("remove " + existingRider.getFullName());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	@Override
	public List<Rider> getRegisteredRidersForDate(Round round) {

		List<Rider> riders = new ArrayList<Rider>();

		EntityManager em = emp.get();

		if (round == null) {
			return riders;
		}

		try {

			TypedQuery<Times> query = em
					.createQuery(
							"select a from "
									+ Times.class.getSimpleName()
									+ " a where a.registered = :registered and a.date = :date and a.country = :country and a.season = :season",
							Times.class);

			Times times = null;

			try {

				times = query.setParameter("registered", true)
						.setParameter("date", round.getDate())
						.setParameter("country", round.getCountry())
						.setParameter("season", round.getSeason()).getSingleResult();

				if (times != null) {
					riders.add(times.getRider());
				}

			} catch (NoResultException nre) {
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return riders;
	}
}
