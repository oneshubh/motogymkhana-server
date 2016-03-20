package eu.motogymkhana.server.password;

import eu.motogymkhana.server.model.Country;

public interface PasswordManager {

	public boolean checkPassword(Country country, String password);

	public String createHash(String password);

	boolean checkRiderPassword(String email, String password);
}
