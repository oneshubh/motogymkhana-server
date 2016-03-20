package eu.motogymkhana.server.api;

import eu.motogymkhana.server.model.Times;

/**
 * Created by christine on 15-5-15.
 */
public class UpdateTimesRequest extends GymkhanaRequest {

	private Times times;

	public UpdateTimesRequest(){
	}
	
	public UpdateTimesRequest(Times times) {
		this.times = times;
	}

	public Times getTimes() {
		return times;
	}
}
