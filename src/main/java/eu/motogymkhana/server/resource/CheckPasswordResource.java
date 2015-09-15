package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.GymkhanaRequest;
import eu.motogymkhana.server.api.GymkhanaResult;

public interface CheckPasswordResource {

	public GymkhanaResult checkPassword(GymkhanaRequest request);
}
