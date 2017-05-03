package eu.motogymkhana.server.dao.impl;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;

import eu.motogymkhana.server.dao.RegistrationDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Bib;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Registration;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.persist.MyEntityManager;

@Singleton
public class RegistrationDaoImpl implements RegistrationDao {

	@InjectLogger
	private Log log;

	private MyEntityManager emp;

	@Inject
	public RegistrationDaoImpl(MyEntityManager emp) {
		this.emp = emp;
	}

	@Override
	public boolean isEmpty() {

		EntityManager em = emp.getEM();

		TypedQuery<Registration> query = em.createQuery(
				"select a from " + Registration.class.getSimpleName() + " a", Registration.class);

		List<Registration> registrations = query.getResultList();

		return registrations == null || registrations.size() == 0;
	}

	@Override
	public boolean hasRegistration(Rider rider, Country country, int season) {
		try{
			return getRegistration(rider, country, season) != null;
		} catch (NoResultException nre){
			return false;
		}
	}

	@Override
	public void addRegistration(Rider rider, Country country, int season, int startNumber) {

		if (!hasRegistration(rider, country, season)) {
			emp.getEM().persist(new Registration(rider, country, season, startNumber, Bib.Y));
		}
	}

	@Override
	public void addRegistration(Registration registration) {

		if (!hasRegistration(registration)) {
			emp.getEM().persist(registration);
		}
	}

	private boolean hasRegistration(Registration registration) {
		return hasRegistration(registration.getRider(), registration.getCountry(),
				registration.getSeason());
	}

	@Override
	public int updateRegistration(Registration registration) {

		EntityManager em = emp.getEM();
		Registration existingRegistration = getRegistration(registration);

		if (registration.isRegistered()) {
			if (existingRegistration != null) {
				em.remove(registration);
			}
		} else {
			addRegistration(registration);
		}

		return 0;
	}

	private Registration getRegistration(Registration registration) {
		return getRegistration(registration.getRider(), registration.getCountry(),
				registration.getSeason());
	}

	private Registration getRegistration(Rider rider, Country country, int season) {

		EntityManager em = emp.getEM();

		TypedQuery<Registration> query = em.createQuery(
				"select a from " + Registration.class.getSimpleName() + " a where "
						+ Registration.RIDER + " = :rider and " + Registration.COUNTRY
						+ " = :country and " + Registration.SEASON + " = :season",
				Registration.class);

		Registration registration = query.setParameter("rider", rider)
				.setParameter("country", country).setParameter("season", season).getSingleResult();

		return registration;
	}
}
