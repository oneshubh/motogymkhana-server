/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import eu.motogymkhana.server.dao.RiderAuthDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.RiderAuth;
import eu.motogymkhana.server.persist.MyEntityManager;

@Singleton
public class RiderAuthDaoImpl implements RiderAuthDao {

	@InjectLogger
	private Log log;

	private MyEntityManager emp;

	@Inject
	public RiderAuthDaoImpl(MyEntityManager emp) {
		this.emp = emp;
	}

	@Override
	public RiderAuth get(String email) {

		EntityManager em = emp.getEM();

		TypedQuery<RiderAuth> query = em.createQuery(
				"select a from " + RiderAuth.class.getSimpleName() + " a where " + RiderAuth.EMAIL
						+ " = :email", RiderAuth.class);

		RiderAuth auth = query.setParameter("email", email).getSingleResult();

		return auth;
	}

	@Override
	public boolean checkPasswordHash(String email, String hash) {

		EntityManager em = emp.getEM();

		TypedQuery<RiderAuth> query = em.createQuery(
				"select a from " + RiderAuth.class.getSimpleName() + " a where " + RiderAuth.EMAIL
						+ " = :email", RiderAuth.class);

		RiderAuth auth = query.setParameter("email", email).getSingleResult();

		return auth != null && hash != null && hash.equals(auth.getPasswordHash());
	}

	@Override
	public void create(RiderAuth riderAuth) {
		emp.getEM().persist(riderAuth);
	}

	@Override
	public void delete(String email) {

		RiderAuth auth = get(email);
		if (auth != null) {
			emp.getEM().remove(auth);
		}
	}
}
