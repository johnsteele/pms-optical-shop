/**
 * @author John Steele
 * @file   OrderEditor.java
 * @date   07/18/2011
 * 
 * <p>A class to represent an Order editor.</p>
 */
package greenwood.editors;

import java.beans.PropertyChangeEvent;

import greenwood.application.WorkbenchImages;
import greenwood.application.WorkbenchPage;
import greenwood.orders.model.Order;
import greenwood.patients.model.Patient;
import greenwood.providers.PatientOrdersLabelProvider;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.sun.corba.se.spi.ior.Writeable;

/**
 * <p>A class to represent an OrderEditor.</p>
 */
public class OrderEditor extends EditorPart {
	
	/**
	 * The tab tool-tip.
	 */
	private static final String TAB_NAME = "Orders";
	private static final String TAB_TIP  = "Edit orders";
	
	/**
	 * The UI form.
	 */
	private Form my_form;
	
	/**
	 * The patient being displayed in this editor.
	 */
	private Patient my_patient;
	
	/**
	 * The list displaying the orders in the table. 
	 * The writable list works like a wrapper around the 
	 * actual patient's list of orders.
	 */
	private WritableList my_orders;

	
	/**
	 * Creates an OrderEditor with the specified values.
	 * 
	 * @param the_page The page owning this view part.
	 * @param the_folder The folder owning this veiw's tab item.
	 * @param the_toolkit The shared form toolkit factory.
	 * @param the_patient The patient to load in the editor. Can be null if non.
	 */
	public OrderEditor(WorkbenchPage the_page, CTabFolder the_folder,
			FormToolkit the_toolkit, Patient the_patient) {
		
		super(the_page, the_folder, the_toolkit);
		getTabItem().setToolTipText(TAB_TIP);
		getTabItem().setText(TAB_NAME);
		getTabItem().setShowClose(true);
		my_isDirtyPatient = false;
		my_patient = the_patient;
	}
	
	
	/**
	 * Creates the UI for the editor's control. 
	 */
	@Override
	public void createPartControl() {
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		super.getComposite().setLayout(layout);
		super.getComposite().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		my_form = getFormToolkit().createForm(getComposite());
		my_form.setLayout(new GridLayout(2, true));
		my_form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		my_form.setBackgroundImage(WorkbenchImages.getImageRegistry().get("banner"));
		
		layout = new GridLayout(1, true);
		my_form.getBody().setLayout(layout);
		
		createOrderTable ();
		setFormHeader();
	}
	
	
	/**
	 * Creates the order table.
	 */
	private void createOrderTable () {
		
		/* Table */
		Table table = getFormToolkit().createTable(my_form.getBody(), SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE);
		TableViewer viewer = new TableViewer (table);
		createColumns (table, viewer);
		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 20;
		table.setLayoutData(data);
		
		/* Table viewer */
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new PatientOrdersLabelProvider());
		my_orders = new WritableList(my_patient.getOrders(), Order.class);
		viewer.setInput(my_orders);
		
	
	}
	
	
	/**
	 * Creates the columns in the order table.
	 * 
	 * @param the_table The table to create columns for.
	 * @param the_viewer The viewer to the table.
	 */
	private void createColumns (Table the_table, TableViewer the_viewer) {
		
		String [] labels = {"Job Number", "Order Date", "Description", "Status", "Status Date/Time"};
		int [] bounds = {150, 150, 300, 200, 100};
		
		for (int i = 0; i < bounds.length; i++) {
			TableViewerColumn col = createViewerColumn (the_viewer, labels[i], bounds[i]);
			col.setLabelProvider(new PatientOrdersLabelProvider());
		}
	}
	
	
	/**
	 * Creates and returns a TableViewerColumn for the provided
	 * table using the specified weight and label.
	 * 
	 * @param the_viewer The viewer to add the column to.
	 * @param the_title The title of the column.
	 * @param the_bounds The size of the column (width).
	 */
	private TableViewerColumn createViewerColumn (TableViewer the_viewer, String the_title, int the_bounds) {
		TableViewerColumn col = new TableViewerColumn(the_viewer, SWT.NONE);
		TableColumn tCol = col.getColumn();
		
		tCol.setResizable(true);
		tCol.setWidth(the_bounds);
		tCol.setText(the_title);
		tCol.setMoveable(true);
		return col;
	}
	
	
	/**
	 * Sets the header text on the form.
	 */
	protected void setFormHeader () {
		int lastLength = my_patient.getLastName().length();
		int firstLength = my_patient.getFirstName().length();
		String lastName = my_patient.getLastName();
		String firstName = my_patient.getFirstName();
		
		if (lastLength > 0 && firstLength > 0) {
			my_form.setText(lastName + ", " + firstName);
			getTabItem().setText(lastName + ", " + firstName + " - Orders");
			getTabItem().setToolTipText(lastName + ", " + firstName + " - Orders");
		}
		
		else if (lastLength > 0 && firstLength == 0) {
			my_form.setText(lastName);
			getTabItem().setText(lastName + " - Orders");
			getTabItem().setToolTipText(lastName + " - Orders");
		}
		
		else if (firstLength > 0 && lastLength == 0) {
			my_form.setText(firstName);
			getTabItem().setText(firstName);
			getTabItem().setToolTipText(firstName + " - Orders");
		}
		
		else {
			my_form.setText("<unknown>");
			getTabItem().setText("<unkown> - Orders");
			getTabItem().setToolTipText("<unknown> - Orders");
		}
	}
	
	
	/**
	 * Returns the patient who's orders are displayed in this editor.
	 * 
	 * @return The patient displayed in this editor.
	 */
	public Patient getPatient () {
		return my_patient;
	}
	

	
	/**
	 * Gives this editor focus.
	 */
	@Override
	public void setFocus() {
	}
	

	/**
	 * Releases resources, unregisters listeners, saves info. 
	 */
	@Override
	public void dispose() {
	}


	/**
	 * Property change callback.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
