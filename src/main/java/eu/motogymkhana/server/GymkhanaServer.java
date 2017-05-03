/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.guice.FinderFactory;
import org.restlet.ext.guice.RestletGuice;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.routing.Router;
import org.restlet.util.Series;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;

import eu.motogymkhana.server.guice.GymkhanaModule;
import eu.motogymkhana.server.jackson.MyJacksonConverter;
import eu.motogymkhana.server.persist.PersistenceInitializer;
import eu.motogymkhana.server.properties.GymkhanaProperties;
import eu.motogymkhana.server.resource.server.CheckPasswordServerResource;
import eu.motogymkhana.server.resource.server.DeleteRiderServerResource;
import eu.motogymkhana.server.resource.server.GetAllRidersServerResource;
import eu.motogymkhana.server.resource.server.GetRidersServerResource;
import eu.motogymkhana.server.resource.server.GetRoundsServerResource;
import eu.motogymkhana.server.resource.server.GetSettingsServerResource;
import eu.motogymkhana.server.resource.server.UpdateRiderServerResource;
import eu.motogymkhana.server.resource.server.UpdateRidersServerResource;
import eu.motogymkhana.server.resource.server.UpdateSettingsServerResource;
import eu.motogymkhana.server.resource.server.UpdateTextServerResource;
import eu.motogymkhana.server.resource.server.UpdateTimesServerResource;
import eu.motogymkhana.server.resource.server.UploadRidersServerResource;
import eu.motogymkhana.server.resource.server.UploadRoundsServerResource;
import eu.motogymkhana.server.resource.ui.server.RegisterUserServerResource;
import eu.motogymkhana.server.resource.ui.server.SendRiderTokenServerResource;
import eu.motogymkhana.server.resource.ui.server.ShowRidersServerResource;
import eu.motogymkhana.server.resource.ui.server.SigninRiderServerResource;
import eu.motogymkhana.server.resource.ui.server.UIGetRiderServerResource;
import eu.motogymkhana.server.resource.ui.server.UIUpdateRiderServerResource;

public class GymkhanaServer extends Application {

	private static final Log log = LogFactory.getLog(GymkhanaServer.class);

	public void startIt() throws Exception {

		System.setProperty("javax.net.debug", "ssl");

		System.getProperties().put("org.restlet.engine.loggerFacadeClass",
				"org.restlet.ext.slf4j.Slf4jLoggerFacade");
		SLF4JBridgeHandler.install();

		Injector injector = RestletGuice.createInjector(new GymkhanaModule(), new JpaPersistModule(
				"gymkhana-db"));

		injector.getInstance(PersistenceInitializer.class);

		FinderFactory ff = injector.getInstance(FinderFactory.class);

		GymkhanaProperties.init();

		/*
		 * main gymkhana server
		 */
		final Component mainComponent = new Component();
		Server server = mainComponent.getServers().add(Protocol.HTTPS,
				GymkhanaProperties.getIntProperty("https_port"));
		mainComponent.getServers().add(Protocol.HTTP,
				GymkhanaProperties.getIntProperty("http_port"));

		Series<Parameter> params = server.getContext().getParameters();

		params.add("sslContextFactory", "org.restlet.engine.ssl.DefaultSslContextFactory");
		params.add("keystorePath", GymkhanaProperties.getProperty("keystore_path"));
		params.add("keystorePassword", GymkhanaProperties.getProperty("keystore_password"));
		params.add("keystoreType", GymkhanaProperties.getProperty("keystore_type"));
		params.add("keyAlias", GymkhanaProperties.getProperty("key_alias"));
		params.add("keyPassword", GymkhanaProperties.getProperty("key_password"));

		final Router router = new Router(mainComponent.getContext().createChildContext());

		router.attach(ServerConstants.UPDATE_SETTINGS,
				ff.finder(UpdateSettingsServerResource.class));
		router.attach(ServerConstants.GET_SETTINGS, ff.finder(GetSettingsServerResource.class));

		router.attach(ServerConstants.UPDATE_RIDER, ff.finder(UpdateRiderServerResource.class));
		router.attach(ServerConstants.UPDATE_TIMES, ff.finder(UpdateTimesServerResource.class));
		router.attach(ServerConstants.DELETE_RIDER, ff.finder(DeleteRiderServerResource.class));
		router.attach(ServerConstants.GET_RIDERS, ff.finder(GetRidersServerResource.class));
		router.attach(ServerConstants.GET_ALL_RIDERS, ff.finder(GetAllRidersServerResource.class));
		router.attach(ServerConstants.UPLOAD_RIDERS, ff.finder(UploadRidersServerResource.class));
		router.attach(ServerConstants.UPDATE_RIDERS, ff.finder(UpdateRidersServerResource.class));
		router.attach(ServerConstants.UPDATE_TEXT, ff.finder(UpdateTextServerResource.class));
		router.attach(ServerConstants.UPLOAD_ROUNDS, ff.finder(UploadRoundsServerResource.class));
		router.attach(ServerConstants.GET_ROUNDS, ff.finder(GetRoundsServerResource.class));
		router.attach(ServerConstants.CHECK_PASSWORD, ff.finder(CheckPasswordServerResource.class));

		router.attach(ServerConstants.UI_GET_RIDERS, ff.finder(ShowRidersServerResource.class));
		router.attach(ServerConstants.UI_GET_ROUNDS, ff.finder(GetRoundsServerResource.class));
		router.attach(ServerConstants.UI_GET_SETTINGS, ff.finder(GetSettingsServerResource.class));
		router.attach(ServerConstants.UI_SEND_TOKEN, ff.finder(SendRiderTokenServerResource.class));
		router.attach(ServerConstants.UI_REGISTER_RIDER, ff.finder(RegisterUserServerResource.class));
		router.attach(ServerConstants.UI_SIGNIN_RIDER, ff.finder(SigninRiderServerResource.class));
		router.attach(ServerConstants.UI_GET_RIDER, ff.finder(UIGetRiderServerResource.class));
		router.attach(ServerConstants.UI_UPDATE_RIDER, ff.finder(UIUpdateRiderServerResource.class));

		mainComponent.getDefaultHost().attach(ServerConstants.MOTOGYMKHANA, router);
		mainComponent.start();

		replaceConverter(JacksonConverter.class, new MyJacksonConverter());
	}

	public static void main(final String[] args) throws Exception {

		try {
			new GymkhanaServer().startIt();
		} catch (Exception e) {
			log.error(e);
			if (e.getCause() != null) {
				log.error(e.getCause());
				if (e.getCause().getCause() != null) {
					log.error(e.getCause().getCause().getMessage());
				}
			}
		}
	}

	void replaceConverter(Class<? extends ConverterHelper> converterClass,
			ConverterHelper newConverter) {

		List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();

		for (ConverterHelper converter : converters) {

			if (converter.getClass().equals(converterClass)) {
				converters.remove(converter);
				break;
			}
		}

		converters.add(newConverter);
	}

}
