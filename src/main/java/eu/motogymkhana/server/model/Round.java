package eu.motogymkhana.server.model;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

	private static final String ID = "id";
	public static final String DATE = "date";
	public static final String NUMBER = "number";
	private static final String TIMESTAMP = "timestamp";
	private static final String CURRENT = "current";
	private static final String COUNTRY = "country";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private int _id;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Collection<Times> timesList = new LinkedList<Times>();

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

    @JsonProperty(COUNTRY)
    @Column(name = COUNTRY)
    private Country country;

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
}
