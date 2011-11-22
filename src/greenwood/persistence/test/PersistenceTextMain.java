package greenwood.persistence.test;

import greenwood.database.PatientDB;
import greenwood.orders.model.Order;
import greenwood.patients.model.Patient;
import greenwood.patients.model.TestPatient;

import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * A driver for testing the database connection and persistence.
 */
public class PersistenceTextMain {

	
	private static final String PERSISTENCE_UNIT_NAME = "optical_shop";
	
	private static EntityManagerFactory my_EM_factory;
	
	
	public static void main (String[] args) {
		
		my_EM_factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = my_EM_factory.createEntityManager();
		
		// Read the existing entries and write them to the console.
		Query q = em.createQuery("Select t from Patient t");
		List<Patient> patientList = q.getResultList();
		
		for (Patient t : patientList) {
			System.out.println("Patient: " + t.toString());
		}
		
		System.out.println("Number of Patients: " + patientList.size());
////		
		// Create a new patient.
		
	 	em.getTransaction().begin();
		//Patient p = new Patient ("John", "Steele", null);
		//em.persist(p);
		
//		Order order = new Order(p, null, 1, "Sun Wear", "Complete", null);
//		em.persist(order);	
//
//		p.addOrder(order);
//		em.persist(p);
		em.getTransaction().commit(); 
//		em.getTransaction().begin();
//		PatientDB db = new PatientDB();
//		List<Patient> patients = db.getPatientsList();
//		for (Patient p : patients) {
//			em.persist(p);
//		}
//		em.getTransaction().commit();
		
		em.close();
		my_EM_factory.close();
	}
}
