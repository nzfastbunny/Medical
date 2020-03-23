package pks.service;

import pks.model.PatientData;

import java.util.List;

/**
 * The interface for the patient data service (to analyse the data being processed)
 */
public interface PatientDataService {
    /**
     * Retrieve the number of episodes in the data set
     *
     * @return the number of unique episodes (per customer, per day) in the data set
     */
    long getNumberOfEpisodes();

    /**
     * Retrieve the number of unique patients in the data set
     *
     * @return the number of unique patients in the data set
     */
    long getNumberOfPatients();

    /**
     * Retrieve the number of patients of the supplied gender in the data set
     *
     * @param gender the supplied gender (either "M" for male or "F" for female)
     * @return the number of patients of the supplied gender in the data set
     */
    long getNumberOfPatientsByGender(String gender);

    /**
     * Retrieve the average age of the patients of the supplied gender in the data set
     *
     * @param gender the supplied gender (either "M" for male or "F" for female)
     * @return the average age of the patients of the supplied gender in the data set
     */
    double getAverageAgeByGender(String gender);

    /**
     * Determine and retrieve a list of all the patients with increasing glucose levels
     *
     * @return the printed list of all the patients with increasing glucose levels
     */
    String getAllPatientsWithIncreasingGlucose();

    /**
     * Determine and retrieve the average blood pressure for the range of ages provided
     *
     * @param lowerRange the lower end of the age range
     * @param upperRange the upper end of the age range
     * @return the average blood pressure for the range of ages (or 0.0 if no patients in that range)
     */
    double getAverageBloodPressureByAgeRange(int lowerRange, int upperRange);
}
