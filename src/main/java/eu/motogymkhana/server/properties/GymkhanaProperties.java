/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.motogymkhana.server.properties.util.Util;

public class GymkhanaProperties {

	public static final String RELOAD_NL = "reload_nl_motogymkhana";
	public static final String RELOAD_EU = "reload_eu_motogymkhana";

	private static final Log log = LogFactory.getLog(GymkhanaProperties.class);

	private static Properties properties;
	private static String path;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");

	private static boolean propertiesRead = false;

	public static void init() {
		if (!propertiesRead) {
			readProperties();
			propertiesRead = true;
		}
	}

	private static void readProperties() {

		path = "/home/christine" + File.separator + Constants.settings_properties_file;

		File settingsFile = new File(path);

		properties = new Properties();

		if (settingsFile.exists()) {

			try {

				InputStream is = new FileInputStream(settingsFile);

				properties.load(is);

			} catch (IOException e) {
				Util.stacktraceToString(e);
				e.printStackTrace();
			}
		}
		for (Object key : properties.keySet()) {
			log.debug("prop: " + (String) key + " " + properties.getProperty((String) key));
		}
	}

	public static boolean containsKey(Object key) {
		return properties.containsKey(key);
	}

	public static boolean hasProperty(Object key) {
		return containsKey(key);
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static int getIntProperty(String key) {
		String stringProp = getProperty(key);
		int result = -1;
		if (NumberUtils.isNumber(stringProp)) {
			result = NumberUtils.toInt(stringProp);
		}
		return result;
	}

	public static boolean getBooleanProperty(String key) {
		String stringProp = getProperty(key);
		return Boolean.parseBoolean(stringProp);
	}

	public static void setBooleanProperty(String key, boolean b) {
		properties.setProperty(key, b ? "true" : "false");
	}

	public static void save() {

		try {

			FileOutputStream fos = new FileOutputStream(new File(path));

			properties.store(fos, "** " + dateFormat.format(new Date()));

		} catch (FileNotFoundException e) {
			Util.stacktraceToString(e);
			e.printStackTrace();
		} catch (IOException e) {
			Util.stacktraceToString(e);
			e.printStackTrace();
		}
	}
}
