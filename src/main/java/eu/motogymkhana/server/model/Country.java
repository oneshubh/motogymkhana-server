/*******************************************************************************
 * Copyright (c) 2015, 2016, 2017, 2018 Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.model;

/**
 * Created by christine on 29-5-15. 0 NL("NL"), 1 BE("BE"), 2 UK("UK"), 3
 * FR("FR"), 4 DE("DE"), 5 PL("PL"), 6 RU("RU"), 7 CZ("CZ"), 8 UA("UA"), 9
 * EU("EU"), 10 LT("LT"), 11 JP("JP")
 */
public enum Country {

	NL("NL"), BE("BE"), UK("UK"), FR("FR"), DE("DE"), PL("PL"), RU("RU"), CZ("CZ"), UA("UA"), EU(
			"EU"), LT("LT"), JP("JP"), MD("MD");

	private String string;

	Country(String string) {
		this.string = string;
	}

	public String toString() {
		return string;
	}
}
