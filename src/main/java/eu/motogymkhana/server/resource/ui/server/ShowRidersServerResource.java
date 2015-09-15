package eu.motogymkhana.server.resource.ui.server;

import java.util.List;

import javax.persistence.EntityManager;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.resource.ui.ShowRidersResource;
import eu.motogymkhana.server.round.RoundManager;
import eu.motogymkhana.server.text.TextManager;

public class ShowRidersServerResource  extends ServerResource implements ShowRidersResource{
	
	@Inject
	private RiderDao riderDao;

	@Inject
	private TextManager textManager;
	
	@Inject
	private Provider<EntityManager> emp;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Get
	public ListRidersResult getRiders() {

		ListRidersResult result = new ListRidersResult();
		result.setResult(-1);
		result.setText(textManager.getText());

		EntityManager em = emp.get();

		em.getTransaction().begin();
		
		try {
			List<Rider> riders = riderDao.getRiders();
			
			result.setRiders(riders);
			result.setResult(ListRidersResult.OK);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		return result;
	}

}
