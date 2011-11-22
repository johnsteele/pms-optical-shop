/**
 * @author John Steele
 * @version 06/20/2011
 * @file WorkbenchWindow.java
 * 
 * <p>
 * A class to represent the workbench window. 
 * </p>
 * WorkbenchWindow is a high-level "main window", with support 
 * for a menu bar with standard menus, toolbar, and status line. 
 */
package greenwood.application;

import greenwood.menus.MenuManager;
import greenwood.menus.ToolbarManager;

import org.eclipse.swt.widgets.Shell;

/**
 * <p>
 * A window within the workbench. The primary responsibility of
 * the workbench window is to manage the window widgets such
 * as the menu and the toolbar. 
 * </p>
 */
public class WorkbenchWindow {
	
	/**
	 * The menu bar manager, or <code>null</code> if none. 
	 */
	private MenuManager my_menu_manager;
	
	/**
	 * The toolbar manager, or <code>null</code> if none. 
	 */
	private ToolbarManager my_toolbar_manager;
	
	/**
	 * The workbench page.
	 */
	private WorkbenchPage my_page;
	
	
	/**
	 * Creates a WorkbenchWindow with the specified shell. 
	 * 
	 * @param the_shell The top-level workbench shell. 
	 */
	public WorkbenchWindow (Shell the_shell) {
		my_menu_manager    = new MenuManager(the_shell);
		my_toolbar_manager = new ToolbarManager(the_shell);		
		my_page            = new WorkbenchPage(the_shell);
	}
	
	
	/**
	 * Opens the menubar, toolbar, and the window page.  
	 */
	public void open () {
		my_menu_manager.openMenu();
		my_toolbar_manager.openToolbar();
		my_page.open();
	}
	
	
	/**
	 * Returns the active workbench page.
	 * 
	 * @return The active workbench page.
	 */
	public WorkbenchPage getPage() {
		return my_page;
	}
}
