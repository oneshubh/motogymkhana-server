package eu.motogymkhana.server.dao.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.dao.RoundDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Round;

public class RoundDaoImpl implements RoundDao {

	@InjectLogger
	private Log log;

	private Provider<EntityManager> emp;

	@Inject
	public RoundDaoImpl(Provider<EntityManager> emp) {
		this.emp = emp;
	}

	@Override
	public int storeRounds(Collection<Round> rounds) {

		Iterator<Round> iterator = rounds.iterator();

		try {

			while (iterator.hasNext()) {
				store(iterator.next());
			}
		} catch (Exception e) {
			return -1;
		}

		return 0;
	}

	@Override
	public int store(Round round) {

		EntityManager em = emp.get();

		try {

			TypedQuery<Round> query = emp
					.get()
					.createQuery(
							"select a from "
									+ Round.class.getSimpleName()
									+ " a where a.country = :country and a.season = :season and a.date = :date",
							Round.class);
			Round existingRound = null;

			try {
				existingRound = query.setParameter("date", round.getDate())
						.setParameter("country", round.getCountry())
						.setParameter("season", round.getSeason()).getSingleResult();

			} catch (NoResultException nre) {
			}

			if (existingRound == null) {

				em.persist(round);

			} else {

				if (round.newerThan(existingRound)) {

					existingRound.merge(round);
					em.merge(existingRound);
					round.set_id(existingRound.get_id());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;

	}

	@Override
	public Collection<Round> getRounds(Country country, int season) {

		EntityManager em = emp.get();

		TypedQuery<Round> query = em.createQuery("select a from " + Round.class.getSimpleName()
				+ " a where a.season = :season and a.country = :country", Round.class);

		List<Round> rounds = query.setParameter("country", country).setParameter("season", season)
				.getResultList();

		return rounds;
	}
}
