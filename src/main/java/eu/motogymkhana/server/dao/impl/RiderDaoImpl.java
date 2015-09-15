package eu.motogymkhana.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.dao.TimesDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.model.Times;

public class RiderDaoImpl implements RiderDao {

	@InjectLogger
	private Log log;

	private Provider<EntityManager> emp;

	@Inject
	public RiderDaoImpl(Provider<EntityManager> emp) {
		this.emp = emp;
	}

	@Override
	public int updateRider(Rider rider) {

		EntityManager em = emp.get();

		try {

			TypedQuery<Rider> query = em.createQuery(
					"select a from " + Rider.class.getSimpleName()
							+ " a where a.riderNumber = :number", Rider.class);
			Rider existingRider = null;

			try {
				existingRider = query.setParameter("number", rider.getRiderNumber())
						.getSingleResult();

			} catch (NoResultException nre) {
			}

			if (existingRider == null) {

				em.persist(rider);
				for (Times t : rider.getTimes()) {
					t.setRider(rider);
				}

			} else {
			
				existingRider.merge(rider);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	@Override
	public int uploadRiders(List<Rider> riders) {

		EntityManager em = emp.get();

		try {

			for (Rider rider : riders) {

				Rider r = getRiderForNumber(rider.getRiderNumber());

				if (r != null) {
					em.remove(r);
				}

				for (Times t : rider.getTimes()) {
					t.setRider(rider);
				}

				em.persist(rider);
			}

			return 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public int updateRiders(List<Rider> riders) {

		EntityManager em = emp.get();

		try {

			for (Rider rider : riders) {

				updateRider(rider);
			}

			return 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	private Rider getRiderForNumber(int riderNumber) {

		EntityManager em = emp.get();
		Rider existingRider = null;

		TypedQuery<Rider> query = em.createQuery("select a from " + Rider.class.getSimpleName()
				+ " a where a.riderNumber = :number", Rider.class);

		try {
			existingRider = query.setParameter("number", riderNumber).getSingleResult();

		} catch (NoResultException nre) {
		}

		return existingRider;
	}

	@Override
	public List<Rider> getRiders() {

		EntityManager em = emp.get();

		TypedQuery<Rider> query = em.createQuery("select a from " + Rider.class.getSimpleName()
				+ " a", Rider.class);

		List<Rider> riders = query.getResultList();

		List<Rider> result = new ArrayList<Rider>();

		for (Rider r : riders) {
			em.refresh(r);
			result.add(r);
		}

		return result;

	}

	@Override
	public int deleteRider(Rider rider) {

		EntityManager em = emp.get();

		try {

			TypedQuery<Rider> query = emp.get().createQuery(
					"select a from " + Rider.class.getSimpleName()
							+ " a where a.riderNumber = :number", Rider.class);
			Rider existingRider = null;

			try {
				existingRider = query.setParameter("number", rider.getRiderNumber())
						.getSingleResult();

			} catch (NoResultException nre) {
			}

			if (existingRider != null) {
				em.remove(existingRider);

			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	@Override
	public List<Rider> getRegisteredRidersForDate(Round round) {

		List<Rider> riders = new ArrayList<Rider>();

		EntityManager em = emp.get();

		if (round == null) {
			return getRiders();
		}

		try {

			TypedQuery<Times> query = em.createQuery("select a from " + Times.class.getSimpleName()
					+ " a where a.registered = :registered and a.date = :date", Times.class);

			Times times = null;

			try {

				times = query.setParameter("registered", true)
						.setParameter("date", round.getDate()).getSingleResult();

				if (times != null) {
					riders.add(times.getRider());
				}

			} catch (NoResultException nre) {
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return riders;
	}
}
