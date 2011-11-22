package greenwood.application;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class WorkbenchImages {
	
	/**
	 * The path to the icon images. 
	 */
	private static final String ICONS_PATH = "";
	
	/**
	 * Maintains a mapping between symbolic image names and SWT image objects.
	 * Image registry manages instances of images; therefore, the programmer
	 * does not need to dispose of them, once the top-level display is disposed
	 * the image registry will dispose the images. 
	 */
	private static ImageRegistry my_image_registry;
	
	private static Map my_image_descriptors;

	
	public static ImageRegistry getImageRegistry () {
		if (my_image_registry == null) 
			initializeImageRegistry ();
		return my_image_registry;
	}
	
	public static Map getDescriptors () {
		if (my_image_descriptors == null) {
			initializeImageRegistry();
		}
		
		return my_image_descriptors;
	}
	
	private static void initializeImageRegistry () {
		my_image_registry = new ImageRegistry ();
		
		my_image_registry.put("add", ImageDescriptor.createFromURL(newURL("file:images/add.gif")));
		my_image_registry.put("delete", ImageDescriptor.createFromURL(newURL("file:images/delete.gif")));
		my_image_registry.put("person", ImageDescriptor.createFromURL(newURL("file:images/person.gif")));
		my_image_registry.put("save", ImageDescriptor.createFromURL(newURL("file:images/save.gif")));
		my_image_registry.put("new", ImageDescriptor.createFromURL(newURL("file:images/new.gif")));
		my_image_registry.put("greenwoodOptical", ImageDescriptor.createFromURL(newURL("file:images/linux.gif")));
		my_image_registry.put("order", ImageDescriptor.createFromURL(newURL("file:images/order.gif")));
		my_image_registry.put("back", ImageDescriptor.createFromURL(newURL("file:images/back.gif")));
		my_image_registry.put("forward", ImageDescriptor.createFromURL(newURL("file:images/forward.gif")));
		my_image_registry.put("home", ImageDescriptor.createFromURL(newURL("file:images/home.gif")));
		my_image_registry.put("refresh", ImageDescriptor.createFromURL(newURL("file:images/refresh.gif")));
		my_image_registry.put("patients", ImageDescriptor.createFromURL(newURL("file:images/patients.gif")));
		my_image_registry.put("web", ImageDescriptor.createFromURL(newURL("file:images/web.gif")));
		my_image_registry.put("banner", ImageDescriptor.createFromURL(newURL("file:images/banner.gif")));
		my_image_registry.put("store_account", ImageDescriptor.createFromURL(newURL("file:images/store_account.gif")));
		my_image_registry.put("newfile_wiz", ImageDescriptor.createFromURL(newURL("file:images/newfile_wiz.gif")));
		my_image_registry.put("Save", ImageDescriptor.createFromURL(newURL("file:images/Save.gif")));
	}
	
	
	public static ImageDescriptor getImageDescriptor (String the_symbolicName) {
		return (ImageDescriptor) getDescriptors().get(the_symbolicName);
	}
	
	public static Image getImage (String the_symbolicName) {
		return getImageRegistry().get(the_symbolicName);
	}
	
	public static URL newURL (String url_name) {
		try {
			return new URL(url_name);
		}
		
		catch (MalformedURLException e) {
			throw new RuntimeException("Malformed url " + url_name);
		}
	}
}
