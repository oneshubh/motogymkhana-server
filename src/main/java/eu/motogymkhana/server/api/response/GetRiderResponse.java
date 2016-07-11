package eu.motogymkhana.server.api.response;

import eu.motogymkhana.server.model.Rider;

public class GetRiderResponse {

	int status;
	Rider rider;
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void setRider(Rider rider){
		this.rider = rider;
	}
}
