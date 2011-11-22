/**
 * @author John Steele
 * @version 06/20/2011
 * @file Workbench.java
 * 
 * <p>
 * A class to represent the applications' workbench.
 * </p>
 */
package greenwood.application;

import java.util.List;

import greenwood.database.DatabaseManager;
import greenwood.orders.model.Order;
import greenwood.patients.model.Patient;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * The workbench class represents the top of the applications user interface. 
 * Its primary responsibility is the management of the workbench window, 
 * dialogs, wizards, and other workbench-related windows.
 * 
 * The workbench also manages the database.
 * 
 * <p>
 * Note that this class is a singleton, classes can access the workbench
 * by calling its accessor method. 
 * </p>
 */
public final class Workbench {
	
	/**
	 * The file to save and restore workbench information. 
	 */
	public static final String WORKBENCH_STATE_FILENAME = "workbench.xml";
	
	/**
	 * Holds onto the only instance of the workbench. 
	 */
	private static Workbench instance;
	
	/**
	 * The shared images for the workbench.
	 */
	private WorkbenchImages my_workbench_images;
	
	/**
	 * The display used for all UI interactions with this workbench. 
	 */
	private Display my_display;
	
	/**
	 * The top-most shell. 
	 */
	private Shell my_shell;
	
	/**
	 * The workbench window supplying the trim widgets. 
	 */
	private WorkbenchWindow my_workbench_window;
	
	/**
	 * The database manager. It exposes API to the database.
	 */
	private DatabaseManager my_DB_manager;
	
	/**
	 * Creates a Workbench object with the specified values. 
	 * 
	 * @param the_display The workbench display.
	 * @param the_shell The top-most shell.
	 */
	public Workbench (Display the_display, Shell the_shell) {
		this.my_display     = the_display;
		this.my_shell       = the_shell;
		Workbench.instance  = this;
		my_workbench_window = new WorkbenchWindow(the_shell);
		my_DB_manager 	    = new DatabaseManager();
		init ();
	}
	
	
	/**
	 * Initializes top-most window elements. 
	 */
	private void init () {
		my_shell.setText(MainApplication.APPLICATION_TITLE);
		my_shell.setLayout(new GridLayout(1, false));		
		setInitialPosition();		
		init_database();
	}
	
	
	/**
	 * Initializes the database.
	 */
	private void init_database () {
		
		List<Patient> p = my_DB_manager.getPatients();
		List<Order> o = my_DB_manager.getOrders();
		
		for (Patient ps : p) {
			System.out.println(ps.toString());	
		}
		
		System.out.println("----------------------------------------------");
		
		for (Order ods : o) {
			System.out.println(ods.toString());
		}
		
	}
			
			
			
	/**
	 * Sets the initial position (size and location) of the workbench.
	 */
	private void setInitialPosition () {
		
		// First set the location.
		Monitor primaryMonitor = my_display.getPrimaryMonitor();
		Rectangle bounds = primaryMonitor.getBounds();
		
		Rectangle rec = my_shell.getBounds();
		
		// The x and y coordinates
		int x = bounds.x + Math.max(0, (bounds.width - rec.width) / 2);
		int y = bounds.y + Math.max(0, (bounds.height - rec.height) / 2);
		
		// Set the x and y coordinates, and the width and height.
		my_shell.setBounds(x, y, rec.width, rec.height);
	}
	
	
	/**
	 * Opens and displays the top-most window.
	 */
	public void open () {		
		my_workbench_window.open();
	}
	
	
	/**
	 * Returns the workbench display.
	 * 
	 * @return The workbench display.
	 */
	public Display getDisplay () {
		return my_display;
	}

	
	/**
	 * Returns the top-most shell.
	 *  
	 * @return The top-most shell of this workbench.
	 */
	public Shell getTopShell () {
		return my_shell;
	}
	
	
	/**
	 * Returns the single workbench instance. 
	 * 
	 * @return The 
	 */
	public static final Workbench getInstance () {
		return instance;
	}
	
	
	/**
	 * Returns the shared workbench images. 
	 * 
	 * @return The shared workbench images. 
	 */
	public final WorkbenchImages getWorkbenchImages () {
		if (my_workbench_images == null) 
			my_workbench_images = new WorkbenchImages();
		
		return my_workbench_images;
	}	
	
	
	/**
	 * Override the clone method so clients can't make a copy of the
	 * workbench. If they do try, this exception will be thrown.
	 * 
	 * @throws CloneNotSupportedException The workbench is a singleton.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	
	/**
	 * Returns the database manager.
	 * 
	 * @return The database manager.
	 */
	public DatabaseManager getDatabase () {
		return my_DB_manager;
	}
}
