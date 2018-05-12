/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.log4j.Logger;

import eu.motogymkhana.server.util.Util;

public class EngineLauncher implements Daemon {

	private static final Logger log = Logger.getLogger(EngineLauncher.class);

	private static GymkhanaServer gymkhana = null;

	@Override
	public void destroy() {
		log.debug("Daemon destroy");
	}

	@Override
	public void init(DaemonContext arg0) throws DaemonInitException, Exception {
		log.debug("Daemon init");
	}

	@Override
	public void start() throws Exception {
		log.debug("Daemon start");
		initialize();
	}

	@Override
	public void stop() throws Exception {
		log.debug("Daemon stop");
		terminate();
	}

	private void initialize() {

		if (gymkhana == null) {
			
			log.info("Starting the Engine");
			gymkhana = new GymkhanaServer();

			try {

				gymkhana.startIt();

			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	private void terminate() {

		if (gymkhana != null) {
			log.info("Stopping the Engine");

			try {
				gymkhana.stop();
			} catch (Exception e) {
				log.error(e);
			}

			log.info("Engine stopped");
		}
	}
}
