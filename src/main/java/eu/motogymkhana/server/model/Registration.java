package eu.motogymkhana.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "registrations")
public class Registration {

	public static final String ID = "_id";
	public static final String SEASON = "season";
	public static final String COUNTRY = "country";
	public static final String RIDER = "rider";
	public static final String NUMBER = "number";
	private static final String REGISTERED = "registered";
	private static final String BIB = "bib";
	private static final String DAY_RIDER = "day_rider";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private int _id;

	@JsonProperty(SEASON)
	@Column(name = SEASON)
	protected int season;

	@JsonProperty(COUNTRY)
	@Column(name = COUNTRY)
	protected Country country;

	@JsonProperty(NUMBER)
	@Column(name = NUMBER)
	protected int number;

	@JsonProperty(BIB)
	@Column(name = BIB)
	protected Bib bib;

	@JsonProperty(DAY_RIDER)
	@Column(name = DAY_RIDER)
	protected boolean dayRider;

	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	protected Rider rider;

	@JsonProperty(REGISTERED)
	@Column(name = REGISTERED)
	protected boolean registered;

	public Registration() {

	}

	public Registration(Rider rider, Country country, int season, int startNumber, Bib bib) {
		this.rider = rider;
		this.country = country;
		this.season = season;
		this.number = startNumber;
		this.bib = bib;
	}

	public Country getCountry() {
		return country;
	}

	public int getSeason() {
		return season;
	}

	public boolean isRegistered() {
		return registered;
	}

	public Rider getRider() {
		return rider;
	}

	public String toString() {
		return _id + " " + country.name() + " " + season + " " + number + " rider id "
				+ (rider != null ? Integer.toString(rider.get_id()) : "null");
	}

	public void setRider(Rider rider) {
		this.rider = rider;
	}

	public int get_id() {
		return _id;
	}

	public void setDayRider(boolean dayRider) {
		this.dayRider = dayRider;
	}

	public boolean isDayRider() {
		return dayRider;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public void set_id(int id) {
		this._id = id;
	}

	public Bib getBib() {
		return bib;
	}

	public void setBib(Bib bib) {
		this.bib = bib;
	}

	public void setRiderNumber(int riderNumber) {
		this.number = riderNumber;
	}

	public int getNumber() {
		return number;
	}
}
