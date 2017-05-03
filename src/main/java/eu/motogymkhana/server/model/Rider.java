/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "riders")
public class Rider {

	private static final Log log = LogFactory.getLog(Rider.class);

	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String RIDER_NUMBER = "number";
	public static final String DAY_RIDER = "day_rider";
	public static final String POINTS = "points";
	public static final String ID = "_id";
	public static final String GENDER = "gender";
	public static final String DATE_OF_BIRTH = "dob";
	public static final String COUNTRY = "country";
	public static final String NATIONALITY = "nationality";
	public static final String BIB = "bib";
	public static final String TEXT = "text";
	public static final String TIMES = "times";
	public static final String TIMESTAMP = "timestamp";
	public static final String SEASON = "season";
	public static final String BIKE = "bike";
	public static final String IMAGE_URL = "image_url";
	public static final String BIKE_IMAGE_URL = "bike_image_url";
	public static final String EMAIL = "email";
	public static final String REGISTRATION = "registration";
	public static final String RIDER_ID = "rider_id";
	public static final String ANDROID_ID = "android_id";
	public static final String HIDE_LASTNAME = "hide_lastname";

	private static final int roundsCountingForSeasonResult = 6;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private int _id;

	@JsonProperty(RIDER_ID)
	@Column(name = RIDER_ID)
	private String rider_id;

	@JsonProperty(ANDROID_ID)
	@Column(name = ANDROID_ID)
	private String androidId;

	@JsonProperty(REGISTRATION)
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	protected Set<Registration> registrations;

	@JsonProperty(TIMES)
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Times> timesList;

	@JsonProperty(SEASON)
	@Column(name = SEASON)
	protected int season;

	public int getSeason() {
		return season;
	}

	public Country getCountry() {
		return country;
	}

	@JsonProperty(COUNTRY)
	@Column(name = COUNTRY)
	protected Country country;

	@JsonProperty(NATIONALITY)
	@Column(name = NATIONALITY)
	private Country nationality;

	@JsonProperty(BIKE)
	@Column(name = BIKE)
	private String bike;

	@JsonProperty(IMAGE_URL)
	@Column(name = IMAGE_URL)
	private String imageUrl;

	@JsonProperty(BIKE_IMAGE_URL)
	@Column(name = BIKE_IMAGE_URL)
	private String bikeImageUrl;

	@JsonProperty(TIMESTAMP)
	@Column(name = TIMESTAMP)
	private long timeStamp;

	@JsonProperty(FIRSTNAME)
	@Column(name = FIRSTNAME)
	private String firstname;

	@JsonProperty(LASTNAME)
	@Column(name = LASTNAME)
	private String lastname;

	@JsonProperty(HIDE_LASTNAME)
	@Column(name = HIDE_LASTNAME)
	private Boolean hideLastname = false;

	@JsonProperty(value = EMAIL)
	@Column(name = EMAIL)
	protected String email;

	@JsonProperty(RIDER_NUMBER)
	@Column(name = RIDER_NUMBER)
	private int riderNumber;

	@JsonProperty(DAY_RIDER)
	@Column(name = DAY_RIDER)
	private boolean dayRider;

	@JsonProperty(GENDER)
	@Column(name = GENDER)
	private Gender gender;

	@JsonProperty(DATE_OF_BIRTH)
	@Column(name = DATE_OF_BIRTH)
	private String dateOfBirth;

	@JsonProperty(BIB)
	@Column(name = BIB)
	private Bib bib;

	@JsonProperty(TEXT)
	@Column(name = TEXT)
	private String text;

	@Column(name = POINTS)
	@JsonIgnore
	private int totalPoints;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	private RiderAuth riderAuth;

	@Transient
	@JsonIgnore
	private volatile int position;

	public Rider() {

	}

	public Rider(String riderString) {

		if (riderString.length() > 0) {

			riderString = riderString.replaceAll("\t", " ");
			riderString = riderString.replaceAll("  ", " ");
			String[] splitString = riderString.split(" ");

			if (splitString.length > 0) {
				firstname = splitString[1];

				if (splitString.length > 1) {
					lastname = splitString[2];
				}
			}
			for (int i = 3; i < splitString.length; i++) {
				lastname = lastname + " " + splitString[i];
			}

			riderNumber = Integer.parseInt(splitString[0]);
		}
	}

	public Rider(int number, String firstName, String lastName) {
		riderNumber = number;
		this.firstname = firstName;
		this.lastname = lastName;
	}

	public Rider(RonaldRider rr, Country country, int season, int riderNumber) {
		update(rr);
		bib = Bib.Y;
		// this.country = country;
		// this.season = season;
		this.riderNumber = riderNumber;
		gender = Gender.F;
	}

	private String getText() {
		return text;
	}

	private String getBikeImageUrl() {
		return bikeImageUrl;
	}

	private String getBike() {
		return bike;
	}

	public String getEmail() {
		return email;
	}

	public int getRiderNumber() {
		return riderNumber;
	}

	public String getFirstName() {
		return firstname;
	}

