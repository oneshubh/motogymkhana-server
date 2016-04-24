package eu.motogymkhana.server.dao;

import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.settings.Settings;


public interface SettingsDao {

	void store(Settings settings);

	Settings getSettings(Country country, int season);

	void initSettings(Country country, int season);
}
