/**
 * @author John Steele
 * @file   OrdersNavContentProvider.java
 * @date   07/17/2011
 * 
 * <p>A content provider for the orders tree viewer.</p>
 */
package greenwood.providers;

import greenwood.application.Workbench;
import greenwood.orders.model.Order;
import greenwood.patients.model.Patient;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * <p>A content provider for the orders tree viewer.</p>
 */
public class OrdersNavContentProver implements ITreeContentProvider {


	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return Workbench.getInstance().getDatabase().getPatients().toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		
		if (parentElement instanceof Patient) 
			return ((Patient) parentElement).getOrders().toArray();
		
		else 
			return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Patient) 
			return true;
		else
			return false;
	}
	
	@Override
	public void dispose() {
	}
}
