package eu.motogymkhana.server.api;

import eu.motogymkhana.server.settings.Settings;

public class UpdateSettingsRequest extends GymkhanaRequest{
	
	private Settings settings;
	
	public Settings getSettings(){
		return settings;
	}
}
