package eu.motogymkhana.server.timer.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import eu.motogymkhana.server.ServerConstants;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.http.HttpResultWrapper;
import eu.motogymkhana.server.http.MyHttpClient;
import eu.motogymkhana.server.http.UrlHelper;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.RonaldRider;
import eu.motogymkhana.server.properties.GymkhanaProperties;
import eu.motogymkhana.server.timer.TimerManager;

@Singleton
public class TimerManagerImpl implements TimerManager {

	private static final String RONALD_NL_TOKEN_STRING = "qXLM8Ya75n2CqS2iyYQOJmMGmqC5YZqpWwOWj37ryaOIi5eO";
	private static final String RONALD_EU_TOKEN_STRING = "3uqbnTEm7AtK82tkrmWziIXfGEhBy49ZiBqXmt6KKNHUCq2Q";

	@Inject
	private RiderDao riderDao;

	@Inject
	private MyHttpClient httpClient;

	@Inject
	private UrlHelper urlHelper;

	@Inject
	private ObjectMapper mapper;

	@Inject
	private Provider<EntityManager> emp;

	@InjectLogger
	private Log log;

	private Runnable downloadRidersTask = new Runnable() {

		@Override
		public void run() {

			GymkhanaProperties.init();

			if (GymkhanaProperties.getBooleanProperty(GymkhanaProperties.RELOAD_NL)) {
				loadFromRonaldsServerInThread(Country.NL);
			}

			if (GymkhanaProperties.getBooleanProperty(GymkhanaProperties.RELOAD_EU)) {
				loadFromRonaldsServerInThread(Country.EU);
			}
		}
	};

	@Override
	public void init() {

		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(downloadRidersTask, ServerConstants.RONALD_DOWNLOAD_START,
				ServerConstants.RONALD_DOWNLOAD_INTERVAL, ServerConstants.RONALD_DOWNLOAD_TIMEUNIT);
	}

	protected void loadFromRonaldsServerInThread(final Country country) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				loadFromRonaldsServer(country, 2016);
			}

		}).start();
	}

	private void loadFromRonaldsServer(Country country, int year) {

		String token = null;
		if (country == Country.NL) {
			token = RONALD_NL_TOKEN_STRING;
		} else {
			token = RONALD_EU_TOKEN_STRING;
		}

		log.debug("loading riders " + country + " " + year);

		try {
			HttpResultWrapper result = httpClient.getStringFromUrl(
					urlHelper.getFromRonaldsServer(country), token);

			if (result.isOK()) {

				EntityManager em = emp.get();
				em.getTransaction().begin();

				TypeFactory factory = mapper.getTypeFactory();

				Map<String, Map<String, List<String>>> map = mapper.readValue(result.getString(),
						factory.constructMapType(
								Map.class,
								factory.constructType(String.class),
								factory.constructMapType(Map.class,
										factory.constructType(String.class),
										factory.constructType(RonaldRider.class))));

				Map m = map.get("members");

				Iterator<RonaldRider> iterator = m.values().iterator();

				List<Integer> numbers = new LinkedList<Integer>();

				List<Rider> riders = riderDao.getRiders(country, year);
				for (Rider r : riders) {
					numbers.add(r.getRiderNumber());
				}
				List<Rider> lastYearsRiders = riderDao.getRiders(country, year - 1);

				log.debug("Number of riders: existing " + riders.size() + " new " + m.size());

				int riderNumber = 0;

				while (iterator.hasNext()) {

					RonaldRider rr = iterator.next();

					log.debug("importing rider " + rr.getFullName() + " " + rr.getCountry());

					Rider newRider;

					Rider existingRider = getMatchingRider(riders, rr);
					if (existingRider != null) {
						existingRider.update(rr);
					} else {

						Rider rider = getMatchingRider(lastYearsRiders, rr);
						riderNumber = getNextNumber(riderNumber, numbers);
						if (rider != null) {
							newRider = new Rider(rider, country, year, riderNumber);
						} else {
							newRider = new Rider(rr, country, year, riderNumber);
						}
						newRider.setEmail(rr.getEmail());
						em.persist(newRider);
					}
				}

				em.getTransaction().commit();
			} else {
				log.debug("server error " + country + " " + year + " " + result.getStatusCode()
						+ " " + result.getErrorMessage() + "\n\n" + result.getString() + "\n\n");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int getNextNumber(final int i, List<Integer> numbers) {

		int j = i + 1;
		while (numbers.contains(j)) {
			j++;
		}
		return j;
	}

	private Rider getMatchingRider(List<Rider> riders, RonaldRider rr) {
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
}
