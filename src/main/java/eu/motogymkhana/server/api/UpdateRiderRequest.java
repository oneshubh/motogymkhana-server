package eu.motogymkhana.server.api;

import eu.motogymkhana.server.model.Rider;

/**
 * Created by christine on 15-5-15.
 */
public class UpdateRiderRequest extends GymkhanaRequest {

	private Rider rider;

	public UpdateRiderRequest(){
		
	}
	
	public UpdateRiderRequest(Rider rider) {
		this.rider = rider;
	}

	public Rider getRider() {
		return rider;
	}
}
