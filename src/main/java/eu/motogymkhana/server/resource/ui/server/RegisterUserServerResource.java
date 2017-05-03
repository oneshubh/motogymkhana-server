/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.resource.ui.server;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

import eu.motogymkhana.server.api.request.RegisterUserRequest;
import eu.motogymkhana.server.api.result.RegisterRiderResult;
import eu.motogymkhana.server.dao.RiderAuthDao;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.RiderAuth;
import eu.motogymkhana.server.persist.MyEntityManager;
import eu.motogymkhana.server.properties.GymkhanaProperties;
import eu.motogymkhana.server.resource.ui.RegisterUserResource;

public class RegisterUserServerResource extends ServerResource implements RegisterUserResource {

	@Inject
	private RiderAuthDao riderAuthDao;

	@Inject
	private MyEntityManager emp;

	@Inject
	private RiderDao riderDao;

	String senderEmail = "noreply@gymcomp.com";

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
	}

	@Override
	@Post
	public RegisterRiderResult register(RegisterUserRequest request) {

		RegisterRiderResult result = new RegisterRiderResult();
		result.setResultCode(-1);

		EntityManager em = emp.getEM();
		em.clear();

		em.getTransaction().begin();

		try {

			RiderAuth riderAuth = null;
			Rider rider = null;

			try {
				riderAuth = riderAuthDao.get(request.getEmail());
			} catch (NoResultException nre) {
			}

			try {
				rider = riderDao.getRiderByEmail(request.getEmail());
			} catch (NoResultException nre) {
				result.setResultCode(-1);
			}

			if (rider != null) {
				if (riderAuth == null) {
					String token = UUID.randomUUID().toString();
					riderAuth = new RiderAuth(request.getEmail(), token);
					riderAuthDao.create(riderAuth);
				}
				
				if(!riderAuth.hasPassword()){

				sendEmail(request.getEmail(), riderAuth.getToken());

				result.setResultCode(RegisterRiderResult.OK);
				} else {
					result.setResultCode(RegisterRiderResult.OK);
				}
			}

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			result.setResultCode(RegisterRiderResult.NOT_OK);
		}

		return result;
	}

	private void sendEmail(String email, String token) {

		String messageText = GymkhanaProperties.getProperty("email_message");
		String subject = GymkhanaProperties.getProperty("email_subject");

		messageText = String.format(messageText, token);
		String host = GymkhanaProperties.getProperty("mailhost");
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject(subject);
			message.setText(messageText);

			Transport.send(message);
			System.out.println("Sent message successfully....");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
