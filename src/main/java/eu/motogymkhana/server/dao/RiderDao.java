package eu.motogymkhana.server.dao;

import java.util.List;

import eu.motogymkhana.server.api.UploadRidersResponse;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Round;

public interface RiderDao {

	int updateRider(Rider rider);

	int uploadRiders(List<Rider> riders);

	List<Rider> getRiders();
	
	int deleteRider(Rider rider);

	int updateRiders(List<Rider> riders);

	List<Rider> getRegisteredRidersForDate(Round round);
}
