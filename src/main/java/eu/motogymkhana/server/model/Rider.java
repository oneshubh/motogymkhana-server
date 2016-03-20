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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	private static final int roundsCountingForSeasonResult = 6;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private int _id;

	@JsonProperty(SEASON)
	@Column(name = SEASON)
	private int season;

	@JsonProperty(COUNTRY)
	@Column(name = COUNTRY)
	private Country country;

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
	private String firstName;

	@JsonProperty(LASTNAME)
	@Column(name = LASTNAME)
	private String lastName;
	
	@JsonProperty(EMAIL)
	@Column(name = EMAIL)
	private String email;

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

	@JsonProperty(TIMES)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Times> timesList = new HashSet<Times>();

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
				firstName = splitString[1];

				if (splitString.length > 1) {
					lastName = splitString[2];
				}
			}
			for (int i = 3; i < splitString.length; i++) {
				lastName = lastName + " " + splitString[i];
			}

			riderNumber = Integer.parseInt(splitString[0]);
		}
	}

	public Rider(int number, String firstName, String lastName) {
		riderNumber = number;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getRiderNumber() {
		return riderNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
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
		return firstName != null && firstName.length() > 0 && lastName != null
				&& lastName.length() > 0 && riderNumber > 0;
	}

	@Override
	public boolean equals(Object other) {

		if (other != null && other instanceof Rider) {

			Rider otherRider = (Rider) other;
			return otherRider.getRiderNumber() == riderNumber;

		} else {
			return false;
		}
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

	public void merge(Rider rider) {

		log.debug("xtien merge rider " + rider.getRiderNumber() + " " + rider.getFirstName() + " "
				+ rider.getLastName());

		firstName = rider.getFirstName();
		lastName = rider.getLastName();
		riderNumber = rider.getRiderNumber();
		totalPoints = rider.getTotalPoints();
		dayRider = rider.isDayRider();
		country = rider.getCountry();
		nationality = rider.getNationality();
		season = rider.getSeason();
		gender = rider.getGender();
		bib = rider.getBib();

		log.debug("xtien existing size = " + timesList.size() + " new size = "
				+ rider.getTimes().size());

		for (Times tRider : rider.getTimes()) {
			log.debug("xtien loop " + tRider.getDate());

			boolean found = false;

			for (Times t : timesList) {
				log.debug("xtien loop " + tRider.getDate() + " " + t.getDate());

				if (t.getDate() == tRider.getDate()) {

					log.debug("xtien found " + t.getDate() + " " + tRider.getDate());

					found = true;

					if (tRider.newerThan(t)) {
						t.merge(tRider);
					}

					break;
				}
			}

			if (!found) {

				log.debug("xtien new " + tRider.getDate());
				tRider.setRider(this);
				timesList.add(tRider);

			}
		}
	}

	private Country getNationality() {
		return nationality;
	}

	private Bib getBib() {
		return bib;
	}

	public Country getCountry() {
		return country;
	}

	@Override
	public String toString() {
		return Integer.toString(riderNumber) + " " + firstName + " " + lastName + getTime1() + " "
				+ getTime2();
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

	public int getSeason() {
		return season;
	}

	public void setCountry(Country country) {
		this.country=country;
	}

	public void setSeason(int season) {
		this.season = season;
	}
}
