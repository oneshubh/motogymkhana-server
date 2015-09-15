package eu.motogymkhana.server.resource.server;

import javax.persistence.EntityManager;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.api.UploadRidersRequest;
import eu.motogymkhana.server.api.UploadRidersResponse;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.resource.UpdateRidersResource;

public class UpdateRidersServerResource extends ServerResource implements UpdateRidersResource {

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
	public UploadRidersResponse updateRiders(UploadRidersRequest request) {

		UploadRidersResponse response = new UploadRidersResponse();

		if(!pwManager.checkPassword(request.getCustomerCode(), request.getPassword())){	
			response.setStatus(404);
			return response;
		}

		EntityManager em = emp.get();

		em.getTransaction().begin();

		try {

			response.setStatus(riderDao.updateRiders(request.getRiders()));
			response.setNumberOfRiders(request.getRiders().size());
			
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}

		return response;
	}

}
