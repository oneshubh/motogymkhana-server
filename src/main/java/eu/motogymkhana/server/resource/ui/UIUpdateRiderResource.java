package eu.motogymkhana.server.resource.ui;

import eu.motogymkhana.server.api.request.UpdateRiderRequest;
import eu.motogymkhana.server.api.response.UpdateRegistrationResponse;

public interface UIUpdateRiderResource {

	UpdateRegistrationResponse updateRider(UpdateRiderRequest request);
}
