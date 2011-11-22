/**
 * @author John Steele
 * @file   OrdersNavLabelProvider.java
 * @date   07/17/2011
 * 
 * <p>A class to represent the orders viewer label provider.<p>
 */
package greenwood.providers;

import greenwood.orders.model.Order;
import greenwood.patients.model.Patient;

import org.eclipse.jface.viewers.LabelProvider;

/**
 * <p>A label provider for the orders navigation tree viewer.</p>
 */
public class OrdersNavLabelProvider extends LabelProvider {
	
	@Override
	public String getText(Object element) {
		
		StringBuffer buff = new StringBuffer();
		
		if (element instanceof Patient) {
			Patient p = (Patient) element;
			buff.append(p.getLastName());
			buff.append(", ");
			buff.append(p.getFirstName());
			return buff.toString();
		}
		
		
		if (element instanceof Order) {
			Order o = (Order) element;
			buff.append(String.valueOf(o.getOrderDate()[0]));
			buff.append("-");
			buff.append(String.valueOf(o.getOrderDate()[1]));
			buff.append("-");
			buff.append(String.valueOf(o.getOrderDate()[2]));
			
			return buff.toString();
		}
		return null;
	}
}
