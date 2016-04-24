package eu.motogymkhana.server.settings;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.motogymkhana.server.model.Country;

/**
 * Created by christine on 15-2-16.
 */
@Entity
@Table(name = "settings")
public class Settings {

	public static final String ID = "_id";
	public static final String NUMBER_OF_ROUNDS_COUNT_FOR_TITLE = "number_of_rounds_that_count_for_title";
	public static final String PERCENTAGE_FOR_GREEN_BIB = "percentage_for_green_bib";
	public static final String PERCENTAGE_FOR_BLUE_BIB = "percentage_for_blue_bib";
	public static final String NUMBER_OF_RESULTS_FOR_SEASON_RESULT = "number_of_results_for_season_result";
	public static final String NUMBER_OF_RESULTS_FOR_BIB = "number_of_results_for_bib";
	public static final String COUNTRY = "country";
	public static final String SEASON = "season";
	public static final String POINTS = "points";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private int _id;

	@JsonProperty(COUNTRY)
	@Column(name = COUNTRY)
	private Country country;

	@JsonProperty(SEASON)
	@Column(name = SEASON)
	private int season;

	@JsonProperty(PERCENTAGE_FOR_GREEN_BIB)
	@Column(name = PERCENTAGE_FOR_GREEN_BIB)
	private int percentageForGreenBib;

	@JsonProperty(PERCENTAGE_FOR_BLUE_BIB)
	@Column(name = PERCENTAGE_FOR_BLUE_BIB)
	private int percentageForBlueBib;

	@JsonProperty(NUMBER_OF_RESULTS_FOR_SEASON_RESULT)
	@Column(name = NUMBER_OF_RESULTS_FOR_SEASON_RESULT)
	private int numberOfResultsForSeasonResult = 6;

	@JsonProperty(NUMBER_OF_RESULTS_FOR_BIB)
	@Column(name = NUMBER_OF_RESULTS_FOR_BIB)
	private int numberOfResultsForBib = 4;

	@JsonProperty(POINTS)
	@Column(name = POINTS)
	private String points;

	@JsonIgnore
	@Transient
	private List<Integer> pointsList;

	public Settings() {

	}

	public Settings(Country nl, int season, int resultsForBib, int numberSeasonResults,
			int percBlueBib, int percGreenBib) {
		this.country = nl;
		this.season = season;
		this.numberOfResultsForBib = resultsForBib;
		this.numberOfResultsForSeasonResult = numberSeasonResults;
		this.percentageForBlueBib = percBlueBib;
		this.percentageForGreenBib = percGreenBib;
	}

	public int getPercentageBlue() {
		return percentageForBlueBib;
	}

	public int getPercentageGreen() {
		return percentageForGreenBib;
	}

	public int getNumberOfResultsForSeasonResult() {
		return numberOfResultsForSeasonResult;
	}

	public int getRoundsForBib() {
		return numberOfResultsForBib;
	}

	public void setPercentageBlue(int s) {
		percentageForBlueBib = s;
	}

	public void setPercentageGreen(int s) {
		percentageForGreenBib = s;
	}

	public void setNumberOfRoundsForBib(int i) {
		numberOfResultsForBib = i;
	}

	public void setNumberOfRoundsForSeasonResult(int i) {
		numberOfResultsForSeasonResult = i;
	}

	public Country getCountry() {
		return country;
	}

	public int getSeason() {
		return season;
	}

	public void merge(Settings settings) {
		this.percentageForBlueBib = settings.getPercentageBlue();
		this.percentageForGreenBib = settings.getPercentageGreen();
		this.numberOfResultsForBib = settings.getRoundsForBib();
		this.numberOfResultsForSeasonResult = settings.getNumberOfResultsForSeasonResult();
		this.points = settings.getPoints();
	}

	public String getPoints() {
		return points;
	}
}
