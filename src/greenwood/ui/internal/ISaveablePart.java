/**
 * @author John Steele
 * @version 06/24/2011
 * @file ISaveablePart.java
 * 
 * <p>
 * An interface to represent a saveable workbench part.
 * </p>
 */
package greenwood.ui.internal;

/**
 * <p>
 * Workbench parts implement or adapt to this interface
 * to participate in the enablement and execution of the
 * <code>Save</code> and <code>Save As</code> actions. 
 * </p>
 */
public interface ISaveablePart {

	/**
	 * Saves the contents of this part. 
	 */
	public void doSave ();
	
	/**
	 * Returns if the contents of this part need to be saved.
	 * 
	 * @return <code>true</code> if the contents have been modified and need to 
	 * be saved, <code>false</code> if they have not been changed since the last
	 * save action. 
	 */
	public boolean isDirty ();
}
