/**
 * @author John Steele
 * @file   OrderNavViewFilter.java
 * @date   07/18/2011
 * 
 * <p>A filter for the order tree viewer.</p>
 */
package greenwood.views.filters;

import greenwood.orders.model.Order;
import greenwood.patients.model.Patient;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * <p>The filter for the order's tree viewer.</p>
 */
public class OrderNavViewFilter extends ViewerFilter {
	
	/**
	 * The pattern we're searching for.
	 */
	private String my_searchPattern;
	
	/**
	 * Sets the search pattern.
	 * 
	 * @param The search pattern.
	 */
	public void setSearchPattern (String the_pattern) {
		my_searchPattern = the_pattern;
	}

	/**
	 * For now it filters by the patient's first and last name.
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		
		if (my_searchPattern == null) return true;
		if (my_searchPattern.length() == 0) return true;
		
		if (element instanceof Patient) {
			Patient p = (Patient) element;
			if (p.getLastName().toLowerCase().indexOf(my_searchPattern.toLowerCase(), 0) == 0) return true;
			if (p.getFirstName().toLowerCase().indexOf(my_searchPattern.toLowerCase(), 0) == 0) return true;
		}
		
		if (element instanceof Order) {
			return true;
		}
		
		return false;
	}

}
