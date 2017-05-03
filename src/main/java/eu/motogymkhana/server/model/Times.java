/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "times")
public class Times {

	private static final Log log = LogFactory.getLog(Rider.class);
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String RIDER_NUMBER = "number";
	public static final String ID = "_id";
	public static final String START_NUMBER = "startnumber";
	public static final String TIME1 = "time1";
	public static final String TIME2 = "time2";
	public static final String BEST_TIME = "best_time";
	public static final String REGISTERED = "registered";
	public static final String DATE = "date";;
	public static final String RIDER = "rider_id";
	private static final String PENALTIES1 = "pen1";
	private static final String PENALTIES2 = "pen2";
	private static final String DISQUALIFIED1 = "dis1";
	private static final String DISQUALIFIED2 = "dis2";
	private static final String TIMESTAMP = "timestamp";
	private static final String COUNTRY = "country";
	private static final String SEASON = "season";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private volatile int _id;

	@JsonProperty(COUNTRY)
	@Column(name = COUNTRY)
	private Country country;

	@JsonProperty(SEASON)
	@Column(name = SEASON)
	private int season;

	@JsonProperty(TIMESTAMP)
	@Column(name = TIMESTAMP)
	private long timeStamp;

	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	private Rider rider;

	@JsonProperty(DATE)
	@Column(name = DATE)
	private long date;

	@JsonProperty(START_NUMBER)
	@Column(name = START_NUMBER)
	private int startNumber;

	@JsonProperty(TIME1)
	@Column(name = TIME1)
	private int time1 = 0;

	@JsonProperty(TIME2)
	@Column(name = TIME2)
	private int time2 = 0;

	@JsonProperty(PENALTIES1)
	@Column(name = PENALTIES1)
	private int penalties1 = 0;

	@JsonProperty(PENALTIES2)
	@Column(name = PENALTIES2)
	private int penalties2 = 0;

	@JsonProperty(DISQUALIFIED1)
	@Column(name = DISQUALIFIED1)
	private boolean disqualified1;

	@JsonProperty(DISQUALIFIED2)
	@Column(name = DISQUALIFIED2)
	private boolean disqualified2;

	@JsonProperty(REGISTERED)
	@Column(name = REGISTERED)
	private boolean registered = true;

	@Transient
	@JsonProperty(Rider.RIDER_NUMBER)
	private int riderNumber;

	@Transient
	@JsonIgnore
	private int points = 0;

	@Transient
	@JsonIgnore
	private volatile int bestTimePlusPenalties;

	public Times() {
	}

	public Times(long date) {
		this.date = date;
	}

	public Times(Times times) {
		this.startNumber = times.getStartNumber();
		this.date = times.getDate();
		this.time1 = times.getTime1();
		this.time2 = times.getTime2();
		this.riderNumber = times.getRiderNumber();
		this.penalties1 = times.getPenalties1();
		this.penalties2 = times.getPenalties2();
		this.disqualified1 = times.isDisqualified1();
		this.disqualified2 = times.isDisqualified2();
		this.registered = times.isRegistered();
		this.country = times.getCountry();
		this.timeStamp = times.getTimeStamp();
		this.season = times.getSeason();
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int id) {
		this._id = id;
	}

	public void setStartNumber(int number) {
		this.startNumber = number;
	}

	public Integer getStartNumber() {
		return this.startNumber;
	}

	@JsonIgnore
	public String getStartNumberString() {
		return Integer.toString(startNumber);
	}

	public int getTime1() {
		return time1;
	}

	public int getTime2() {
		return time2;
	}

	public void setTime1(int milliseconds) {
		time1 = milliseconds;
	}

	public void setTime2(int milliseconds) {
		time2 = milliseconds;
	}

	@JsonIgnore
	public int getBestTime() {

		int t1 = time1 + 1000 * penalties1;
		int t2 = time1 + 1000 * penalties2;

		if (t2 == 0) {
			return t1;
		}

		return t1 < t2 ? t1 : t2;

	}

