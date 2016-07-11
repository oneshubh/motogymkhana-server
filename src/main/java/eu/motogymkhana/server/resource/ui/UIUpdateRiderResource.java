package eu.motogymkhana.server.resource.ui;

import eu.motogymkhana.server.api.request.UpdateRiderRequest;
import eu.motogymkhana.server.api.response.UpdateRiderResponse;

public interface UIUpdateRiderResource {

	UpdateRiderResponse updateRider(UpdateRiderRequest request);
}
