/**
 * @athor John Steele
 * @file  Order.java
 * @date  07/17/2011
 * 
 * <p>A class to represent a prescription order.<p>
 */
package greenwood.orders.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import greenwood.patients.model.Patient;

/**
 * <p>A class to represent a prescription order.</p>
 */
public class Order implements PropertyChangeListener, Serializable {
	
	/**
	 * Generated ID for this entity.
	 */
	private Long id;
	
	/**
	 * The patient owning this order.
	 */
	private Patient my_patient;
	
	/**
	 * Job number.
	 */
	private int my_jobNumber = 0;
	
	/**
	 * Order description.
	 */
	private String my_description;
	
	/**
	 * Order status (e.g., shipped, canceled, in fabrication, in transit). 
	 */
	private String my_status;
	
	/**
	 * Date created.
	 */
	private int my_orderDay = 0;
	private int my_orderMonth = 0;
	private int my_orderYear = 0;
	
	private int my_statusDay = 0;
	private int my_statusMonth = 0;
	private int my_statusYear = 0;
	
	
	/**
	 * The order details.
	 */
	private OrderDetails my_details;
	
	/**
	 * The property change support for this order.
	 */
	@Transient
	private PropertyChangeSupport my_propertyChangeSupport = new PropertyChangeSupport(this);
	
	
	/**
	 * Creates a Order object with default values. 
	 */
	public Order () {
		my_description = "";
		my_status = "";
		my_patient = null;
	}
	
	
	/**
	 * Creates a Order object with the specified patient.
	 * 
	 * @param the_patient The patient with this order.
	 * @param the_date The order date.
	 * @param the_jobNum The job number.
	 * @param the_desc The order description.
	 * @param the_status The order status.
	 * @param the_statusDate The date of the recent status update.
	 */
	public Order (Patient the_patient, int orderDay, int orderMonth, int orderYear, 
					int the_jobNum, String the_desc, String the_status, 
					int statusDay, int statusMonth, int statusYear) {
		my_jobNumber   = the_jobNum;
		my_description = the_desc;
		my_status      = the_status;
		setPatient(the_patient);
		setDateCreated(orderDay, orderMonth, orderYear);
		setStatusDate(statusDay, statusMonth, statusYear);
		my_details = new OrderDetails();
	}
	
	
	/**
	 * Adds the listener to a list of listeners that will be notified when the property
	 * is changed.
	 * 
	 * @param property The property being listened to.
	 * @param listener The lister to be notified when changes occur on the property.
	 */
	public void addPropertyChangeListener (String property, PropertyChangeListener listener) {
		my_propertyChangeSupport.addPropertyChangeListener(property, listener);
	}
	
	
	/**
	 * Removes the listener from the list of listeners listening to changes on the property.
	 * 
	 * @param property The property being listened to.
	 * @param listner The listener listing to be removed.
	 */
	public void removePropertyChangeListner (String property, PropertyChangeListener listner) {
		my_propertyChangeSupport.removePropertyChangeListener(property, listner);
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
	}
	
	
	/**
	 * Sets this order's patient to the provided patient.
	 * 
	 * @param the_patient The patient owning this order.
	 */
	public void setPatient (Patient the_patient) {
		if (the_patient != null) {
			my_propertyChangeSupport.firePropertyChange("my_patient", null, the_patient);
			my_patient = the_patient;
		}
	}
	
	
	/**
	 * Sets the creation date for this order.
	 * 
	 * @param the_date The date this order was created.
	 */
	public void setDateCreated (int day, int month, int year) {
			setDayCreated(day);
			setMonthCreated(month);
			setYearCreated(year);
	}
	
	
	/**
	 * Sets the day this order was created.
	 * 
	 * @param The day the order was created.
	 */
	public void setDayCreated (int the_day) {
		my_propertyChangeSupport.firePropertyChange("my_orderDay", my_orderDay, 
				this.my_orderDay = the_day);
	}
	
	
	/**
	 * Sets the month this order was created.
	 * 
	 * @param The month the order was created.
	 */
	public void setMonthCreated (int the_month) {
		my_propertyChangeSupport.firePropertyChange("my_orderMonth", my_orderMonth, 
				this.my_orderMonth = the_month);
	}
	
	
	/**
	 * Sets the year this order was created.
	 * 
	 * @param The year the order was created.
	 */
	public void setYearCreated (int the_year) {
		my_propertyChangeSupport.firePropertyChange("my_orderYear", this.my_orderYear, 
				this.my_orderYear = the_year);
	}
	
	
	/**
	 * Returns the order details.
	 * 
	 * @return The order details.
	 */
	public OrderDetails getOrderDetails () {
		return my_details;
	}
	
	
	/**
	 * Sets the order details.
	 * 
	 * @param The order details.
	 */
	public void setOrderDetails (OrderDetails the_details) {
		my_details = the_details;
	}
	
	
	/**
	 * Returns the patient owning this order.
	 * 
	 * @return The patient owning this order.
	 */
	public Patient getPatient () {
		return my_patient;
	}
	
	
	/**
	 * Returns a Date object for order date.
	 * 
	 * @return The order date object.
	 */
	public int [] getOrderDate () {
		return new int [] {my_orderDay, my_orderMonth, my_orderYear};
	}
	
	
	/**
	 * Returns the job number.
	 * 
	 * @return the job number.
	 */
	public int getJobNumber () {
		return my_jobNumber;
	}
	
	
	/**
	 * Sets the job number.
	 * 
	 * @param the_num The job number.
	 */
	public void setJobNumber (int the_num) {
		my_jobNumber = the_num;
	}
	
	
	/**
	 * Return the order description.
	 * 
	 * @return The order description.
	 */
	public String getDescription () {
		return my_description;
	}
	
	
	/**
	 * Sets the description of this order.
	 * 
	 * @param the_desc The order description.
	 */
	public void setDescription (String the_desc) {
		my_description = the_desc;
	}
	
	
	/**
	 * Returns the order status.
	 * 
	 * @return The order status.
	 */
	public String getStatus () {
		return my_status;
	}
	
	
	/**
	 * Sets the order status.
	 * 
	 * @param the_status The order status.
	 */
	public void setStatus (String the_status) {
		my_status = the_status;
	}
	
	
	/**
	 * Returns the status date, the date of the most
	 * recent status change.
	 * 
	 * @return The status date. 
	 */
	public int [] getStatusDate () {
		return new int [] {my_statusDay, my_statusMonth, my_statusYear};
	}
	
	
	/**
	 * Sets the status date. The date the status on this
	 * order was updated.
	 * 
	 * @param the_date The status date.
	 */
	public void setStatusDate (int day, int month, int year) {
		my_statusDay   = day;
		my_statusMonth = month;
		my_statusYear  = year;
	}
	
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("[ ");
		s.append(my_description);
		s.append(", ");
		s.append(my_status);
		s.append(" ]");
		return s.toString();
	}
}
