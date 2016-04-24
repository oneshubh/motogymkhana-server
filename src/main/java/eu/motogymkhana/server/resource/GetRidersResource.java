package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.GymkhanaRequest;
import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.model.Country;


public interface GetRidersResource {

	public ListRidersResult getRiders(GymkhanaRequest request);
}
