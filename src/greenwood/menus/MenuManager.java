/**
 * @author John Steele
 * @version 06/27/2011
 * @file MenuManager.java
 * 
 * <p>
 * A class to represent the menu manager. 
 * </p>
 */
package greenwood.menus;

import java.lang.reflect.Method;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/**
 * The menu manager is a container for the menu bar for managing the 
 * menu bar and all of its menu items. It handles the visibility of 
 * the items, as well as their call-backs (handlers).
 */
public class MenuManager {
	
	/**
	 * The top-level shell.
	 */
	private Shell my_shell;
	
	/**
	 * The shell's menubar. 
	 */
	private Menu my_menu;

	
	/**
	 * Creates a MenuManager object with the specified shell.
	 * 
	 * @param the_shell The top-level shell.
	 */
	public MenuManager (Shell the_shell) {
		this.my_shell = the_shell;
		my_menu  = new Menu(my_shell, SWT.BAR | SWT.LEFT_TO_RIGHT);
	}
	
	
	/**
	 * Opens the menubar.
	 */
	public void openMenu () {
		initMenu ();
		my_shell.setMenuBar(my_menu);
	}
	
	
	/**
	 * Initializes the menubar.
	 */
	protected void initMenu () {
		createActions ();
	}
	
	
	/**
	 * Creates the actions to be placed in the menubar. 
	 */
	protected void createActions () {
	
		/* File */ 
			MenuItem fileItem = 
				createMenuItem (my_menu, SWT.CASCADE, "&File", null, -1, true, null);
			
			Menu fileDropDown =
				createMenu (my_shell, SWT.DROP_DOWN, fileItem, true);
			
			// New 
			createMenuItem (fileDropDown, SWT.PUSH, "&New", null, -1, true, null);
			
			// Separator
			createMenuItem (fileDropDown, SWT.SEPARATOR, null, null, -1, false, null);
					
			// Exit
			createMenuItem (fileDropDown, SWT.PUSH, "E&xit\tCtrl+X", null, SWT.CTRL + 'X', true, "exitCallback");
			
			
		/* Edit */
			MenuItem editItem =
				createMenuItem (my_menu, SWT.CASCADE, "&Edit", null, -1, true, null);
			
			Menu editMenuDropDown =
				createMenu (my_shell, SWT.DROP_DOWN, editItem, true);
			
			// Undo
			createMenuItem(editMenuDropDown, SWT.PUSH, "&Undo Typing\t Ctrl+Z", null, SWT.CTRL + 'Z', true, "undoCallback");
			
			
		/* Search */ 
			MenuItem searchItem = createMenuItem(my_menu, SWT.CASCADE, "Se&arch", null, -1, true, null);
			
		/* Window */ 
			MenuItem windowItem = createMenuItem(my_menu, SWT.CASCADE, "&Window", null, -1, true, null);
			
		/* Help */
			MenuItem helpItem = createMenuItem(my_menu, SWT.CASCADE, "&Help", null, -1, true, null);
	}
	

	/**
	 * Creates a Menu using the specified values.
	 * 
	 * @param parent The parent menu.
	 * @param enabled The true for enabled false for disabled.
	 * @return A new menu with the specified values.
	 */
	protected Menu createMenu (Menu parent, boolean enabled) {
		Menu m = new Menu (parent);
		m.setEnabled(enabled);
		return m;
	}
	
	
	/**
	 * Creates a Menu with the specified values. 
	 * 
	 * @param parent The shell parent.
	 * @param style The style of menu to create.
	 * @return The new menu.
	 */
	protected Menu createMenu (Shell parent, int style) {
		Menu m = new Menu (parent, style);
		return m;
	}
	
	
	/**
	 * Creates  a Menu with the specified values.
	 * 
	 * @param parent The parent menu.
	 * @param style The style of the menu.
	 * @param container The MenuItem to set as container.
	 * @param enabled Enabled state.
	 * @return The new Menu with the specified values. 
	 */
	protected Menu createMenu (Shell parent, int style, MenuItem container, boolean enabled) {
		Menu m = createMenu (parent, style);
		m.setEnabled(enabled);
		container.setMenu(m);
		return m;
	}
	
	
	/**
	 * Creates a MenuItem using the specified values. 
	 * 
	 * @param parent The parent Menu.
	 * @param style The style of MenuItem.
	 * @param text The text on the MenuItem.
	 * @param icon The MenuItem icon.
	 * @param accelerator The keyboard accelerator. (use -1 if none)
	 * @param enabled The enabled state.
	 * @param callback The call-back called from the item selection listener.
	 * @return The newly created MenuItem.
	 */
	protected MenuItem createMenuItem (Menu parent, int style, String text, 
				Image icon, int accelerator, boolean enabled, String callback) {
		
		MenuItem item = new MenuItem (parent, style);
		
		if (text != null) {
			if (text.length() > 0)
				item.setText(text);
		}
		
		if (icon != null) {
			item.setImage(icon);
		}
		
		if (accelerator != -1) {
			item.setAccelerator(accelerator);
		}
		
		item.setEnabled(enabled);
		
		if (callback != null) {
			if (callback.length() > 0)
				registerCallback (item, this, callback);	
		}
		
		return item;
	}
	
	
	/**
	 * Uses Java reflection to link the MenuItem with the 
	 * method that processes them. 
	 * 	
	 * @param item The menu item to link the call-back to.
	 * @param handler The object containing the call-back method.
	 * @param handlerName The name of the call-back method.
	 */
	protected void registerCallback (final MenuItem item, 
									 final Object handler, 
									 final String handlerName) {
		
		item.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					Method m = handler.getClass().getMethod(handlerName, null);
					m.invoke(handler, null);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * A handler method to be used as a call-back to 
	 * exit the application. 
	 */
	public void exitCallback () {
		my_shell.dispose();
	}
	
	
	public void undoCallback () {
		System.out.println("Undo...");
	}
}
