/**
 * @author John Steele
 * @file   Patient.java
 * @date   07/15/2011
 * 
 * <p>A model object to represent a patient.</p>
 */
package greenwood.patients.model;

import greenwood.orders.model.Order;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * <p>A class to represent a patient.</p>
 */
public class Patient implements PropertyChangeListener, Serializable {
	
	/** 
	 * Patients' first name.
	 */
	private String my_firstName;
	
	/**
	 * Patients' last name.
	 */
	private String my_lastName;
	
	/**
	 * DOB day, month, year.
	 */
	private String dob;
	
	/**
	 * The list of orders.
	 */
	private List<Order> my_orders;
	
	private WebInfo my_webInfo;
	private EyeCareInfo my_eyeInfo;
	private ContactInfo my_contactInfo;
	
	private boolean isMale = false;
	
	/**
	 * The property change support for this patient.
	 */
	private PropertyChangeSupport my_propertyChangeSupport;
	
	
	/**
	 * Creates a Patient object with default values.
	 */
	public Patient () {
		my_propertyChangeSupport = new PropertyChangeSupport(this);
		my_orders    = new ArrayList<Order>();
		my_firstName = "";
		my_lastName  = "";	
		my_webInfo   = new WebInfo ();
		my_eyeInfo   = new EyeCareInfo();
		my_contactInfo = new ContactInfo();
	}
	
	
	/**
	 * Creates a Patient object with the specified values. 
	 * 
	 * @param the_first The first name.
	 * @param the_last The last name.
	 * @param the_dob The date of birth.
	 */
	public Patient (String the_first, String the_last, String the_dob, boolean isMale) {
		my_propertyChangeSupport = new PropertyChangeSupport(this);
		my_orders                = new ArrayList<Order>();
		setFirstName(the_first);
		setLastName(the_last);
		dob = the_dob;
		this.isMale = isMale;
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
			my_propertyChangeSupport.firePropertyChange("my_orders", null, my_orders);
	}
	
	
	/**
	 * Adds the listener to the list of listeners that will be notified when the property 
	 * is changed. 
	 * 
	 * @param property The property to listen for changes in. 
	 * @param listener The listener to be notified when changes where made to the property.
	 */
	public void addPropertyChangeListener (String property, PropertyChangeListener listener) {
		my_propertyChangeSupport.addPropertyChangeListener(property, listener);
	}
	
	
	/**
	 * Removes the listener from the list of listeners to be notified when changes are made
	 * to the property.
	 * 
	 * @param property The property which changes are being listened to.
	 * @param listener The listener to be removed.
	 */
	public void removePropertyChangeListner (String property, PropertyChangeListener listener) {
		my_propertyChangeSupport.removePropertyChangeListener(property, listener);
	}
	
	
	/**
	 * Adds the order to this patient's order list.
	 * 
	 * @param the_order The order to add to the patients order list.
	 */
	public void addOrder (Order the_order) {
//		the_order.addPropertyChangeListener("my_orderDay",   this);
//		the_order.addPropertyChangeListener("my_patient", 	 this);
		my_propertyChangeSupport.firePropertyChange("my_orders", this.my_orders, my_orders.add(the_order));
	}
	
	
	/**
	 * Returns the list of orders from this patient.
	 * 
	 * @return The list of this patient's orders.
	 */
	public List<Order> getOrders () {
		return my_orders;
	}
	
	/**
	 * Sets the orders.
	 */
	public void setOrders (List<Order> the_orders) {
		this.my_orders = the_orders;
	}
	
	
	/**
	 * Returns the first name;
	 * 
	 * @return The first name.
	 */
	public String getFirstName () {
		return my_firstName;
	}
	
	
	/**
	 * Returns the last name.
	 * 
	 * @return The last name.
	 */
	public String getLastName () {
		return my_lastName;
	}


	/**
	 * Sets the first name.
	 * 
	 * @param firstName The first name.
	 */
	public void setFirstName(String firstName) {
		my_propertyChangeSupport.firePropertyChange("my_firstName", this.my_firstName, 
				my_firstName = firstName);
	}


	/**
	 * Sets the last name.
	 * 
	 * @param lastName The last name.
	 */
	public void setLastName(String lastName) {
		my_propertyChangeSupport.firePropertyChange("my_lastName", this.my_lastName, 
				my_lastName = lastName);
	}
	
	
	/**
	 * Sets the date of birth.
	 */
	public void setDOB (String the_dob) {
		
		my_propertyChangeSupport.firePropertyChange("dob",   this.dob, dob = the_dob);
	}
	
	/**
	 * Returns the date of birth.
	 * 
	 * @return This patient's date of birth.
	 */
	public String getDOB () {
		return dob;
	}
	
	
	/**
	 * Compares patients by there first and last name.
	 * 
	 * @return True if first and last are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof Patient) {
			Patient p = (Patient) other;
			return my_firstName.equalsIgnoreCase(p.getFirstName()) &&
			       	my_lastName.equalsIgnoreCase(p.getLastName());
		}
		return false;
	}
	
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[");
		buff.append(getLastName());
		buff.append(", ");
		buff.append(getFirstName());
		buff.append("] ");
		if (my_orders != null) {
			buff.append("Orders: ");
			for (Order o : my_orders) {
				buff.append(o.toString());
				buff.append(" , ");
			}
		}
		return buff.toString();
	}
	
	public void setGender (boolean isMale) {
		this.isMale = isMale;
	}
	
	public boolean getGender () {
		return isMale;
	}
	
	/** Web information. */
	public void setWebInfo (WebInfo info) {
		my_webInfo = info;
	}
	public WebInfo getWebInfo () {
		return my_webInfo;
	}
	
	/** Eye care information. */
	public void setEyeInfo (EyeCareInfo info) {
		my_eyeInfo = info;
	}
	public EyeCareInfo getEyeInfo () {
		return my_eyeInfo;
	}
	
	/** Contact information. */
	public void setContactInfo (ContactInfo info) {
		my_contactInfo = info;
	}
	public ContactInfo getContactInfo () {
		return my_contactInfo;
	}	
}
