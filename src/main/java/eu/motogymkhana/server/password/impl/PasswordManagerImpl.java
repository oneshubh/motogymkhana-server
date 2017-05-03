/*******************************************************************************


 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.password.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.NoResultException;

import org.apache.commons.codec.binary.Base64;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import eu.motogymkhana.server.ServerConstants;
import eu.motogymkhana.server.api.request.TokenRequest;
import eu.motogymkhana.server.dao.PasswordDao;
import eu.motogymkhana.server.dao.RiderAuthDao;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.RiderAuth;
import eu.motogymkhana.server.password.PasswordManager;

@Singleton
public class PasswordManagerImpl implements PasswordManager {

	@Inject
	private PasswordDao passwordDao;

	@Inject
	private RiderAuthDao riderAuthDao;

	private String nlPasswordHash = "aVPXVxwCy9tyU+RqbtLLsjwbtE40z9ZTDEvbubQ8I/c=";
	private String uaPasswordHash = "r/OZGL5gdtMwFKkncxOhXN5/7qhZLp3xO1RoDZfzsUk=";
	private String mdPasswordHash = "NmioBU99ZD3omW7zbbnSmxCEgxW9UvdkpABNRQutRto=";

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
			try {
				return riderAuthDao.checkPasswordHash(email, createHash(password));
			} catch (NoResultException nre) {
				return false;
			}
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

	@Override
	public int createRiderAccount(TokenRequest request) {

		try {
			RiderAuth auth = riderAuthDao.get(request.getEmail());
			auth.setPasswordHash(createHash(request.getPassword()));
			auth.removeToken();
			return 0;

		} catch (Exception e) {
			return -1;
		}
	}
}
