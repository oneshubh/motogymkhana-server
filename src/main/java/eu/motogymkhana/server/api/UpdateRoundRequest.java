package eu.motogymkhana.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.model.Round;

/**
 * Created by christine on 15-5-15.
 */
public class UpdateRoundRequest extends GymkhanaRequest {

	@JsonProperty("round")
	private Round round;

	public UpdateRoundRequest(Round round) {
		this.round = round;
	}

	public Round getRound() {
		return round;
	}
}
