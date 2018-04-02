/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * file with user ids and passwords for app admins. Pwhash for my 
 * password is aVPXVxwCy9tyU+RqbtLLsjwbtE40z9ZTDEvbubQ8I/c= (that is
 * the content of the password field in the database)
 * @author christine
 *
 */
@Entity
@Table(name = "passwords")
public class Password {

	public static final String COUNTRY = "country";
	public static final String PW_HASH = "pw_hash";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private int _id;

	@Column(name = COUNTRY)
	private Country country;

	@Column(name = PW_HASH)
	private String passwordHash;

	public Password() {
	}
	
	public Password(Country country, String password) {
		this.country=country;
		this.passwordHash=password;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
