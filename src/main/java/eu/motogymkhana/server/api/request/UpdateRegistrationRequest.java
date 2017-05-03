package eu.motogymkhana.server.api.request;

import eu.motogymkhana.server.model.Registration;

public class UpdateRegistrationRequest  extends GymkhanaRequest {

	private Registration registration;

	public Registration getRegistration() {
		return registration;
	}
}
