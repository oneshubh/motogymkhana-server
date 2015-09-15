package eu.motogymkhana.server.dao;

import java.util.Collection;

import eu.motogymkhana.server.model.Round;

public interface RoundDao {

	int storeRounds(Collection<Round> rounds);

	int store(Round round);

	Collection<Round> getRounds();
}
