package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.UpdateSettingsRequest;
import eu.motogymkhana.server.api.UpdateSettingsResponse;

public interface UpdateSettingsResource {

	public UpdateSettingsResponse updateSettings(UpdateSettingsRequest request);
}
