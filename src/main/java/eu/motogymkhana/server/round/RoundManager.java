package eu.motogymkhana.server.round;

import java.util.Collection;
import java.util.List;

import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Round;

public interface RoundManager {

	Round getCurrentRound();

	Collection<Round> getRounds();
}
