package eu.motogymkhana.server.dao;

import java.util.List;

import eu.motogymkhana.server.api.UploadRidersResponse;
import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Round;

public interface RiderDao {

	int updateRider(Rider rider);

	int uploadRiders(Country country, int season, List<Rider> riders);

	List<Rider> getRiders(Country country, int year);

	int deleteRider(Rider rider);

	int updateRiders(List<Rider> riders);

	List<Rider> getRegisteredRidersForDate(Round round);

	Rider getRiderForNumber(Country country, int season, int riderNumber);
}