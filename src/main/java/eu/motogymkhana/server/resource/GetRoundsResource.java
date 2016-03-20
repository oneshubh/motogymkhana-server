package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.GymkhanaRequest;
import eu.motogymkhana.server.api.ListRoundsResult;


public interface GetRoundsResource {

	ListRoundsResult getRounds(GymkhanaRequest request);

}
