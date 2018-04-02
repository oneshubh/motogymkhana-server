package eu.motogymkhana.server.test;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.dao.TimesDao;
import eu.motogymkhana.server.guice.GuiceIntegration;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Times;
import eu.motogymkhana.server.persist.MyEntityManager;

@RunWith(GuiceIntegration.class)
public class UpdateTimesTest {

	@Inject
	private TimesDao timesDao;

	@Inject
	private RiderDao riderDao;

	@Inject
	private MyEntityManager emp;

	private Country country = Country.NL;
	private int season = 2016;
	private int time1 = 45000;

	@Test
	public void timesStoreTest() {

		Rider rider = new Rider();
	
		EntityManager em = emp.getEM();
		em.getTransaction().begin();

		riderDao.updateRider(rider);
		em.getTransaction().commit();

		List<Rider> riders = riderDao.getRiders(country, season);

		Times times = new Times();
		times.setCountry(country);
		times.setSeason(season);

		times.setRider(rider);
		rider.addTimes(times);
		times.setRegistered(true);

		em.getTransaction().begin();
		timesDao.update(times);
		em.getTransaction().commit();

		times.setTime1(time1);
		times.setPenalties1(4);

		em.getTransaction().begin();
		timesDao.update(times);
		em.getTransaction().commit();

		List<Rider> list = riderDao.getRiders(country, season);

		Assert.assertEquals(1, list.size());

		Rider r = list.get(0);
		Assert.assertEquals(1, r.getTimes().size());

		Iterator<Times> iterator = r.getTimes().iterator();

		Times t = iterator.next();
		Assert.assertTrue(t.isRegistered());
		Assert.assertEquals(time1, t.getTime1());
	}
}
