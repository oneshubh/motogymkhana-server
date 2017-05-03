/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.model;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "rounds")
public class Round {

	public static final String DATE = "date";
	public static final String NUMBER = "number";
	private static final String TIMESTAMP = "timestamp";
	private static final String CURRENT = "current";
	private static final String COUNTRY = "country";
	private static final String SEASON = "season";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private int _id;

	@Column(name = SEASON)
	@JsonProperty(SEASON)
	private int season;

	@JsonProperty(COUNTRY)
	@Column(name = COUNTRY)
	private Country country;

	@Column(name = DATE)
	@JsonProperty(DATE)
	private Long date;

	@JsonProperty(NUMBER)
	@Column(name = NUMBER)
	private int number;

	@JsonProperty(TIMESTAMP)
	@Column(name = TIMESTAMP)
	private long timeStamp;

	@JsonProperty(CURRENT)
	@Column(name = CURRENT)
	private boolean current;

	public Round() {

	}

	public Round(long date) {
		this.date = date;
	}

	public int getNumber() {
		return number;
	}

	public long getDate() {
		return date;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int id) {
		this._id = id;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	public boolean newerThan(Round existingRound) {
		return timeStamp >= existingRound.getTimeStamp();
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void merge(Round round) {
		this.number = round.getNumber();
		this.current = round.isCurrent();
		this.date = round.getDate();
		this.timeStamp = round.getTimeStamp();
	}

	public Country getCountry() {
		return country;
	}

	public int getSeason() {
		return season;
	}

	@Override
	public boolean equals(Object otherRound) {
		return otherRound instanceof Round && ((Round) otherRound).getDate() == date;
	}
	
	@Override
	public int hashCode(){
		return date.intValue();
	}
}
