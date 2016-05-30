package eu.motogymkhana.server.dao;

import java.util.List;

import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Times;

public interface TimesDao {

	int update(Times times);

	List<Times> getTimes(Country country, int season, long date);
}
