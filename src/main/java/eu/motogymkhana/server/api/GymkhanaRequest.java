package eu.motogymkhana.server.api;

import eu.motogymkhana.server.model.Country;

public class GymkhanaRequest {

	private Country country;
	private int season;
	private String password;

	public GymkhanaRequest() {
	}
	
	public GymkhanaRequest(Country country, int season) {
		this.country = country;
		this.season = season;
	}

	public Country getCountry() {
		return country;
	}

	public String getPassword() {
		return password;
	}

	public int getSeason() {
		return season;
	}
}
