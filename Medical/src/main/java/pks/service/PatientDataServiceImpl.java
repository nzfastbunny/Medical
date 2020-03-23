package pks.service;

import com.opencsv.bean.CsvToBeanBuilder;
import pks.App;
import pks.model.Episode;
import pks.model.Patient;
import pks.model.PatientData;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The patient data service to analyse the contents and patterns in the supplied data set
 */
public class PatientDataServiceImpl implements PatientDataService {
    /**
     * The name of the CSV file containing the data set
     */
    public static final String CSV_FILE_NAME = "messages.csv";
    /**
     * The date pattern that matches the dates supplied in the data set
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * The constants for the data attribute names
     */
    public static final String AGE = "Age";
    public static final String GLUCOSE = "Glucose";
    public static final String BLOOD_PRESSURE = "Blood pressure";
    public static final String GENDER = "Gender";

    /**
     * The organised data set sorted into patients-episodes-data and sorted in a map keyed by the unique patient ID
     */
    private Map<Long, Patient> patients = new HashMap<>();

    /**
     * Public constructor (used to load up all the data and organise it, ready to be analysed)
     */
    public PatientDataServiceImpl() {
        // Load up all the patient data from the CSV file
        List<PatientData> data = readData();

        populatePatients(data);
    }

    @Override
    public long getNumberOfEpisodes() {
        long noOfEpisodes = 0;

        for (Patient patient : patients.values()) {
            noOfEpisodes += patient.getEpisodes().size();
        }

        return noOfEpisodes;
    }

    @Override
    public long getNumberOfPatients() {
        return patients.values().size();
    }

    @Override
    public long getNumberOfPatientsByGender(String gender) {
        long noOfSuppliedGender = 0;

        for (Patient patient : patients.values()) {
            // We are assuming that gender doesn't change
            if (patient.getGender().equalsIgnoreCase(gender)) {
                noOfSuppliedGender++;
            }
        }

        return noOfSuppliedGender;
    }

    @Override
    public double getAverageAgeByGender(String gender) {
        long noOfSuppliedGender = 0;
        long totalAge = 0;
        double average = 0.0;

        for (Patient patient : patients.values()) {
            // We are assuming that gender doesn't change
            if (patient.getGender().equalsIgnoreCase(gender)) {
                List<Date> episodeDates = new ArrayList<>(patient.getEpisodes().keySet());
                if (episodeDates.size() > 0) {
                    Collections.sort(episodeDates);
                    // Get the last episode to get the most accurate age
                    Date episodeDate = episodeDates.get(episodeDates.size() - 1);
                    Episode episode = patient.getEpisodes().get(episodeDate);
                    noOfSuppliedGender++;
                    totalAge += Long.parseLong(episode.getEpisodeData().get(AGE));
                }
            }
        }

        // Prevent divide by 0 if no patients of the supplied gender are found
        if (noOfSuppliedGender > 0) {
            average = (double) totalAge / noOfSuppliedGender;
        }

        return average;
    }

    @Override
    public String getAllPatientsWithIncreasingGlucose() {
        StringBuilder warningPatients = new StringBuilder();

        for (Patient patient : patients.values()) {
            Double previousGlucose = null;

            List<Date> episodeDates = new ArrayList<>(patient.getEpisodes().keySet());
            if (episodeDates.size() > 1) { // Only can have increasing if more than 1 episode...
                Collections.sort(episodeDates);

                for (Date episodeDate : episodeDates) {
                    Episode episode = patient.getEpisodes().get(episodeDate);
                    double glucose = Double.parseDouble(episode.getEpisodeData().get(GLUCOSE));

                    if (previousGlucose != null && previousGlucose < glucose) {
                        warningPatients.append(String.format("[Patient ID: %d, Glucose increase: %.1f to %.1f]\n",
                                patient.getId(), previousGlucose, glucose));
                    }

                    previousGlucose = glucose;
                }
            }
        }

        return warningPatients.toString();
    }

    @Override
    public double getAverageBloodPressureByAgeRange(int lowerRange, int upperRange) {
        long noOfEpisodes = 0;
        long totalBloodPressure = 0;
        double average = 0.0;

        for (Patient patient : patients.values()) {
            Collection<Episode> episodes = patient.getEpisodes().values();
            for (Episode episode : episodes) {
                long age = Long.parseLong(episode.getEpisodeData().get(AGE));

                if (age >= lowerRange && age <= upperRange) {
                    noOfEpisodes++;
                    totalBloodPressure += Long.parseLong(episode.getEpisodeData().get(BLOOD_PRESSURE));
                }
            }
        }

        // Prevent divide by 0 if no patients are in the supplied age range
        if (noOfEpisodes > 0) {
            average = (double) totalBloodPressure / noOfEpisodes;
        }

        return average;
    }

    /**
     * Read the data set from the supplied CSV file into the PatientData bean
     *
     * @return the list of PatientData objects containing the rows of the CSV file
     */
    private List<PatientData> readData() {
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream(CSV_FILE_NAME);
        if (inputStream == null) {
            System.err.println("The attempt to read messages.csv failed, so no data could be loaded!");
            return new ArrayList<>();
        }
        return new CsvToBeanBuilder(new InputStreamReader(inputStream))
                .withType(PatientData.class).build().parse();
    }

    /**
     * Populate the patients map with all the data from the CSV
     *
     * @param data the list of data objects retrieved from the CSV
     */
    private void populatePatients(List<PatientData> data) {
        for (PatientData entry : data) {
            long patientId = entry.getPatientId();
            if (!patients.containsKey(patientId)) {
                patients.put(patientId, new Patient(patientId));
            }
            Patient patient = patients.get(patientId);

            populateEpisodesAndData(entry, patientId, patient);
        }
    }

    /**
     * Populate the episodes and data into the existing patient records from the data set provided
     *
     * @param entry     the data entry (a row from the CSV file)
     * @param patientId the unique patient ID
     * @param patient   the patient record that the data entry relates to
     */
    private void populateEpisodesAndData(PatientData entry, long patientId, Patient patient) {
        final String dateStr = entry.getDate();

        try {
            Date date = new SimpleDateFormat(DATE_PATTERN).parse(dateStr);
            if (!patient.getEpisodes().containsKey(date)) {
                patient.getEpisodes().put(date, new Episode(patientId, date));
            }
            Episode episode = patient.getEpisodes().get(date);

            // Populate gender on the patient object (as it shouldn't change and future episodes don't provide it)
            if (entry.getName().equalsIgnoreCase(GENDER)) {
                patient.setGender(entry.getValue());
            } else {
                episode.getEpisodeData().put(entry.getName(), entry.getValue());
            }
        } catch (ParseException e) {
            System.err.println("Error occurred when trying to parse the date: " + dateStr);
        }
    }
}
