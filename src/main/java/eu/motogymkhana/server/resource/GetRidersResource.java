package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.GymkhanaRequest;
import eu.motogymkhana.server.api.ListRidersResult;


public interface GetRidersResource {

	public ListRidersResult getRiders(GymkhanaRequest request);
}
