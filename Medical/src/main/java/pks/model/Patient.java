package pks.model;

import com.opencsv.bean.CsvBindByName;

import java.util.*;

/**
 * The patient model class to store the patient related data (which has links to the patient's episodes and data)
 */
public class Patient {
	/**
	 * The unique patient ID
	 */
	long id;
	/**
	 * Based on the data provided the gender isn't always supplied in each episode (and most likely won't change)
	 * So setting it on the patient level
	 */
	String gender;
	/**
	 * The list of episodes for this patient
	 */
	Map<Date, Episode> episodes = new HashMap<>();

	public Patient(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Map<Date, Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Map<Date, Episode> episodes) {
		this.episodes = episodes;
	}
}
