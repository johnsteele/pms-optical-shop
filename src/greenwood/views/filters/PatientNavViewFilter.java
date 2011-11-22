/**
 * @author John Steele
 * @file   PatientNavViewFilter.java
 * @date   07/18/2011
 * 
 * <p>A class to represent the filter for the patient navigation 
 * tree viewer.</p>
 */
package greenwood.views.filters;

import greenwood.patients.model.Patient;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * <p>A filter for the patient navigation tree viewer.</p>
 * <p>Filters by the beginning of the patient's first and 
 * last name.</p>
 */
public class PatientNavViewFilter extends ViewerFilter {
	
	private String my_searchString;
	
	public void setSearchString (String the_search) {
		// Search must be substring of existing value.
		//my_searchString = ".*" + the_search + ".*";
		my_searchString = the_search;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		
		if (my_searchString == null) return true;
		if (my_searchString.length() == 0) return true;
		
		Patient p = (Patient) element;
		
		/* support for sub-expression, maybe some other day... */
		//if (p.getLastName().toLowerCase().matches(my_searchString.toLowerCase())) return true;
		//if (p.getFirstName().toLowerCase().matches(my_searchString.toLowerCase())) return true;
		
		if (p.getLastName().toLowerCase().indexOf(my_searchString.toLowerCase(), 0) == 0) return true;
		if (p.getFirstName().toLowerCase().indexOf(my_searchString.toLowerCase(), 0) == 0) return true;
		
		return false;
	}

}
