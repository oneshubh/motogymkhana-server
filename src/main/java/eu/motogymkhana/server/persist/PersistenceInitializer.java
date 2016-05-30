package eu.motogymkhana.server.persist;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.PersistService;

/**
 * http://code.google.com/p/google-guice/wiki/JPA
 * 
 * @author christine
 * 
 */
public class PersistenceInitializer {

	@Inject
	PersistenceInitializer(PersistService service, Provider<EntityManager> emp) {

		service.start();
	}
}
