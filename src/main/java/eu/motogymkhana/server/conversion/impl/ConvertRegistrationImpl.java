package eu.motogymkhana.server.conversion.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.log4j.lf5.viewer.LogTable;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.conversion.ConvertRegistration;
import eu.motogymkhana.server.dao.RegistrationDao;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.dao.TimesDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.model.Bib;
import eu.motogymkhana.server.model.Registration;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.RonaldRider;
import eu.motogymkhana.server.model.Times;
import eu.motogymkhana.server.persist.MyEntityManager;

@Singleton
public class ConvertRegistrationImpl implements ConvertRegistration {

	@Inject
	private RiderDao riderDao;

	@Inject
	private TimesDao timesDao;

	@InjectLogger
	private Log log;

	private MyEntityManager emp;

	@Inject
	public ConvertRegistrationImpl(MyEntityManager emp) {
		this.emp = emp;
	}

	@Override
	public void initRegistrations() {

		updateSequences();

		List<Rider> existingRiders = new ArrayList<Rider>();

		for (Rider rider : riderDao.getAllRiders()) {

			if (rider.getFirstName() != null && rider.getLastName() != null) {

				log.debug("rider: " + rider.getFullName());

				Rider existingRegisteredRider = getMatchingRider(existingRiders, rider);

				if (existingRegisteredRider != null) {

					log.debug("existing rider: " + existingRegisteredRider.getFullName());

					existingRegisteredRider.setRiderId(existingRegisteredRider.getRiderId());

					Registration registration = new Registration(existingRegisteredRider,
							rider.getCountry(), rider.getSeason(), rider.getRiderNumber(),
							rider.getBib());
					registration.setDayRider(existingRegisteredRider.isDayRider());
					registration.setRiderNumber(existingRegisteredRider.getRiderNumber());

					existingRegisteredRider.getRegistrations().add(registration);

					log.debug("registration: " + registration.getSeason() + " "
							+ registration.getCountry() + " " + rider.get_id());

					List<Times> timesList = timesDao.getTimesForRiderId(rider.get_id());
					for (Times t : timesList) {
						t.setRider(existingRegisteredRider);
						existingRegisteredRider.addTimes(t);
						log.debug("new times " + t.get_id() + " " + t.getSeason() + " " + t.getCountry().name() + " ");
					}

				} else {

					log.debug("new rider: " + rider.getFullName() + " " + rider.get_id());

					rider.setRiderId(Integer.toString(rider.get_id()));
					Registration registration = new Registration(rider, rider.getCountry(),
							rider.getSeason(), rider.getRiderNumber(), Bib.Y);
					registration.setDayRider(rider.isDayRider());
					registration.setRiderNumber(rider.getRiderNumber());

					rider.getRegistrations().add(registration);

					existingRiders.add(rider);

					log.debug("registration: " + registration.getSeason() + " "
							+ registration.getCountry() + " " + rider.get_id());
				}
			}
		}
	}

	private void updateSequences() {

		String[] sqlScripts = new String[] { "select setval('settings__id_seq', 6);",
				"select setval('passwords__id_seq', 6);",
				"select setval('registrations__id_seq', 195);",
				"select setval('times__id_seq', 1375);", "select setval('rider_auth__id_seq', 8);",
				"select setval('riders__id_seq', 311);" };

		for (String sqlScript : sqlScripts) {

			Query q = emp.getEM().createNativeQuery(sqlScript);
			q.getResultList();
		}
	}

	private Rider getMatchingRider(List<Rider> riders, Rider rr) {
		// https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html#getLevenshteinDistance(java.lang.CharSequence,%20java.lang.CharSequence)

		int w = 99;
		String name = rr.getFullName();
		Rider resultRider = null;

		Iterator<Rider> iterator = riders.iterator();
		while (iterator.hasNext()) {
			Rider rider = iterator.next();
			String existingName = rider.getFullName();
			int weight = StringUtils.getLevenshteinDistance(name, existingName);
			if (weight < w) {
				w = weight;
				resultRider = rider;
			}
		}

		if (resultRider != null) {
			log.debug(rr.getFullName() + " "
					+ (resultRider != null ? resultRider.getFullName() : " null ") + " weight = "
					+ w);
		}

		if (w < 5) {
			return resultRider;
		} else {
			return null;
		}
	}

	@Override
	public void removeUnregisteredRiders() {

		for (Rider rider : riderDao.getAllRiders()) {

			if (!rider.hasRegistrations()) {
				emp.getEM()
						.createNativeQuery("delete from times where rider__id = " + rider.get_id())
						.executeUpdate();
				emp.getEM().createNativeQuery("delete from riders where _id = " + rider.get_id())
						.executeUpdate();
			}
		}
	}
}
