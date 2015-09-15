package eu.motogymkhana.server.persist;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

/**
 * http://code.google.com/p/google-guice/wiki/JPA
 * 
 * @author christine
 * 
 */
public class PersistenceInitializer {

	@Inject
	PersistenceInitializer(PersistService service) {

		service.start();
		// At this point JPA is started and ready.
	}
}
