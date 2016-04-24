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
