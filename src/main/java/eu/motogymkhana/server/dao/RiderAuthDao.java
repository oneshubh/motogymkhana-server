package eu.motogymkhana.server.dao;

import eu.motogymkhana.server.model.RiderAuth;

public interface RiderAuthDao {

	RiderAuth get(String email);

	boolean checkPasswordHash(String email, String createHash);

}
