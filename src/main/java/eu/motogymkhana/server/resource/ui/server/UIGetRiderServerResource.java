package eu.motogymkhana.server.resource.ui.server;

import javax.persistence.EntityManager;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.request.GetRiderRequest;
import eu.motogymkhana.server.api.response.GetRiderResponse;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.persist.MyEntityManager;
import eu.motogymkhana.server.resource.ui.UIGetRiderResource;

public class UIGetRiderServerResource extends ServerResource implements UIGetRiderResource {

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
	public GetRiderResponse getRider(GetRiderRequest request) {

		GetRiderResponse response = new GetRiderResponse();
		response.setStatus(-1);

		if (!passwordManager.checkRiderPassword(request.getEmail(), request.getPassword())) {
			response.setStatus(401);
			return response;
		}

		EntityManager em = emp.getEM();
		em.clear();
		em.getTransaction().begin();

		try {
			Rider rider = riderDao.getRiderByEmail(request.getEmail());
			response.setRider(rider);
			response.setStatus(200);
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
