package eu.motogymkhana.server.password.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import eu.motogymkhana.server.ServerConstants;
import eu.motogymkhana.server.dao.PasswordDao;
import eu.motogymkhana.server.password.PasswordManager;

@Singleton
public class PasswordManagerImpl implements PasswordManager {

	@Inject
	private PasswordDao passwordDao;

	@Override
	public boolean checkPassword(String customerCode, String password) {

		if (password == null || password.length() < 6) {
			return false;
		}

		byte[] hash = digest(password, "37");

		String hashString = null;
		hashString = Base64.encodeBase64String(hash);

		return passwordDao.checkPasswordHash(customerCode, hashString);
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

}
