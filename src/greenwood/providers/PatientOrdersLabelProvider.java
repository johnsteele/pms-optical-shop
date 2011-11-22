package greenwood.providers;

import greenwood.orders.model.Order;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.swt.graphics.Image;

public class PatientOrdersLabelProvider extends StyledCellLabelProvider implements ITableLabelProvider {
	
	@Override
	public String getColumnText(Object element, int columnIndex) {
		
		Order order = (Order) element;
		switch (columnIndex) {
		
			// Job number
			case 0: {
				return String.valueOf(order.getJobNumber());
			}
			
			// Order date
			case 1: {
				StringBuffer buff = new StringBuffer ();
				buff.append(String.valueOf(order.getOrderDate()[0]));
				buff.append("-");
				buff.append(String.valueOf(order.getOrderDate()[1]));
				buff.append("-");
				buff.append(String.valueOf(order.getOrderDate()[2]));
				return buff.toString();
			}
			
			// Description
			case 2: {
				return order.getDescription();
			}
			
			// Status
			case 3: {
				return order.getStatus();
			}
			
			// Status Date/Time
			case 4: {
				StringBuffer buff = new StringBuffer();
				buff.append(String.valueOf(order.getStatusDate()[0]));
				buff.append("-");
				buff.append(String.valueOf(order.getStatusDate()[1]));
				buff.append("-");
				buff.append(String.valueOf(order.getStatusDate()[2]));
				return buff.toString();
			}
			
			default:
				return "<unkown>";
		
		}
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
