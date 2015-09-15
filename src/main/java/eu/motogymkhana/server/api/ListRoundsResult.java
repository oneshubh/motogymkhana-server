package eu.motogymkhana.server.api;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Round;

/**
 * wrapper for sending a collection of riders back to the UI
 * 
 * @author christine
 * 
 */
public class ListRoundsResult extends GymkhanaResult {

	@JsonProperty("rounds")
	private Collection<Round> rounds;

	public Collection<Round> getRounds() {
		return rounds;
	}

	public void setRounds(Collection<Round> rounds) {
		this.rounds = rounds;
	}

}
