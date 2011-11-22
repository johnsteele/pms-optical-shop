/**
 * @author John Steele
 * @file   PatientDB.java
 * @date   07/15/2011
 * 
 * <p>A class to represent the patient database.</p>
 */
package greenwood.database;

import greenwood.orders.model.Order;
import greenwood.patients.model.ContactInfo;
import greenwood.patients.model.EyeCareInfo;
import greenwood.patients.model.Patient;
import greenwood.patients.model.WebInfo;
import greewood.persistence.PersistenceConnection;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * <p>A class to manage the patient database.</p>
 */
public class PatientDB {
	
	/**
	 * The list of patients.
	 */
	private List<Patient> my_patientList;
	
	
	/**
	 * Creates a PatientDB with default values.
	 */
	public PatientDB () {
		my_patientList = new ArrayList<Patient>();
		loadPatients();
	}
	
	
	/**
	 * Returns the list of patients.
	 * 
	 * @return The list of patients.
	 */
	public List<Patient> getPatientsList () {
		return my_patientList;
	}
	
	
	/**
	 * Returns the list of all the patient's orders.
	 * 
	 * @return A list of all the patients orders.
	 */
	public List<Order> getAllPatientsOrders () {
		List<Order> totalOrders = new ArrayList<Order>();
		
		/* Traverse through all the patients. */
		Iterator<Patient> iter = my_patientList.iterator();
		while (iter.hasNext()) {
			Patient p = (Patient) iter.next();
			List<Order> pOrders = p.getOrders();
			
			/* Traverse each patient's orders, and add them to the list. */
			Iterator<Order> pOrdersIter = pOrders.iterator();
			while (pOrdersIter.hasNext()) {
				totalOrders.add((Order)pOrdersIter.next());
			}
		}
		
		return totalOrders;
	}
	
	
	/**
	 * Loads the patient list with the patients from the database.
	 */
	private void loadPatients () {
		Patient p = new Patient("Pam", "Carlson", "1-2-1990", false);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("pam@gmail.com", "pCarlson", "pCarlson90"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 15, "1-2-2011", "1-2-2012", true, "Lazy left eye."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Jim", "Dow", "2-3-1991", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("jim@gmail.com", "jDow", "jDow91"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 15, "1-4-2011", "1-4-2012", true, "Lazy right eye."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Joe", "Roge", "3-5-1992", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("joe@gmail.com", "jRoge", "jRoge92"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 0, "1-4-209", "1-10-2012", true, "Bad temper."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Bill", "Watson", "4-5-1993", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("bill@gmail.com", "bWatson", "bWatson93"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 0, "5-5-2011", "5-5-2012", true, "Better vision in left than right."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Tim", "Pallenty", "5-6-1994", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("tim@gmail.com", "tPallenty", "tPallenty94"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 20, "6-6-2011", "6-6-2012", false, ""));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Nicky", "Writter", "6-7-1995", false);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("nicky@gmail.com", "nWritter", "nWritter95"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 15, "7-7-2011", "7-7-2012", true, "Need translator."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Bill", "Nixon", "9-7-1996", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("bill@gmail.com", "bNixon", "bNixon96"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 10, "8-8-2011", "8-8-2012", false, "Bad english."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Abraham", "Fox", "9-8-1997", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("abraham@gmail.com", "aFox", "aFox97"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 15, "9-9-2011", "9-9-2012", true, "Medical prescription."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Charlie", "Dwite", "10-9-1998", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("charlie@gmail.com", "cDwite", "cDwite98"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 15, "3-10-2011", "3-10-2012", true, "No problems."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Greg", "Newman", "11-10-1999", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("greg@gmail.com", "gNewman", "gNewman99"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 5, "1-22-2011", "1-22-2012", false, "Lazy right eye."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Hillory", "McDaniel", "12-11-1998", false);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("hillory@gmail.com", "hMcDaniel", "hMcDaniel98"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 25, "7-14-2011", "7-14-2012", true, "Medicare patient."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Indingo", "McMiller", "11-13-1997", false);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("indingo@gmail.com", "iMcMiller", "iMcMiller97"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 15, "3-3-2011", "3-3-2012", false, "Translator needed."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Justin", "Wayne", "10-14-1996", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("justin@gmail.com", "jWayne", "jWayne96"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 5, "1-5-2011", "1-5-2012", false, "Lazy left eye."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Krital", "Abe", "9-15-1995", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("krital@gmail.com", "kAbe", "kAbe95"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 0, "1-2-2011", "1-2-2012", true, "Lazy right eye."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Russel", "Broxton", "8-16-1994", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("russel@gmail.com", "rBroxton", "rBroxton94"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 3, "1-2-2011", "1-2-2012", true, "Prescription for right eye."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Patrick", "Bowser", "7-17-1994", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("partrick@gmail.com", "pBowser", "pBowser94"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 30, "4-4-2011", "4-4-2012", false, "Translator needed."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Linda", "Zimmer", "8-16-1993", false);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("linda@gmail.com", "lZimmer", "lZimmer93"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 0, "6-28-2011", "6-28-2012", true, "Bad temper."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Morgan", "Tan", "5-19-1992", false);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("morgan@gmail.com", "mTan", "mTan92"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 50, "4-18-2011", "4-18-2012", true, "Long time patient."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Nionka", "Pitsberg", "4-20-1991", false);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("nionka@gmail.com", "nPitsberg", "nPitsbers91"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 45, "1-2-2011", "1-2-2012", true, "Crazy!"));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
		
		p = new Patient("Oscar", "Dell", "3-21-1990", true);
		p.setOrders(getPatientOrders(p));
		p.setWebInfo(new WebInfo("oscar@gmail.com", "oDell", "oDell90"));
		p.setEyeInfo(new EyeCareInfo("UW Optical", 0, "Mike Sung", 25, "8-2-2011", "8-2-2012", false, "Super cool."));
		p.setContactInfo(new ContactInfo("5488 Heckle Street NE", "Apt. #4", "", "Seattle", "WA", 98105, "(206) 850-5591", "(206) 397-2289", "(425) 894-5632", "NA"));
		my_patientList.add(p);
	}
	
	
	/**
	 * Returns a list of orders. 
	 * 
	 * @param the_patient The patient with this order.
	 * @return A list or orders.
	 */
	private List<Order> getPatientOrders (Patient the_patient) {
		List<Order> orders = new ArrayList<Order>();
		
		Order o = new Order(the_patient, 1, 2, 2011, // new GregorianCalendar(2011, Calendar.DECEMBER, 17).getTime(),
								1, "Okley sunglasses", "Shipped", 5, 2, 2011); //new GregorianCalendar(2011, Calendar.DECEMBER, 20).getTime());
		orders.add(o);
		
		o = new Order(the_patient, 3, 5, 2009, //new GregorianCalendar(2011, Calendar.MAY, 1).getTime(),
								2, "Prescription Reading Glasses", "Canceled", 9, 5, 2009);//new GregorianCalendar(2011, Calendar.MAY, 3).getTime());
		orders.add(o);
		
		o = new Order(the_patient, 16, 5, 2002, //new GregorianCalendar(2010, Calendar.AUGUST, 23).getTime(),
								3, "Driving Glasses", "In Fabrication", 20, 5, 2002);//new GregorianCalendar(2010, Calendar.AUGUST, 24).getTime());
		orders.add(o);
		
		o = new Order(the_patient, 21, 1, 2011, //new GregorianCalendar(2008, Calendar.JULY, 18).getTime(),
								4, "Prescription Lens", "Shipped", 22, 1, 2011);
		orders.add(o);
		
		return orders;
	}
}
