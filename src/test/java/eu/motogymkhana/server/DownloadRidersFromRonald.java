package eu.motogymkhana.server;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.resource.GetRidersResource;
import eu.motogymkhana.server.test.GuiceIntegration;

@RunWith(GuiceIntegration.class)
public class DownloadRidersFromRonald {

	@Inject
	private GetRidersResource getRidersResource;

	@Inject
	private RiderDao riderDao;

	private Country country = Country.NL;
	private int season = 2016;

	@Test
	public void downloadRiders() {

		//getRidersResource.loadFromRonaldsServer(country, season);

		List<Rider> riders = riderDao.getRiders(country, season);
		Assert.assertEquals(25, riders.size());

	}
}
