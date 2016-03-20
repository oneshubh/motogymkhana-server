package eu.motogymkhana.server.api;

import eu.motogymkhana.server.settings.Settings;

public class SettingsResult extends GymkhanaResult{

	private Settings settings;
	
	public void setSettings(Settings settings){
		this.settings = settings;
	}
}
