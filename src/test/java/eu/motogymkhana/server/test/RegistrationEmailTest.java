package eu.motogymkhana.server.test;

import mock.MockRegisterRiderRequest;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.request.RegisterUserRequest;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.guice.GuiceIntegration;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.properties.GymkhanaProperties;
import eu.motogymkhana.server.resource.ui.RegisterUserResource;

@RunWith(GuiceIntegration.class)
public class RegistrationEmailTest {

	@Inject
	private RegisterUserResource registerRiderResource;
	
	@Inject
	private RiderDao riderDao;
	
	@Test
	public void testRegistrationEmail(){
		
		GymkhanaProperties.init();
		
		Rider rider = new Rider();
		rider.setEmail("christine@christine.nl");
		rider.setRiderNumber(26);
		
		riderDao.updateRider(rider);
		
		RegisterUserRequest request = new MockRegisterRiderRequest();
		
		registerRiderResource.register(request);
	}
}
