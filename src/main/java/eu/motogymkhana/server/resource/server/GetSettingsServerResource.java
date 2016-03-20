package eu.motogymkhana.server.resource.server;

import javax.persistence.EntityManager;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.api.GymkhanaRequest;
import eu.motogymkhana.server.api.SettingsResult;
import eu.motogymkhana.server.dao.SettingsDao;
import eu.motogymkhana.server.resource.GetSettingsResource;
import eu.motogymkhana.server.settings.Settings;

public class GetSettingsServerResource extends ServerResource implements GetSettingsResource {

	@Inject
	private SettingsDao settingsDao;

	@Inject
	private Provider<EntityManager> emp;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post
	public SettingsResult getSettings(GymkhanaRequest request) {

		SettingsResult result = new SettingsResult();
		result.setResultCode(0);
		Settings settings = null;

		emp.get().getTransaction().begin();

		try {

			settings = settingsDao.getSettings(request.getCountry(), request.getSeason());
			result.setResultCode(200);
			emp.get().getTransaction().commit();

		} catch (Exception e) {
			try {
				emp.get().getTransaction().rollback();
			} catch (Exception ee) {

			}
		}

		result.setSettings(settings);
		return result;
	}
}
