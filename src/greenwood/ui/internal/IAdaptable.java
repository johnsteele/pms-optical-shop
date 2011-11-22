/**
 * @author John Steele
 * @version 06/24/2011
 * @file IAdaptable.java
 * 
 * <p>
 * An interface for adaptable objects. 
 * </p>
 */
package greenwood.ui.internal;

public interface IAdaptable {
	
	/**
	 * Returns an Object which is an instance of the provided class
	 * associated with this object. Returns <code>null</code> if 
	 * not such object can be found. 
	 * 
	 * @param adapter The adapter class to look up.
	 * 
	 * @return A object castable to the given class, or
	 *         <code>null</code> if this object does not
	 *         have an adapter for the given class. 
	 */
	public Object getAdapter (Class adapter);
}
