package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.UpdateRiderResponse;
import eu.motogymkhana.server.api.UpdateTimesRequest;

public interface UpdateTimesResource {

	public UpdateRiderResponse updateTimes(UpdateTimesRequest request);
}
