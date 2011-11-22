package greenwood.patients.model;

/**
 * Retains all eye care information for a patient.
 * 
 * @author John Steele
 */
public class EyeCareInfo {
	
	private String practice = "";
	private int locationIndex = 0;
	private String doctor = "";
	private int discount = 0;
	
	private String lastExam = "";
	private String nextExam = "";
	private boolean emailNotification = false;
	
	private String notes = "";
	
	public EyeCareInfo () {}
	
	public EyeCareInfo (String practice, int location, String doctor, int discount, String lastE, String nextE, 
							boolean emailNotify, String notes) {
		this.practice = practice;
		this.locationIndex = location;
		this.doctor = doctor;
		this.discount = discount;
		this.lastExam = lastE;
		this.nextExam = nextE;
		this.emailNotification = emailNotify;
		this.notes = notes;
	}

	public String getPractice() {
		return practice;
	}

	public void setPractice(String practice) {
		this.practice = practice;
	}

	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getLastExam() {
		return lastExam;
	}

	public void setLastExam(String lastExam) {
		this.lastExam = lastExam;
	}

	public String getNextExam() {
		return nextExam;
	}

	public void setNextExam(String nextExam) {
		this.nextExam = nextExam;
	}

	public boolean isEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(boolean emailNotification) {
		this.emailNotification = emailNotification;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
