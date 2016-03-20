package eu.motogymkhana.server.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import eu.motogymkhana.server.dao.RiderAuthDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Password;
import eu.motogymkhana.server.model.RiderAuth;

@Singleton
public class RiderAuthDaoImpl implements RiderAuthDao {

	@InjectLogger
	private Log log;

	private Provider<EntityManager> emp;

	@Inject
	public RiderAuthDaoImpl(Provider<EntityManager> emp) {
		this.emp = emp;
	}

	@Override
	public RiderAuth get(String email) {

		EntityManager em = emp.get();

		TypedQuery<RiderAuth> query = em.createQuery(
				"select a from " + RiderAuth.class.getSimpleName() + " a where " + RiderAuth.EMAIL
						+ " = :email", RiderAuth.class);

		RiderAuth auth = query.setParameter("email", email).getSingleResult();
		
		return auth;
	}
	
	@Override
	public boolean checkPasswordHash(String email, String hash) {

		EntityManager em = emp.get();

		TypedQuery<RiderAuth> query = em.createQuery(
				"select a from " + Password.class.getSimpleName() + " a where "
						+ RiderAuth.EMAIL + " = :email", RiderAuth.class);

		RiderAuth auth = query.setParameter("email", email).getSingleResult();

		return auth != null
				&& auth.getPasswordHash().equals(hash);
	}

}
