/**
 * @author John Steele
 * @file   HomeView.java
 * @date   07/13/2011
 * 
 * Home view represents a class responsible for displaying 
 * an overview of all the applications functionality. Conceptually,
 * it can be viewed as a dashboard.
 */
package greenwood.editors;

import java.beans.PropertyChangeEvent;

import greenwood.application.WorkbenchPage;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * HomeView displays an overview of the common functionality offered
 * by the application, similar to a dashboard.
 */
public class HomeEditor extends EditorPart {
	
	/**
	 * The name displayed on the folder tab item.
	 */
	private static final String TAB_TITLE = "Home";
	private static final String TAB_TOOLTIP = "Home";
	
	/**
	 * The home page is represented as a form.
	 */
	private ScrolledForm my_form;

	
	/**
	 * Creates a HomeView object with default values.
	 * 
	 * @param the_page The page containing this view.
	 * @param the_folder The parent composite tab folder.
	 * @param the_toolkit The shared toolkit factory.
	 */
	public HomeEditor (WorkbenchPage the_page, CTabFolder the_folder, FormToolkit the_toolkit) {
		
		super(the_page, the_folder, the_toolkit);
		getTabItem().setShowClose(true);
		getTabItem().setText(TAB_TITLE);
		getTabItem().setToolTipText(TAB_TOOLTIP);
		
		initForm ();
	}
	
	
	/**
	 * Initializes the form.
	 */
	private void initForm () {
		my_form = new ScrolledForm(getComposite());
	}


	@Override
	public void setFocus() {
	}


	@Override
	public void createPartControl() {
	}


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
