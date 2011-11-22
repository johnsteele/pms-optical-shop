/**
 * @author John Steele
 * @file   SavedSessionAttributes.java
 * @date   07/14/2011
 * 
 * <p>A class to store session information.</p> 
 */
package greenwood.preferences;

import org.eclipse.swt.custom.CTabItem;

/**
 * <p>
 * Maintains the current sessions information, so that
 * it is saved upon shutdown of the application, and restored
 * upon running the application.
 * </p> 
 */
public class SavedSessionAttributes {

	/**
	 * The weight of the left-side sash within the sash form.
	 * Conceptually, this is the navigation area.
	 */
	private int my_leftSashWeight;
	
	/**
	 * The weight of the right-side sash within the sash form.
	 * Conceptually, this is the editor area.
	 */
	private int my_rightSashWeight;
	
	/**
	 * The active tab item within navigation area.
	 * This is never <code>null</code> because the navigation
	 * area is always active, thus a tab is always being displayed.
	 */
	private CTabItem my_activeNavTab;
	
	/**
	 * The active tab item within the editor area.
	 * This is <code>null</code> if there not an editor tab open. 
	 */
	private CTabItem my_activeEditorTab;
	
	
}