	@JsonIgnore
	public String getTime1PlusPenaltiesString() {
		return makeString(time1 + (penalties1 * 1000));
	}

	@JsonIgnore
	public String getTime2PlusPenaltiesString() {
		return makeString(time2 + (penalties2 * 1000));
	}

	@JsonIgnore
	public String getTime1String() {
		return makeString(time1);
	}

	@JsonIgnore
	public String getTime2String() {
		return makeString(time2);
	}

	@JsonIgnore
	public String getBestTimeString() {

		if (time2 != 0 && time2 < time1) {
			bestTimePlusPenalties = time2 + (penalties2 * 1000);
		} else {
			bestTimePlusPenalties = time1 + (penalties1 * 1000);
		}

		return makeString(bestTimePlusPenalties);
	}

	private String makeString(int time) {

		int minutes = time / 60000;
		int milliseconds = time - (minutes * 60000);
		int seconds = milliseconds / 1000;
		int centiseconds = (milliseconds - (seconds * 1000)) / 10;

		String secondsString = Integer.toString(seconds);
		String centiSecondsString = Integer.toString(centiseconds);

		return Integer.toString(minutes) + ":" + (secondsString.length() == 1 ? "0" : "")
				+ secondsString + "." + (centiSecondsString.length() == 1 ? "0" : "")
				+ centiSecondsString;
	}

	public void setRegistered(boolean isChecked) {
		registered = isChecked;
	}

	public boolean isRegistered() {
		return registered;
	}

	public long getDate() {
		return date;
	}

	public void setRider(Rider rider) {
		this.rider = rider;
	}

	public Rider getRider() {
		return rider;
	}

	@JsonIgnore
	public String getPenalties1String() {
		return Integer.toString(penalties1);
	}

	@JsonIgnore
	public String getPenalties2String() {
		return Integer.toString(penalties2);
	}

	public void setPenalties1String(String string) {
		if (string.length() > 0) {
			penalties1 = Integer.parseInt(string);
		}
	}

	public void setPenalties2String(String string) {
		if (string.length() > 0) {
			penalties2 = Integer.parseInt(string);
		}
	}

	public void setDisqualified1(boolean checked) {
		disqualified1 = checked;
	}

	public void setDisqualified2(boolean checked) {
		disqualified2 = checked;
	}

	public boolean isDisqualified1() {
		return disqualified1;
	}

	public boolean isDisqualified2() {
		return disqualified2;
	}

	public int getPenalties1() {
		return penalties1;
	}

	public int getPenalties2() {
		return penalties2;
	}

	public void setPoints(int p) {
		points = p;
	}

	public int getPoints() {
		return points;
	}

	public void setPenalties1(int penalties1) {
		this.penalties1 = penalties1;
	}

	public void setPenalties2(int penalties2) {
		this.penalties2 = penalties2;
	}

	public boolean newerThan(Times existingTimes) {
		boolean b = timeStamp > existingTimes.getTimeStamp();

		log.debug(dateFormat.format(timeStamp) + " newer than "
				+ dateFormat.format(existingTimes.getTimeStamp()) + " " + b);
		return b;
	}

	private long getTimeStamp() {
		return timeStamp;
	}

	public void merge(Times times) {

		time1 = times.getTime1();
		time2 = times.getTime2();
		penalties1 = times.getPenalties1();
		penalties2 = times.getPenalties2();
		points = times.getPoints();
		date = times.getDate();
		disqualified1 = times.isDisqualified1();
		disqualified2 = times.isDisqualified2();
		registered = times.isRegistered();
		timeStamp = times.getTimeStamp();
		startNumber = times.getStartNumber();
		country = times.getCountry();
		season = times.getSeason();
	}

	public void setDate(long date) {
		this.date = date;
	}

	public Country getCountry() {
		return country;
	}

	public int getSeason() {
		return season;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public int getRiderNumber() {
		return riderNumber;
	}

	public boolean has_id() {
		return _id != 0;
	}

	public String toString() {
		return _id + " " + country.name() + " " + season + " " + date;
	}
}
