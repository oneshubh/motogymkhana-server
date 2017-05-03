package eu.motogymkhana.server.dao;

import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Registration;
import eu.motogymkhana.server.model.Rider;

public interface RegistrationDao {

	boolean isEmpty();

	boolean hasRegistration(Rider rider,Country country, int season);

	int updateRegistration(Registration registration);

	void addRegistration(Rider rider, Country country, int season, int startNumber);

	void addRegistration(Registration registration);
}
