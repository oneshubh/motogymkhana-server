package eu.motogymkhana.server.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetRiderRequest extends GymkhanaRequest{
	
	@JsonProperty("email")
	private String email;

	public String getEmail() {
		return email;
	}
}
