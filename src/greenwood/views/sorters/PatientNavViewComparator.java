package greenwood.views.sorters;

import greenwood.patients.model.Patient;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

public class PatientNavViewComparator extends ViewerComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		
		int result = 0;
		Patient p1 = (Patient) e1;
		Patient p2 = (Patient) e2;
		
		return p1.getLastName().compareTo(p2.getLastName());
	}
}
