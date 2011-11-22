/**
 * @author John Steele
 * @version 06/27/2011
 * @file ToolbarManager.java
 * 
 * <p>
 * A class to represent the toolbar manager.
 * </p>
 */
package greenwood.menus;

import greenwood.application.WorkbenchImages;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * The toolbar manager is a container for managing the toolar
 * and all of its tool items. It handles the visibility of 
 * the items, as well as their call-backs (handlers).
 */
public class ToolbarManager {
	
	/**
	 * The toolbars' composite.
	 */
	private Composite my_composite;
	
	/**
	 * The shells' toolbar.
	 */
	private ToolBar my_toolbar;
	
	
	/**
	 * Creates a ToolbarManager object with the specified shell.
	 * 
	 * @param the_shell The top-level application shell.
	 */
	public ToolbarManager (Shell the_shell) {
		this.my_composite = new Composite(the_shell, SWT.NONE);
		my_composite.setLayout(new RowLayout());
		my_toolbar = new ToolBar(my_composite, SWT.HORIZONTAL | SWT.FLAT | SWT.WRAP | SWT.LEFT);
	}
	
	
	/**
	 * Opens the toolbar (i.e., creates it).
	 */
	public void openToolbar () {		
	
		ToolItem back = new ToolItem(my_toolbar, SWT.PUSH);
		back.setImage(WorkbenchImages.getImageRegistry().get("back"));
		back.setToolTipText("Back");

		
		ToolItem forward = new ToolItem(my_toolbar, SWT.PUSH);
		forward.setImage(WorkbenchImages.getImageRegistry().get("forward"));
		forward.setToolTipText("Forward");
		
		ToolItem refresh = new ToolItem(my_toolbar, SWT.PUSH);
		refresh.setImage(WorkbenchImages.getImageRegistry().get("refresh"));
		
		ToolItem sep = new ToolItem(my_toolbar, SWT.SEPARATOR);
		
		ToolItem web = new ToolItem (my_toolbar, SWT.PUSH);
		web.setImage(WorkbenchImages.getImageRegistry().get("web"));
		web.setToolTipText("Open web browser");
		
		ToolItem home = new ToolItem(my_toolbar, SWT.PUSH);
		home.setImage(WorkbenchImages.getImageRegistry().get("home"));
		home.setToolTipText("Goto home");
	}
	
	
	/**
	 * Creates the toolbar. 
	 */
	protected void createToolbar () {
		
	}
	
	
	/**
	 * Creates the item actions.
	 */
	protected void creatActions () {
		
	}

}
