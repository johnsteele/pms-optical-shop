/**
 * @author John Steele
 * @version 06/28/2011
 * @file ViewPart.java
 * 
 * <p>
 * A class to represent a view part within the workbench. 
 * </p>
 * ViewPart is a part that can be displayed within 
 * the workbench page.  
 */
package greenwood.views;

import java.beans.PropertyChangeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

import greenwood.application.WorkbenchPage;
import greenwood.application.WorkbenchPart;

/**
 * <p>
 * A view part is a part that can be displayed in the workbench. 
 * </p>
 */
public abstract class ViewPart extends WorkbenchPart implements IViewPart, PropertyChangeListener{
	
	/**
	 * Each view is a tab within a tab folder.
	 */
	private CTabItem my_tabItem;
	
	/**
	 * Creates a ViewPart with the specified parent and shared form toolkit.
	 * 
	 * @param the_page The workbench page containing this view.
	 * @param the_folder The parent composite tab folder. 
	 * @param the_toolkit The shared form toolkit.
	 */
	public ViewPart(WorkbenchPage the_page, CTabFolder the_folder, 
						FormToolkit the_toolkit) {
		
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
	 * Creates the SWT controls for this view. 
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
	 * Returns the tab item for this view.
	 * 
	 * @return The tab item.
	 */
	public CTabItem getTabItem () {
		return my_tabItem;
	}
}
