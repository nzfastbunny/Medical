package pks.model;

import com.opencsv.bean.CsvBindByName;

import java.util.Date;

/**
 * The Patient Data bean to store the data set retrieved from the CSV file
 */
public class PatientData {
    @CsvBindByName(required = true)
    private String date;

    @CsvBindByName(required = true)
    private long patientId;

    @CsvBindByName(column = "Attribute Name", required = true)
    private String name;

    @CsvBindByName(column = "Attribute Value", required = true)
    private String value;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
		// The CSV file might contain leading spaces
        this.name = name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
    	// The CSV file might contain leading spaces
        this.value = value.trim();
    }
}
