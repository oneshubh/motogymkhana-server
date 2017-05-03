package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.request.UpdateRegistrationRequest;
import eu.motogymkhana.server.api.response.UpdateRegistrationResponse;

public interface UpdateRegistrationResource {

	UpdateRegistrationResponse updateRegistration(UpdateRegistrationRequest request);

}
