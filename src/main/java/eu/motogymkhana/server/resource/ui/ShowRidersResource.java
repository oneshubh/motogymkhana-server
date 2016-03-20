package eu.motogymkhana.server.resource.ui;

import eu.motogymkhana.server.api.GymkhanaRequest;
import eu.motogymkhana.server.api.ListRidersResult;

public interface ShowRidersResource {

	ListRidersResult getRiders(GymkhanaRequest request);

}
