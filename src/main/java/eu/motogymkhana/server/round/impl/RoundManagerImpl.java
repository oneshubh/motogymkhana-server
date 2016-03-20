package eu.motogymkhana.server.round.impl;

import java.util.Collection;

import com.google.inject.Inject;

import eu.motogymkhana.server.dao.RoundDao;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.round.RoundManager;

public class RoundManagerImpl implements RoundManager {

	@Inject
	private RoundDao roundDao;

	@Override
	public Round getCurrentRound(Country country, int season) {

		long yesterday = System.currentTimeMillis() - (24l * 3600l * 1000l);

		Round round = null;

		Collection<Round> rounds = roundDao.getRounds(country, season);

		if (rounds != null && rounds.size() > 0) {
			for (Round r : rounds) {

				if (round == null || round.getDate() < yesterday && round.getDate() > r.getDate()) {
					round = r;
				}
			}
		}

		return round;
	}

	@Override
	public Collection<Round> getRounds(Country country, int season) {
		return roundDao.getRounds(country, season);
	}
}
