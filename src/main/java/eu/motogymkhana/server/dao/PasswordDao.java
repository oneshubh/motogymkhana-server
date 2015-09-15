package eu.motogymkhana.server.dao;


public interface PasswordDao {

	boolean checkPasswordHash(String customerCode, String passwordHash);

	void store(String customerCode, String hash);
}
