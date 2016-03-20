package eu.motogymkhana.server.resource.server;

import javax.persistence.EntityManager;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.api.UpdateRiderRequest;
import eu.motogymkhana.server.api.UpdateRiderResponse;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.resource.UpdateRiderResource;

public class UpdateRiderServerResource extends ServerResource implements UpdateRiderResource {

	@Inject
	private RiderDao riderDao;
	
	@Inject
	private PasswordManager pwManager;

	@Inject
	private Provider<EntityManager> emp;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post("json")
	public UpdateRiderResponse updateRider(UpdateRiderRequest request) {

		UpdateRiderResponse response = new UpdateRiderResponse();
		
		if(!pwManager.checkPassword(request.getCountry(), request.getPassword())){	
			response.setStatus(404);
			return response;
		}

		EntityManager em = emp.get();

		em.getTransaction().begin();

		try {

			int result = riderDao.updateRider(request.getRider());

			response.setStatus(result);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}

		return response;
	}

}
