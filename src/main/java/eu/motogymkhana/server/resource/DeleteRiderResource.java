package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.UpdateRiderRequest;
import eu.motogymkhana.server.api.UpdateRiderResponse;

public interface DeleteRiderResource {

	public UpdateRiderResponse deleteRider(UpdateRiderRequest request);
}
