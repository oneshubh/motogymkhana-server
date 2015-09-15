package eu.motogymkhana.server.api;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.model.Round;

public class UploadRoundsRequest extends GymkhanaRequest {

	@JsonProperty("rounds")
	private Collection<Round> rounds;

	public UploadRoundsRequest() {
	}

	public UploadRoundsRequest(Collection<Round> rounds) {
		this.rounds = rounds;
	}

	public Collection<Round> getRounds() {
		return rounds;
	}
}
