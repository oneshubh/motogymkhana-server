package eu.motogymkhana.server.resource.ui.server;

import javax.persistence.EntityManager;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.request.UpdateRiderRequest;
import eu.motogymkhana.server.api.response.UpdateRegistrationResponse;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.persist.MyEntityManager;
import eu.motogymkhana.server.resource.ui.UIUpdateRiderResource;

public class UIUpdateRiderServerResource extends ServerResource implements UIUpdateRiderResource {

	@Inject
	private PasswordManager passwordManager;

	@Inject
	private MyEntityManager emp;

	@Inject
	private RiderDao riderDao;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post
	public UpdateRegistrationResponse updateRider(UpdateRiderRequest request) {

		UpdateRegistrationResponse response = new UpdateRegistrationResponse();
		response.setStatus(-1);

		if (request.getRider() == null
				|| !passwordManager.checkRiderPassword(request.getEmail(), request.getPassword())) {
			response.setStatus(404);
			return response;
		}

		EntityManager em = emp.getEM();
		em.clear();
		em.getTransaction().begin();

		try {

			riderDao.updateRider(request.getRider());

			em.getTransaction().commit();

		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}

		return response;

	}

}
