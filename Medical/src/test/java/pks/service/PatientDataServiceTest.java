package pks.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PatientDataServiceTest {
    PatientDataService service;

    @Before
    public void setUp() {
        service = new PatientDataServiceImpl();
    }

    @Test
    public void testFileLoaded() {
        Assert.assertTrue(service.getNumberOfPatients() > 0);
    }

    @Test
    public void testNoOfPatients() {
        Assert.assertTrue(service.getNumberOfPatients() == 3);
    }

    @Test
    public void testNoOfEpisodes() {
        Assert.assertTrue(service.getNumberOfEpisodes() == 8);
    }

    @Test
    public void testNoOfMalePatients_nullSupplied() {
        Assert.assertTrue(service.getNumberOfPatientsByGender(null) == 0);
    }

    @Test
    public void testNoOfMalePatients() {
        Assert.assertTrue(service.getNumberOfPatientsByGender("M") == 1);
    }

    @Test
    public void testNoOfFemalePatients_invalidOption() {
        Assert.assertTrue(service.getNumberOfPatientsByGender("N") == 0);
    }

    @Test
    public void testNoOfFemalePatients() {
        Assert.assertTrue(service.getNumberOfPatientsByGender("F") == 2);
    }

    @Test
    public void testAveAgeOfMalePatients_nullSupplied() {
        Assert.assertTrue(service.getAverageAgeByGender(null) == 0.0);
    }

    @Test
    public void testAveAgeOfMalePatients() {
        Assert.assertTrue(service.getAverageAgeByGender("M") == 24);
    }

    @Test
    public void testAveAgeOfFemalePatients_invalidOption() {
        Assert.assertTrue(service.getAverageAgeByGender("K") == 0.0);
    }

    @Test
    public void testAveAgeOfFemalePatients() {
        Assert.assertTrue(service.getAverageAgeByGender("F") == 24.5);
    }

    @Test
    public void testAllPatientsWithIncreasingGlucose() {
        Assert.assertEquals("[Patient ID: 1, Glucose increase: 11.1 to 12.2]\n" +
                        "[Patient ID: 1, Glucose increase: 11.7 to 12.8]\n" +
                        "[Patient ID: 2, Glucose increase: 5.5 to 5.8]\n" +
                        "[Patient ID: 3, Glucose increase: 9.7 to 9.9]\n",
                service.getAllPatientsWithIncreasingGlucose());
    }

    @Test
    public void testAverageBloodPressureByAgeRange() {
        Assert.assertTrue(service.getAverageBloodPressureByAgeRange(20, 30) == 185.375);
    }

    @Test
    public void testAverageBloodPressureByAgeRange_lowRange() {
        Assert.assertTrue(service.getAverageBloodPressureByAgeRange(0, 20) == 0.0);
    }

    @Test
    public void testAverageBloodPressureByAgeRange_upperRange() {
        Assert.assertTrue(service.getAverageBloodPressureByAgeRange(30, 70) == 0.0);
    }

    @Test
    public void testAverageBloodPressureByAgeRange_upper20s() {
        Assert.assertTrue(service.getAverageBloodPressureByAgeRange(25, 30) == 214.0);
    }

    @Test
    public void testAverageBloodPressureByAgeRange_lower20s() {
        Assert.assertEquals("175.833", String.format("%.3f",
                service.getAverageBloodPressureByAgeRange(20, 25)));
    }
}