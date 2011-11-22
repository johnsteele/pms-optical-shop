/**
 * @author John Steele
 * @version 06/28/2011
 * @file EditorPart.java
 * 
 * <p>
 * A class to represent a editor part within the workbench. 
 * </p>
 * EditorPart is a part that can be displayed within 
 * the workbench page.  
 */
package greenwood.editors;

import java.beans.PropertyChangeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

import greenwood.application.WorkbenchPage;
import greenwood.application.WorkbenchPart;

/**
 * EditorPart is an abstract base class for all editors displayed within the workbench.
 * Common functionality shared by all editors is implemented here. 
 */
public abstract class EditorPart extends WorkbenchPart implements IEditorPart, PropertyChangeListener {

	/**
	 * Every editor part is displayed as tab within a tab folder.
	 */
	private CTabItem my_tabItem;
	
	/**
	 * Flag to indicate patient information has been modified 'dirty'
	 * and the changes have not been saved. 
	 */
	protected boolean my_isDirtyPatient;
	
	/**
	 * Creates a EditorPart object with the specified parent composite.
	 * 
	 * @param the_page The workbench page containing this editor part.
	 * @param the_folder The parent folder of the tab item.
	 * @param the_toolkit The shared form toolkit factory. 
	 */
	public EditorPart(WorkbenchPage the_page, CTabFolder the_folder, FormToolkit the_toolkit) {
		super(the_page, the_folder, the_toolkit);
		my_tabItem = new CTabItem(the_folder, SWT.NULL);
		my_tabItem.setControl(getComposite());
	}

    /**
     * Asks this part to take focus within the workbench. Parts must
     * assign focus to one of the controls contained in the part's
     * parent composite.
     */
	@Override
	public abstract void setFocus();

	/**
	 * Creates the SWT controls for this editor. 
	 */
	@Override
	public abstract void createPartControl();

	
	/**
	 * Disposes the workbench recourses, fonts, images, etc.&nbsp;
	 * Also, unregister any listeners from the workbench.
	 */
	@Override
	public abstract void dispose();
	
	
	/**
	 * Returns the tab item.
	 * 
	 * @return The tab item containing this editor.
	 */
	public CTabItem getTabItem () {
		return my_tabItem;
	}
	
}