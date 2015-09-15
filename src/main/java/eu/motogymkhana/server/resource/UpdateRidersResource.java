package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.UploadRidersRequest;
import eu.motogymkhana.server.api.UploadRidersResponse;

public interface UpdateRidersResource {

	public UploadRidersResponse updateRiders(UploadRidersRequest request);
}
