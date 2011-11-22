package greenwood.providers;

import greenwood.application.WorkbenchImages;
import greenwood.orders.model.StoreAccount;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class StoreAccountLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof StoreAccount) 
			return ((StoreAccount)element).toString();
		
		else 
			return "unkown";
	}
	
	
	@Override
	public Image getImage(Object element) {
	
		if (element instanceof StoreAccount) 
			return WorkbenchImages.getImageRegistry().get("store_account");
		
		return null;
	}
}
