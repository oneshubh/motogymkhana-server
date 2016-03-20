package eu.motogymkhana.server.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.dao.SettingsDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.settings.Settings;

public class SettingsDaoImpl implements SettingsDao {

	@InjectLogger
	private Log log;

	private Provider<EntityManager> emp;

	@Inject
	public SettingsDaoImpl(Provider<EntityManager> emp) {
		this.emp = emp;
	}

	@Override
	public Settings getSettings(Country country, int season) {

		EntityManager em = emp.get();

		TypedQuery<Settings> query = em.createQuery(
				"select a from " + Settings.class.getSimpleName()
						+ " a where a.season = :season and a.country = :country", Settings.class);

		List<Settings> result = query.setParameter("country", country)
				.setParameter("season", season).getResultList();

		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void store(Settings settings) {

		Settings existingSettings = getSettings(settings.getCountry(), settings.getSeason());
		if (existingSettings == null) {
			emp.get().persist(settings);
		} else {
			existingSettings.merge(settings);
		}
	}

	@Override
	public void initSettings() {

		store(new Settings(Country.NL, 2015, 4, 6, 105, 115));
		store(new Settings(Country.NL, 2016, 4, 6, 105, 115));
		store(new Settings(Country.EU, 2015, 4, 6, 105, 115));
		store(new Settings(Country.EU, 2016, 4, 6, 105, 115));
	}
}
