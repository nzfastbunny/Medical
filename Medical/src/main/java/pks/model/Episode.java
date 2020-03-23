package pks.model;

import com.opencsv.bean.CsvBindByName;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The episode model class containing the patient's episodes and related data
 */
public class Episode {
	/**
	 * The patient ID for this episode
	 */
	long patientId;
	/**
	 * The date that the episode occurred (the test date) for this patient
	 */
	Date episodeDate;
	/**
	 * The map of episode data values keyed by the attribute name
	 */
	Map<String, String> episodeData = new HashMap<>();

	public Episode(long patientId, Date episodeDate) {
		this.patientId = patientId;
		this.episodeDate = episodeDate;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public Date getEpisodeDate() {
		return episodeDate;
	}

	public void setEpisodeDate(Date episodeDate) {
		this.episodeDate = episodeDate;
	}

	public Map<String, String> getEpisodeData() {
		return episodeData;
	}

	public void setEpisodeData(Map<String, String> episodeData) {
		this.episodeData = episodeData;
	}
}
