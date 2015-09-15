package eu.motogymkhana.server.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.dao.TimesDao;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Times;

public class TimesDaoImpl implements TimesDao {

	private Provider<EntityManager> emp;

	@Inject
	public TimesDaoImpl(Provider<EntityManager> emp) {
		this.emp = emp;
	}
}
