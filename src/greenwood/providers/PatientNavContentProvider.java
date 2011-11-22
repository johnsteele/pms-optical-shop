package greenwood.providers;

import greenwood.patients.model.Patient;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class PatientNavContentProvider implements ITreeContentProvider {
	
	private List<Patient> my_list;

	
	@Override
	public void dispose() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				my_list = (List<Patient>) newInput;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return my_list.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

}
