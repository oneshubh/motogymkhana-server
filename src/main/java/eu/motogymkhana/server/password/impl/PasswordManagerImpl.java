package eu.motogymkhana.server.password.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import eu.motogymkhana.server.ServerConstants;
import eu.motogymkhana.server.dao.PasswordDao;
import eu.motogymkhana.server.dao.RiderAuthDao;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Password;
import eu.motogymkhana.server.password.PasswordManager;

@Singleton
public class PasswordManagerImpl implements PasswordManager {

	@Inject
	private PasswordDao passwordDao;

	@Inject
	private RiderAuthDao riderAuthDao;

	private String nlPasswordHash = "aVPXVxwCy9tyU+RqbtLLsjwbtE40z9ZTDEvbubQ8I/c=";

	@Override
	public boolean checkPassword(Country country, String password) {

		if (!passwordDao.checkPasswordHash(Country.NL, nlPasswordHash)) {
			createPasswords();
		}

		if (password != null && password.length() >= 6) {
			return passwordDao.checkPasswordHash(country, createHash(password));
		} else {
			return false;
		}
	}

	@Override
	public boolean checkRiderPassword(String email, String password) {
		if (password != null && password.length() >= 6) {
			return riderAuthDao.checkPasswordHash(email, createHash(password));
		} else {
			return false;
		}
	}

	@Override
	public String createHash(String password) {

		byte[] hash = digest(password, "37");

		String hashString = null;
		hashString = Base64.encodeBase64String(hash);

		return hashString;
	}

	private byte[] digest(String password, String salt) {

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(ServerConstants.digestAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md.digest((salt + password).getBytes());
	}

	private void createPasswords() {
		passwordDao.store(Country.NL, nlPasswordHash);
		passwordDao.store(Country.EU, nlPasswordHash);
	}
}
