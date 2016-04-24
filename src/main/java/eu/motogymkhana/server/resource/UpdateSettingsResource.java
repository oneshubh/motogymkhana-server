package eu.motogymkhana.server.resource;

import eu.motogymkhana.server.api.UploadSettingsRequest;
import eu.motogymkhana.server.api.UpdateSettingsResponse;

public interface UpdateSettingsResource {

	public UpdateSettingsResponse updateSettings(UploadSettingsRequest request);
}
