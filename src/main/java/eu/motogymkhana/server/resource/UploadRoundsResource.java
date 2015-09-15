package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.UploadRoundsRequest;
import eu.motogymkhana.server.api.UploadRoundsResponse;

public interface UploadRoundsResource {

	public UploadRoundsResponse uploadRounds(UploadRoundsRequest request);
}
