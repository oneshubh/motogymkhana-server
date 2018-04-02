/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.resource.server;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.request.GymkhanaRequest;
import eu.motogymkhana.server.api.result.ListRidersResult;
import eu.motogymkhana.server.api.result.ListRoundsResult;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.persist.MyEntityManager;
import eu.motogymkhana.server.resource.GetRoundsResource;
import eu.motogymkhana.server.round.RoundManager;

public class GetRoundsServerResource extends ServerResource implements GetRoundsResource {

	@Inject
	private RoundManager roundManager;

	@Inject
	private MyEntityManager emp;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post
	public ListRoundsResult getRounds(GymkhanaRequest request) {

		ListRoundsResult result = new ListRoundsResult();
		result.setResultCode(-1);
		
		if(request == null){
			return result;
		}

		EntityManager em = emp.getEM();
		em.clear();

		em.getTransaction().begin();

		try {
			Collection<Round> rounds = roundManager.getRounds(request.getCountry(),
					request.getSeason());

			result.setRounds(rounds);
			result.setResultCode(ListRidersResult.OK);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		return result;
	}

}