	public String getLastName() {
		return lastname;
	}

	@JsonIgnore
	public String getRiderNumberString() {
		return Integer.toString(riderNumber);
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int id) {
		this._id = id;
	}

	public Times getTimes(long date) {

		if (timesList != null) {

			Iterator<Times> iterator = timesList.iterator();

			while (iterator.hasNext()) {
				Times t = iterator.next();
				if (date == t.getDate()) {
					return t;
				}
			}
		}
		return null;
	}

	public Collection<Times> getTimes() {
		return timesList;
	}

	public boolean hasEUTimes() {
		Times euTimes = getEUTimes();
		return euTimes != null && euTimes.getBestTime() > 0;
	}

	private Times getEUTimes() {

		Iterator<Times> iterator = timesList.iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		}

		return null;
	}

	@JsonIgnore
	public String getTime1() {

		Times euTimes = getEUTimes();

		if (euTimes != null) {
			return euTimes.getTime1String();
		} else {
			return "";
		}
	}

	@JsonIgnore
	public String getTime2() {

		Times euTimes = getEUTimes();

		if (euTimes != null) {
			return euTimes.getTime2String();
		} else {
			return "";
		}
	}

	@JsonIgnore
	public int getPenalty1() {

		Times euTimes = getEUTimes();

		if (euTimes != null) {
			return euTimes.getPenalties1();
		} else {
			return 0;
		}
	}

	@JsonIgnore
	public int getPenalty2() {

		Times euTimes = getEUTimes();

		if (euTimes != null) {
			return euTimes.getPenalties2();
		} else {
			return 0;
		}
	}

	@JsonIgnore
	public String getBestTimeString() {

		Times euTimes = getEUTimes();

		if (euTimes != null) {
			return euTimes.getBestTimeString();
		} else {
			return "";
		}
	}

	@JsonIgnore
	public int getBestTime() {

		if (getEUTimes() != null) {
			return getEUTimes().getBestTime();
		} else {
			return 0;
		}

	}

	public void addTimes(Times times) {
		timesList.add(times);
		times.setRider(this);
	}

	public int getTotalPoints() {

		List<Integer> totalPointsList = new ArrayList<Integer>();

		for (Times times : timesList) {
			totalPointsList.add(times.getPoints());
		}

		Collections.sort(totalPointsList);

		while (totalPointsList.size() > roundsCountingForSeasonResult) {
			totalPointsList.remove(0);
		}

		totalPoints = 0;

		for (Integer i : totalPointsList) {
			totalPoints += i;
		}

		return totalPoints;
	}

	public String getTotalPointsString() {
		return Integer.toString(getTotalPoints());
	}

	public boolean hasTimes() {
		return getEUTimes() != null;
	}

	public int getFirstTimeForSort(long date) {

		Times times = getTimes(date);

		return times.getPenalties1() + (times.getTime1() != 0 ? times.getTime1() : 360000);
	}

	public boolean isDayRider() {
		return dayRider;
	}

	public void setDayRider(boolean dayRider) {
		this.dayRider = dayRider;
	}

	public boolean isValid() {
		return firstname != null && firstname.length() > 0 && lastname != null
				&& lastname.length() > 0 && riderNumber > 0;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(dateOfBirth).append(riderNumber).toHashCode();
	}

	public void setPosition(int i) {
		position = i;
	}

	@JsonIgnore
	public int getPosition() {
		return position;
	}

	public Gender getGender() {
		return gender;
	}

	public void merge(Rider rider, EntityManager em) {

		log.debug("xtien merge rider " + rider.get_id() + " " + rider.getFirstName() + " "
				+ rider.getLastName());

		firstname = rider.getFirstName();
		lastname = rider.getLastName();
		totalPoints = rider.getTotalPoints();
		dayRider = rider.isDayRider();
		nationality = rider.getNationality();
		gender = rider.getGender();
		hideLastname = rider.isHideLastname();
		email = rider.getEmail();

		mergeRegistrations(rider.getRegistrations(), em);
		mergeTimes(rider.getTimes(), em);

		if (rider.hasText()) {
			text = rider.getText();
		}
		if (rider.hasBike()) {
			bike = rider.getBike();
		}
		if (rider.hasImageUrl()) {
			imageUrl = rider.getImageUrl();
		}
		if (rider.hasBikeImageUrl()) {
			bikeImageUrl = rider.getBikeImageUrl();
		}
	}

	public void mergeRegistrations(Collection<Registration> registrations, EntityManager em) {
		log.debug("Merge registrations " + getFullName());

		for (Registration registration : registrations) {
			log.debug("mergeRegistrations " + getFullName() + " " + registration.getSeason() + " "
					+ registration.getCountry());
			boolean found = false;

			for (Registration reg : this.registrations) {
				if (reg.getSeason() == registration.getSeason()
						&& reg.getCountry() == registration.getCountry()) {
					found = true;
					log.debug("found " + reg.getSeason() + " " + reg.getCountry());
					reg.setRegistered(registration.isRegistered());
					reg.setDayRider(registration.isDayRider());
					reg.setBib(registration.getBib());
				}
			}

			if (!found) {
				Registration resultRegistration = null;

				TypedQuery<Registration> query = em.createQuery(
						"select a from " + Registration.class.getSimpleName()
								+ " a where a.rider = :rider and a.season = :season and a.country = :country",
						Registration.class);
				query.setParameter("rider", this);
				query.setParameter("season", registration.getSeason());
				query.setParameter("country", registration.getCountry());

				try {
					resultRegistration = query.getSingleResult();

				} catch (NoResultException nre) {
				}

				if (resultRegistration != null) {
					em.remove(resultRegistration);
				}

				log.debug(
						"new registration " + registration.get_id() + " " + registration.getSeason()
								+ " " + registration.getCountry() + " " + getFullName());
				registration.setRider(this);
				em.persist(registration);
				this.registrations.add(registration);
				registration.setRider(this);
				em.persist(registration);
				this.registrations.add(registration);
			}
		}
	}

	public void mergeTimes(Collection<Times> newTimesList, EntityManager em) {
		log.debug("merge Times ");

		for (Times newTimes : newTimesList) {

			boolean found = false;

			for (Times times : this.timesList) {

				if (times.getDate() == newTimes.getDate()) {

					log.debug("times found " + times.toString() + " " + newTimes.toString());

					found = true;

					if (newTimes.newerThan(times)) {
						times.merge(newTimes);
					}

					break;
				}
			}

			if (!found) {

				log.debug("new Times " + newTimes.getDate() + " " + getFullName() + " " + _id);
				newTimes.setRider(this);
				em.persist(newTimes);
				this.timesList.add(newTimes);

				TypedQuery<Times> query = em.createQuery(
						"select a from " + Times.class.getSimpleName()
								+ " a where a.rider = :rider and a.date = :date and a.season = :season and a.country = :country",
						Times.class);
				query.setParameter("rider", newTimes.getRider());
				query.setParameter("country", newTimes.getCountry());
				query.setParameter("season", newTimes.getSeason());
				query.setParameter("date", newTimes.getDate());

				List<Times> resultTimes = null;

				try {
					resultTimes = query.getResultList();
				} catch (NoResultException nre) {

				}

				if (resultTimes != null && resultTimes.size() > 1) {
					for (Times t : resultTimes) {
						log.debug("result times found " + t.toString());
					}
				}
			}
		}
	}

	public Collection<Registration> getRegistrations() {
		return registrations;
	}

	private String getImageUrl() {
		return imageUrl;
	}

	private boolean hasImageUrl() {
		return imageUrl != null;
	}

	private boolean hasBikeImageUrl() {
		return bikeImageUrl != null;
	}

	private boolean hasText() {
		return text != null;
	}

	private boolean hasBike() {
		return bike != null;
	}

	private Country getNationality() {
		return nationality;
	}

	public Bib getBib() {
		return bib;
	}

	@JsonIgnore
	public boolean isRegistered() {

		if (getEUTimes() == null) {
			return false;
		} else {
			return getEUTimes().isRegistered();
		}
	}

	public boolean newerThan(Rider r) {
		return timeStamp > r.getTimeStamp();
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void update(RonaldRider rr) {

		firstname = rr.getFirstName();
		lastname = rr.getLastName();
		email = rr.getEmail();
		setNationality(rr.getCountry());
		imageUrl = rr.getProfilePicture().replaceAll("\\\\", "");
	}

	private void setNationality(String string) {

		if (string.startsWith("Be")) {
			nationality = Country.BE;
		} else {
			nationality = Country.NL;
		}
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return firstname + " " + lastname;
	}

	public void setRiderNumber(int number) {
		this.riderNumber = number;
	}

	public boolean hasEmail() {
		return email != null;
	}

	@Override
	public boolean equals(Object other) {

		if (!(other instanceof Rider)) {
			return false;
		}
		Rider otherRider = (Rider) other;

		if (_id != 0 && _id == otherRider.get_id()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasRegistrations() {
		return registrations != null && registrations.size() > 0;
	}

	public boolean hasProfile() {
		return imageUrl != null && bikeImageUrl != null;
	}

	public void addProfile(Rider rider) {
		imageUrl = rider.getImageUrl();
		bikeImageUrl = rider.getBikeImageUrl();
		text = rider.getText();
	}

	public boolean hasId() {
		return rider_id != null;
	}

	public void setRiderId(String id) {
		this.rider_id = id;
	}

	public String getRiderId() {
		return rider_id;
	}

	public void nullTimes() {
		this.timesList = null;
	}

	@Override
	public String toString() {

		String string = "";
		for (Registration r : registrations) {
			string += " " + r.getSeason();
		}
		for (Times t : timesList) {
			string += " " + t.getSeason();
		}

		return Integer.toString(_id) + " " + firstname + " " + lastname + " " + getTime1() + " "
				+ getTime2() + string;
	}

	public boolean isHideLastname() {
		return hideLastname != null && hideLastname;
	}

	public void setLastName(String lastName) {
		this.lastname = lastName;
	}
}
