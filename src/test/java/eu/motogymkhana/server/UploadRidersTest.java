package eu.motogymkhana.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.ListRidersResult;
import eu.motogymkhana.server.api.UpdateRiderRequest;
import eu.motogymkhana.server.api.UploadRidersRequest;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.model.Times;
import eu.motogymkhana.server.resource.GetRidersResource;
import eu.motogymkhana.server.resource.UpdateRiderResource;
import eu.motogymkhana.server.resource.UploadRidersResource;
import eu.motogymkhana.server.test.GuiceIntegration;

@RunWith(GuiceIntegration.class)
public class UploadRidersTest {

	@Inject
	private UpdateRiderResource updateRiderResource;

	@Inject
	private GetRidersResource getRidersResource;

	@Inject
	private UploadRidersResource uploadRidersResource;

	private String date = "20150524";
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

	@Test
	public void testUpdateRider() throws ParseException {

		Rider rider = new Rider("2 Jan Jansen");
		updateRiderResource.updateRider(new UpdateRiderRequest(rider));

		Round round = new Round(dateFormat.parse(date).getTime());

		Times times = new Times(dateFormat.parse(date).getTime());
		times.setDate(round.getDate());
		times.setRider(rider);
		rider.addTimes(times);

		updateRiderResource.updateRider(new UpdateRiderRequest(rider));

		ListRidersResult result = getRidersResource.getRiders();
		Collection<Rider> riders = result.getRiders();

		Assert.assertNotNull(riders);
		Assert.assertEquals(1, riders.size());

		Rider r = riders.iterator().next();

		Assert.assertNotNull(r.getTimes());
		Assert.assertEquals(1, r.getTimes().size());

	}

	@Test
	public void testUpLoadRiders() throws ParseException {

		Rider rider1 = new Rider("1 Pien Plong");
		Rider rider2 = new Rider("2 Mona Moon");
		Rider rider3 = new Rider("3 Rina Reinders");

		List<Rider> riders = new ArrayList<Rider>();

		riders.add(rider1);
		riders.add(rider2);
		riders.add(rider3);

		Round round = new Round(dateFormat.parse(date).getTime());

		Times times1 = new Times(dateFormat.parse(date).getTime());
		times1.setDate(round.getDate());
		times1.setRider(rider1);
		rider1.addTimes(times1);

		Times times2 = new Times(dateFormat.parse(date).getTime());
		times2.setDate(round.getDate());
		times2.setRider(rider2);
		rider2.addTimes(times2);

		Times times3 = new Times(dateFormat.parse(date).getTime());
		times3.setDate(round.getDate());
		times3.setRider(rider3);
		rider3.addTimes(times3);

		uploadRidersResource.uploadRiders(new UploadRidersRequest(riders));

		Collection<Rider> result = getRidersResource.getRiders().getRiders();

		Assert.assertNotNull(result);
		Assert.assertEquals(3, result.size());

		Rider r = result.iterator().next();

		Assert.assertNotNull(r.getTimes());
		Assert.assertEquals(1, r.getTimes().size());
	}
}
