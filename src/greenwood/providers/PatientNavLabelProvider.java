/**
 * @author John Steele
 * @version 06/20/2011
 * @file PatientNavLabelProvider.java
 * 
 * <p>
 * A class to represent the patient label provider.
 * </p>
 */
package greenwood.providers;
import greenwood.patients.model.Patient;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class PatientNavLabelProvider extends LabelProvider {
	
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
		
		return null;
	}
	
	@Override
	public Image getImage(Object element) {
		return null; //WorkbenchImages.getImageRegistry().get("person");
	}
}
