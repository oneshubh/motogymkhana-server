package eu.motogymkhana.server.resource.server;

import java.util.Collection;

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
import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.api.ListRoundsResult;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.resource.GetRoundsResource;
import eu.motogymkhana.server.resource.ui.ShowRidersResource;
import eu.motogymkhana.server.round.RoundManager;

public class GetRoundsServerResource extends ServerResource implements GetRoundsResource {

	@Inject
	private RoundManager roundManager;

	@Inject
	private Provider<EntityManager> emp;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post
	public ListRoundsResult getRounds(GymkhanaRequest request) {

		ListRoundsResult result = new ListRoundsResult();
		result.setResultCode(-1);
		
		if(request == null){
			return result;
		}

		EntityManager em = emp.get();

		em.getTransaction().begin();

		try {
			Collection<Round> rounds = roundManager.getRounds(request.getCountry(),
					request.getSeason());

			result.setRounds(rounds);
			result.setResultCode(ListRidersResult.OK);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		return result;
	}

}
