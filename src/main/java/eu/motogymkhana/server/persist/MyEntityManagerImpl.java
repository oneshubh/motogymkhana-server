package eu.motogymkhana.server.persist;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MyEntityManagerImpl implements MyEntityManager{

	@Inject
	private Provider<EntityManager> emp;

	@Override
	public EntityManager getEM() {
		EntityManager em =  emp.get();
		return em;
	}

}
