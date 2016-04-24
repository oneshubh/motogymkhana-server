package eu.motogymkhana.server.http.impl;

import eu.motogymkhana.server.http.UrlHelper;
import eu.motogymkhana.server.model.Country;

public class UrlHelperImpl implements UrlHelper {

	// https://api.motogymkhana.nl/v1/members?query=&pageLimit=250&page=1&type=temporary&sortExtra=
	private final static String ronaldUrl = "https://api.motogymkhana.%s/v1/members?query=&pageLimit=250&page=1&type=%s&sortExtra=";
	private static final String COMPETITION = "competition";
	private static final String TEMPORARY = "temporary";

	@Override
	public String getFromRonaldsServer(Country country) {
		if (country == Country.NL) {
			return String.format(ronaldUrl, country.name().toLowerCase(), COMPETITION);
		} else {
			return String.format(ronaldUrl, country.name().toLowerCase(), TEMPORARY);
		}
	}
}
