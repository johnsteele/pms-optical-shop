package greenwood.patients.model;

/**
 * Retains a patient's contact information.
 * 
 * @author John Steele
 */
public class ContactInfo {

	private String address1 = "";
	private String address2 = "";
	private String address3 = "";
	
	private String city = "";
	private String state = "";
	private int zip = 0;
	
	private String home_phone = "";
	private String cell_phone = "";
	private String work_phone = "";
	private String fax = "";
	
	public ContactInfo () {}
	
	public ContactInfo (String addr1, String addr2, String addr3, String city, String state, int zip, 
							String h_phone, String c_phone, String w_phone, String fax) {
		this.address1 = addr1;
		this.address2 = addr2;
		this.address3 = addr3;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.home_phone = h_phone;
		this.cell_phone = c_phone;
		this.work_phone = w_phone;
		this.fax = fax;
	}
	
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public String getHome_phone() {
		return home_phone;
	}
	public void setHome_phone(String home_phone) {
		this.home_phone = home_phone;
	}
	public String getCell_phone() {
		return cell_phone;
	}
	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}
	public String getWork_phone() {
		return work_phone;
	}
	public void setWork_phone(String work_phone) {
		this.work_phone = work_phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
}
