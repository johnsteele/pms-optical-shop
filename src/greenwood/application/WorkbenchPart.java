/**
 * @author John Steele
 * @version 06/20/2011
 * @file WorkbenchPart.java
 * 
 * <p>
 * A class to represent the workbench part. 
 * </p>
 * WorkbenchPart is a part that can be displayed within 
 * the workbench page.  
 */
package greenwood.application;

import greenwood.ui.internal.IWorkbenchPart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * <p>
 * A workbench part is one of many parts that can be displayed 
 * within the workbench page (i.e., views and editors).
 * </p>
 */
public abstract class WorkbenchPart implements IWorkbenchPart {
	
	/**
	 * The composite for this part's widgets and controls.
	 */
	private Composite my_composite;
	
	/**
	 * The shared form toolkit factory used to create widgets 
	 * on the SashForm. 
	 */
	private FormToolkit my_toolkit;
	
	/**
	 * The workbench page containing this workbench part.
	 */
	private WorkbenchPage my_page;
	
	/**
	 * Creates a WorkbenchPart with the specified shell. 
	 * 
	 * @param the_page The workbench page containing this part.
	 * @param the_parent The parent composite.
	 * @param the_toolkit The shared form toolkit factory.
	 */
	public WorkbenchPart (WorkbenchPage the_page, Composite the_parent, FormToolkit the_toolkit) {
		this.my_page       = the_page;
		this.my_composite  = new Composite(the_parent, SWT.BORDER);
		this.my_toolkit    = the_toolkit;
	}
	
	
	/**
	 * Opens this workbench part. 
	 */
	public void open () {
		createPartControl();
	}
	
	
	/**
	 * Returns the composite for this part.
	 * 
	 * @return This part's composite.
	 */
	protected Composite getComposite () {
		return my_composite;
	}
	
	
	/**
	 * Returns the shared instance of the form toolkit factory.
	 * 
	 * @return The shared form toolkit.
	 */
	protected FormToolkit getFormToolkit () {
		return my_toolkit;
	}
	
	
	/**
	 * Returns the workbench page containing this workbench part.
	 * 
	 * @return The workbench page.
	 */
	public WorkbenchPage getWorkbenchPage () {
		return my_page;
	}
}
