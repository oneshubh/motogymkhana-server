package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.GymkhanaRequest;
import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.api.SettingsResult;

public interface GetSettingsResource {

	public SettingsResult getSettings(GymkhanaRequest request);
}
