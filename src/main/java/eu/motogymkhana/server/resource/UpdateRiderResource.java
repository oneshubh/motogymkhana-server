package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.UpdateRiderRequest;
import eu.motogymkhana.server.api.UpdateRiderResponse;

public interface UpdateRiderResource {

	public UpdateRiderResponse updateRider(UpdateRiderRequest request);
}
