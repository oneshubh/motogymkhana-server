package eu.motogymkhana.server.password;

public interface PasswordManager {

	public boolean checkPassword(String customerCode, String password);
}
