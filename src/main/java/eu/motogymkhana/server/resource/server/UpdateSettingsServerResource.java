package eu.motogymkhana.server.resource.server;

import javax.persistence.EntityManager;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.api.GymkhanaResult;
import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.api.UpdateSettingsRequest;
import eu.motogymkhana.server.api.UpdateSettingsResponse;
import eu.motogymkhana.server.dao.SettingsDao;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.resource.UpdateSettingsResource;

public class UpdateSettingsServerResource extends ServerResource implements UpdateSettingsResource {

	@Inject
	private Provider<EntityManager> emp;

	@Inject
	private SettingsDao settingsDao;
	
	@Inject
	private PasswordManager pwManager;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post
	public UpdateSettingsResponse updateSettings(UpdateSettingsRequest request) {
		
		UpdateSettingsResponse response = new UpdateSettingsResponse();

		if(!pwManager.checkPassword(request.getCountry(), request.getPassword())){	
			response.setStatus(404);
			return response;
		}

		emp.get().getTransaction().begin();

		try {
			
			settingsDao.store(request.getSettings());
			emp.get().getTransaction().commit();
			response.setStatus(200);
			
		} catch (Exception e) {
			response.setStatus(500);
			try {
				emp.get().getTransaction().rollback();
			} catch (Exception ee) {
			}
		}
		
		return response;
	}
}
