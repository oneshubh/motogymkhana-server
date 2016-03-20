package eu.motogymkhana.server.resource.server;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.GymkhanaRequest;
import eu.motogymkhana.server.api.GymkhanaResult;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.resource.CheckPasswordResource;

public class CheckPasswordServerResource extends ServerResource implements CheckPasswordResource {

	@Inject
	private PasswordManager passwordManager;

	@Override
	@Post("json")
	public GymkhanaResult checkPassword(GymkhanaRequest request) {

		GymkhanaResult result = new GymkhanaResult();

		boolean b = passwordManager.checkPassword(request.getCountry(), request.getPassword());
		result.setResultCode(b ? 0 : -1);

		return result;
	}
}
