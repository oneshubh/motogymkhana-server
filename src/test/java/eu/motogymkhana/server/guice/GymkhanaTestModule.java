package eu.motogymkhana.server.guice;

import com.google.inject.AbstractModule;

import eu.motogymkhana.server.resource.GetRidersResource;
import eu.motogymkhana.server.resource.UpdateRiderResource;
import eu.motogymkhana.server.resource.UploadRidersResource;
import eu.motogymkhana.server.resource.server.GetRidersServerResource;
import eu.motogymkhana.server.resource.server.UpdateRiderServerResource;
import eu.motogymkhana.server.resource.server.UploadRidersServerResource;
import eu.motogymkhana.server.resource.ui.RegisterUserResource;
import eu.motogymkhana.server.resource.ui.server.RegisterUserServerResource;

/**
 * 
 * @author christine
 * 
 */
public class GymkhanaTestModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(UpdateRiderResource.class).to(UpdateRiderServerResource.class);
		bind(GetRidersResource.class).to(GetRidersServerResource.class);
		bind(UploadRidersResource.class).to(UploadRidersServerResource.class);
		bind(RegisterUserResource.class).to(RegisterUserServerResource.class);
	}
}
