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

import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Registration;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.model.Times;
import eu.motogymkhana.server.persist.MyEntityManager;

public class RiderDaoImpl implements RiderDao {

	@InjectLogger
	private Log log;

	private MyEntityManager emp;

	@Inject
	public RiderDaoImpl(MyEntityManager emp) {
		this.emp = emp;
	}

	@Override
	public Rider updateRider(Rider rider) {

		EntityManager em = emp.getEM();

		try {

			if (rider.hasId()) {

				Rider existingRider = getRiderForRiderId(rider.getRiderId());
				log.debug("update rider id " + rider.get_id() + " existingRider id "
						+ existingRider.get_id());
//				for (Times times : existingRider.getTimes()) {
//					log.debug("times " + times.toString());
//				}

				existingRider.merge(rider, em);

				for (Times times : existingRider.getTimes()) {
					log.debug("times after merge " + times.toString());
				}

				return existingRider;

			} else {

				emp.getEM().persist(rider);
				rider.setRiderId(Integer.toString(rider.get_id()));

				if (rider.hasRegistrations()) {
					for (Registration registration : rider.getRegistrations()) {
						registration.setRider(rider);
					}
				}
				for (Times t : rider.getTimes()) {
					t.setRider(rider);
				}

				rider.setRiderId(Integer.toString(rider.get_id()));
				return rider;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Rider getRiderForRiderId(String riderId) {

		EntityManager em = emp.getEM();
		Rider existingRider = null;

		TypedQuery<Rider> query = em.createQuery("select a from " + Rider.class.getSimpleName()
				+ " a where a." + Rider.RIDER_ID + " = :riderId ", Rider.class);

		try {
			existingRider = query.setParameter("riderId", riderId).getSingleResult();
		} catch (NoResultException nre) {
		}

		return existingRider;
	}

	@Override
	public int uploadRiders(Country country, int season, List<Rider> riders) {

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

		EntityManager em = emp.getEM();
		Rider existingRider = null;

		TypedQuery<Rider> query = em.createQuery(
				"select registration.rider from " + Registration.class.getSimpleName()
						+ " a where registration." + Registration.NUMBER
						+ " = :number and registration." + Registration.COUNTRY
						+ " = :country and registration." + Registration.SEASON + " = :season",
				Rider.class);

		try {
			existingRider = query.setParameter("country", country).setParameter("season", season)
					.setParameter("number", riderNumber).getSingleResult();

		} catch (NoResultException nre) {
		}

		return existingRider;
	}

	@Override
	public Rider getRiderByEmail(String email) {

		EntityManager em = emp.getEM();
		Rider existingRider = null;

		TypedQuery<Rider> query = em.createQuery(
				"select a from " + Rider.class.getSimpleName() + " a where a.email = :email ",
				Rider.class);

		try {
			existingRider = query.setParameter("email", email).getSingleResult();
		} catch (NoResultException nre) {
		}

		return existingRider;
	}

	@Override
	public Rider getRegisteredRiderByName(Rider rider) {

		EntityManager em = emp.getEM();
		Rider existingRider = null;

		TypedQuery<Rider> query = em.createQuery(
				"select a from " + Rider.class.getSimpleName() + " a where a." + Rider.FIRSTNAME
						+ " = :firstname and a." + Rider.LASTNAME + "= :lastname ",
				Rider.class);

		try {
			List<Rider> existingRiders = query.setParameter("firstname", rider.getFirstName())
					.setParameter("lastname", rider.getLastName()).getResultList();

			for (Rider r : existingRiders) {
				log.debug("riderdao : " + r.getFullName());
				if (r.hasRegistrations()) {
					if (existingRider == null) {
						existingRider = r;
						log.debug("riderdao : first registration " + r.getFullName());
					} else {
						for (Registration registration : r.getRegistrations()) {
							log.debug("riderdao : add registration " + r.getFullName());
							registration.setRider(existingRider);
							existingRider.getRegistrations().add(registration);
						}
					}
				}
			}

		} catch (NoResultException nre) {
			return null;
		}

		return existingRider;
	}

	@Override
	public List<Rider> getRiders(Country country, int year) {

		EntityManager em = emp.getEM();

		TypedQuery<Registration> query = em.createQuery(
				"select registration from " + Registration.class.getSimpleName()
						+ " registration where registration.country = :country and registration.season = :season",
				Registration.class);

		List<Registration> registrations = query.setParameter("country", country)
				.setParameter("season", year).getResultList();

		List<Rider> result = new ArrayList<Rider>();

		for (Registration r : registrations) {
			if (r.getRider() != null) {
				result.add(r.getRider());
			} else {
				log.debug("registration with no rider " + r.get_id());
			}
		}

		return result;

	}

	@Override
	public List<Rider> getAllRiders() {

		EntityManager em = emp.getEM();

		TypedQuery<Rider> query = em.createQuery(
				"select a from " + Rider.class.getSimpleName()
						+ " a where a.firstname is not null and a.lastname is not null",
				Rider.class);

		List<Rider> riders = query.getResultList();

		return riders;
	}

	@Override
	public int deleteRider(Rider rider) {

		log.debug("Delete rider " + rider.get_id() + " " + rider.getFullName());

		EntityManager em = emp.getEM();

		try {

			Rider existingRider = getRider(rider);

			log.debug("Existing rider " + existingRider == null ? "null"
					: existingRider.getFullName());

			if (existingRider != null) {
				if (existingRider.hasTimes()) {
					for (Times times : existingRider.getTimes()) {
						em.remove(times);
					}
				}

				if (existingRider.hasRegistrations()) {
					for (Registration registration : existingRider.getRegistrations()) {
						em.remove(registration);
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

		EntityManager em = emp.getEM();

		if (round == null) {
			return riders;
		}

		try {

			TypedQuery<Times> query = em.createQuery(
					"select a from " + Times.class.getSimpleName()
							+ " a where a.registered = :registered and a.date = :date and a.country = :country and a.season = :season",
					Times.class);

			Times times = null;

			try {

				times = query.setParameter("registered", true).setParameter("date", round.getDate())
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

	@Override
	public Rider getRider(Rider rider) {

		TypedQuery<Rider> query = emp.getEM().createQuery(
				"select a from " + Rider.class.getSimpleName() + " a where a._id = :id ",
				Rider.class);
		Rider existingRider = null;

		try {
			existingRider = query.setParameter("id", rider.get_id()).getSingleResult();

		} catch (NoResultException nre) {
		}
		return existingRider;
	}

	@Override
	public void remove(Rider rider) {
		emp.getEM().remove(rider);
	}
}
