/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RonaldRider {

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("name")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("emailAddress")
	private String email;

	@JsonProperty("phoneNumber")
	private String phone;

	@JsonProperty("countryCode")
	private String countryCode;

	@JsonProperty("country")
	private String country;

	@JsonProperty("profilePicture")
	private String profilePicture;

	@JsonProperty("hasUnconfirmed")
	private boolean hasUnconfirmed;

	@JsonProperty("fullyPaid")
	private boolean hasPaid;

	@JsonProperty("paymentLate")
	private boolean paymentLate;

	public String getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCountry() {
		return country;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public boolean isHasUnconfirmed() {
		return hasUnconfirmed;
	}

	public boolean isHasPaid() {
		return hasPaid;
	}

	public boolean isPaymentLate() {
		return paymentLate;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}
}
