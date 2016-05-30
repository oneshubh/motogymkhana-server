package eu.motogymkhana.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.dao.TimesDao;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Times;

public class TimesDaoImpl implements TimesDao {

	private Provider<EntityManager> emp;
	private RiderDao riderDao;

	@Inject
	public TimesDaoImpl(Provider<EntityManager> emp, RiderDao riderDao) {
		this.emp = emp;
		this.riderDao = riderDao;
	}

	@Override
	public int update(Times times) {

		EntityManager em = emp.get();

		try {

			TypedQuery<Times> query = em
					.createQuery(
							"select a from "
									+ Times.class.getSimpleName()
									+ " a where a.rider.riderNumber = :number and a.country = :country and a.season = :season and a.date = :date",
							Times.class);
			Times existingTimes = null;

			try {
				existingTimes = query.setParameter("country", times.getCountry())
						.setParameter("season", times.getSeason())
						.setParameter("date", times.getDate())
						.setParameter("number", times.getRiderNumber()).getSingleResult();

			} catch (NoResultException nre) {
			}

			if (existingTimes == null) {

				Rider rider = riderDao.getRiderForNumber(times.getCountry(), times.getSeason(),
						times.getRiderNumber());
				times.setRider(rider);
				em.persist(times);
				rider.addTimes(times);

			} else {
				existingTimes.merge(times);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	@Override
	public List<Times> getTimes(Country country, int season, long date) {

		EntityManager em = emp.get();
		
		List<Times> result = new ArrayList<Times>();

		TypedQuery<Times> query = em
				.createQuery(
						"select a from "
								+ Times.class.getSimpleName()
								+ " a where a.country = :country and a.season = :season and a.date = :date",
						Times.class);

		try {
			result = query.setParameter("country", country).setParameter("season", season)
					.setParameter("date", date).getResultList();

		} catch (NoResultException nre) {
		}

		return result;
	}
}
