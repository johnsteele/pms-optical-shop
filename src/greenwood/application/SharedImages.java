package greenwood.application;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class SharedImages {

	public Image getImage (String the_symbolicName) {
		Image image = WorkbenchImages.getImage (the_symbolicName);
		if (image != null) return image;
		
		// If there is a descriptor for it, add the image to the registry.
		ImageDescriptor desc = WorkbenchImages.getImageDescriptor (the_symbolicName);
		if (desc != null) {
			WorkbenchImages.getImageRegistry().put (the_symbolicName, desc);
			return WorkbenchImages.getImageRegistry().get(the_symbolicName);
		}
		
		return null;
	}
}
