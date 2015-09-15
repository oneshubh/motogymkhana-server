package eu.motogymkhana.server.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.dao.PasswordDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Password;

public class PasswordDaoImpl implements PasswordDao {

	@InjectLogger
	private Log log;

	private Provider<EntityManager> emp;

	@Inject
	public PasswordDaoImpl(Provider<EntityManager> emp) {
		this.emp = emp;
	}

	@Override
	public boolean checkPasswordHash(String customerCode, String hash) {

		EntityManager em = emp.get();

		TypedQuery<Password> query = em.createQuery(
				"select a from " + Password.class.getSimpleName() + " a where "
						+ Password.CUSTOMER_CODE + " = :code", Password.class);

		List<Password> passwords = query.setParameter("code", customerCode).getResultList();

		return passwords != null && passwords.size() > 0
				&& passwords.get(0).getPasswordHash().equals(hash);
	}

	@Override
	public void store(String customerCode, String hash) {

		EntityManager em = emp.get();

		em.getTransaction().begin();

		em.persist(new Password(customerCode, hash));

		em.getTransaction().commit();
	}
}
