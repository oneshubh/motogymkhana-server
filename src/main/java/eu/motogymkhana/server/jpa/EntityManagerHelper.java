package eu.motogymkhana.server.jpa;

import javax.persistence.EntityManager;

public interface EntityManagerHelper {

	EntityManager getEntityManager();

	void closeEntityManager();

	void closeEntityManagerFactory();

	void beginTransaction();

	void rollback();

	void commit();

}
