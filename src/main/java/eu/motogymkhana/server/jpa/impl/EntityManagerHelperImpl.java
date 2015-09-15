package eu.motogymkhana.server.jpa.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.inject.Singleton;

import eu.motogymkhana.server.jpa.EntityManagerHelper;

@Singleton
public class EntityManagerHelperImpl implements EntityManagerHelper {

	private final EntityManagerFactory emf;
	private final ThreadLocal<EntityManager> threadLocal;

	public EntityManagerHelperImpl() {
		emf = Persistence.createEntityManagerFactory("BookStoreUnit");
		threadLocal = new ThreadLocal<EntityManager>();
	}

	@Override
	public EntityManager getEntityManager() {

		EntityManager em = threadLocal.get();

		if (em == null) {
			em = emf.createEntityManager();
			threadLocal.set(em);
		}
		return em;
	}

	@Override
	public void closeEntityManager() {

		EntityManager em = threadLocal.get();

		if (em != null) {
			em.close();
			threadLocal.set(null);
		}
	}

	@Override
	public void closeEntityManagerFactory() {
		emf.close();
	}

	@Override
	public void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}

	@Override
	public void rollback() {
		getEntityManager().getTransaction().rollback();
	}

	@Override
	public void commit() {
		getEntityManager().getTransaction().commit();
	}
}
