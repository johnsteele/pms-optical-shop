package greenwood.views.sorters;

import java.util.Date;

import greenwood.orders.model.Order;
import greenwood.patients.model.Patient;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

public class OrderNavViewComparator extends ViewerComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		
		int result = 0;
		
		if (e1 instanceof Patient && e2 instanceof Order) {}
		
		else if (e1 instanceof Order && e2 instanceof Patient) {}
		
		else if (e1 instanceof Patient && e2 instanceof Patient) {
			Patient p1 = (Patient) e1;
			Patient p2 = (Patient) e2;
			result = p1.getLastName().compareTo(p2.getLastName());
		}
		
		else if (e1 instanceof Order && e2 instanceof Order) {
			int[] d1 = ((Order) e1).getOrderDate();
			int[] d2 = ((Order) e2).getOrderDate();
			// Not sure how or if I should do this.
		}
		
		return result;
	}
}
