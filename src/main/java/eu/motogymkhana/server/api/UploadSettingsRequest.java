package eu.motogymkhana.server.api;

import eu.motogymkhana.server.settings.Settings;

public class UploadSettingsRequest extends GymkhanaRequest{
	
	private Settings settings;
	
	public UploadSettingsRequest(){
		
	}
	
	public Settings getSettings(){
		return settings;
	}
}
