package eu.motogymkhana.server.round;

import java.util.Collection;

import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Round;

public interface RoundManager {

	Round getCurrentRound(Country country, int season);

	Collection<Round> getRounds(Country country, int season);
}
