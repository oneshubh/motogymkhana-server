package eu.motogymkhana.server.dao;

import eu.motogymkhana.server.model.Country;

public interface PasswordDao {

	boolean checkPasswordHash(Country country, String passwordHash);

	void store(Country country, String hash);
}
