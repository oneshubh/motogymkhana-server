package eu.motogymkhana.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.persistence.annotations.Cache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "times")
public class Times {

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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private volatile int _id;

	@JsonProperty(TIMESTAMP)
	@Column(name = TIMESTAMP)
	private long timeStamp;

	@JsonIgnore
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
	private int points = 0;

	@Transient
	private volatile int bestTimePlusPenalties;

	public Times() {
	}

	public Times(long date) {
		this.date = date;
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
		return timeStamp >= existingTimes.getTimeStamp();
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
	}

	public void setDate(long date) {
		this.date = date;
	}

	@Override
	public boolean equals(Object other) {

		if (!(other instanceof Times)) {
			return false;
		}

		Times otherTimes = (Times) other;

		return otherTimes.getDate() == date && rider != null && otherTimes.getRider() != null
				&& otherTimes.getRider().getRiderNumber() == rider.getRiderNumber();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(date).toHashCode();
	}
}
