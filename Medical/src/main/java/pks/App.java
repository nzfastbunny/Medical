package pks;

import com.opencsv.bean.CsvToBeanBuilder;
import pks.model.PatientData;
import pks.service.PatientDataService;
import pks.service.PatientDataServiceImpl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An application that reads medical data from a CSV file and outputs analysis on that data
 */
public class App {
    /**
     * The representation for a male patient
     */
    public static final String MALE = "M";
    /**
     * The representation of a female patient
     */
    public static final String FEMALE = "F";

    public static void main(String[] args) {
        // Read in patient data from CSV file and organise it
        PatientDataService service = new PatientDataServiceImpl();

        // Print to console the results of the analysis on the data
        System.out.println("Number of patients: " + service.getNumberOfPatients());
        System.out.println("Number of episodes: " + service.getNumberOfEpisodes());

        System.out.println("Number of male patients: " + service.getNumberOfPatientsByGender(MALE));
        System.out.println("Number of female patients: " + service.getNumberOfPatientsByGender(FEMALE));

        System.out.println("Average age of male patients: " + service.getAverageAgeByGender(MALE));
        System.out.println("Average age of female patients: " + service.getAverageAgeByGender(FEMALE));

        // Extra ones - just for the fun of it (and to show it can be easily extended)
        System.out.println("The patients with increasing glucose levels:\n"
                + service.getAllPatientsWithIncreasingGlucose());
        System.out.println("The average blood pressure for patients between 20 and 30 years of age: "
                + service.getAverageBloodPressureByAgeRange(20, 30));
    }
}
