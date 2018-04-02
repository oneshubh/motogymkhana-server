package eu.motogymkhana.server.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.request.GymkhanaRequest;
import eu.motogymkhana.server.api.result.ListRidersResult;
import eu.motogymkhana.server.guice.GuiceIntegration;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.resource.GetRidersResource;

@RunWith(GuiceIntegration.class)
public class GetRidersTest {

	@Inject
	protected GetRidersResource getRidersResource;

	@Test
	public void testGetRiders() {

		GymkhanaRequest request = new GymkhanaRequest(Country.NL, 2016);
		ListRidersResult result = getRidersResource.getRiders(request);

		Assert.assertEquals(25, result.getRiders().size());
	}
}
