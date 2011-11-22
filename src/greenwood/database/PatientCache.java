package greenwood.database;

import greenwood.patients.model.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cache of the patients.
 * 
 * @author John Steele
 */
public class PatientCache {
	
	/**
	 * The list of cached Patients from the database.
	 */
	private List<Patient> my_patients = new ArrayList<Patient>();
	
	
	/**
	 * Creates a PatientCache with default values.
	 */
	public PatientCache () { }
	
	
	/**
	 * Sets the cached patients to the provided list of patients.
	 * 
	 * @param The patient cache.
	 */
	public void setCachedPatients (List<Patient> the_patients) {
		my_patients = the_patients;
	}

	
	/**
	 * Returns the list of cached patients.
	 * 
	 * @return The list of cached patients.
	 */
	public List<Patient> getCachedPatients () {
		return my_patients;
	}
}
