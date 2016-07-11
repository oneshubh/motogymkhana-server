package eu.motogymkhana.server;

import mock.MockRegisterRiderRequest;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.request.RegisterRiderRequest;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.properties.GymkhanaProperties;
import eu.motogymkhana.server.resource.ui.RegisterRiderResource;
import eu.motogymkhana.server.test.GuiceIntegration;

@RunWith(GuiceIntegration.class)
public class RegistrationEmailTest {

	@Inject
	private RegisterRiderResource registerRiderResource;
	
	@Inject
	private RiderDao riderDao;
	
	@Test
	public void testRegistrationEmail(){
		
		GymkhanaProperties.init();
		
		Rider rider = new Rider();
		rider.setCountry(Country.NL);
		rider.setSeason(2016);
		rider.setEmail("christine@christine.nl");
		rider.setRiderNumber(26);
		
		riderDao.updateRider(rider);
		
		RegisterRiderRequest request = new MockRegisterRiderRequest();
		
		registerRiderResource.register(request);
	}
}
