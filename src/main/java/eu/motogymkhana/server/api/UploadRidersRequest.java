package eu.motogymkhana.server.api;

import java.util.List;

import eu.motogymkhana.server.model.Rider;

public class UploadRidersRequest extends GymkhanaRequest {

	private List<Rider> riders;

	public UploadRidersRequest() {
	}

	public UploadRidersRequest(List<Rider> riders) {
		this.riders = riders;
	}

	public List<Rider> getRiders() {
		return riders;
	}

	public void setRiders(List<Rider> riders) {
		this.riders = riders;
	}

}
