/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;

import eu.motogymkhana.server.dao.SettingsDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.persist.MyEntityManager;
import eu.motogymkhana.server.settings.Settings;

public class SettingsDaoImpl implements SettingsDao {

	@InjectLogger
	private Log log;

	private MyEntityManager emp;

	@Inject
	public SettingsDaoImpl(MyEntityManager emp) {
		this.emp = emp;
	}

	@Override
	public Settings getSettings(Country country, int season) {

		EntityManager em = emp.getEM();

		TypedQuery<Settings> query = em
				.createQuery(
						"select a from " + Settings.class.getSimpleName()
								+ " a where a.season = :season and a.country = :country",
						Settings.class);

		List<Settings> result = query.setParameter("country", country)
				.setParameter("season", season).getResultList();

		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void store(Settings settings) {
		
		log.debug("SettingsDaoImpl " + settings.get_id() + " " + settings.getCountry() + " "
				+ settings.getSeason());

		Settings existingSettings = getSettings(settings.getCountry(), settings.getSeason());
		if (existingSettings == null) {
			emp.getEM().persist(settings);
		} else {
			existingSettings.merge(settings);
		}
	}

	@Override
	public void initSettings(Country country, int season) {
		store(new Settings(country, season, 4, 6, 105, 115));
	}
}
