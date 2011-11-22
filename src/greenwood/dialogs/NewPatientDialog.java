package greenwood.dialogs;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewPatientDialog extends TitleAreaDialog {

	public NewPatientDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		
		getShell().setText("New Patient");
		setTitle("Create New Patient");
		setMessage("Enter the new patient information.");
		final Composite comp = (Composite) super.createDialogArea(parent);
		createFormArea (comp);	
		//setTitleImage(WorkbenchImages.getImageRegistry().get("person"));
		return comp;
	}
	
	/**
	 * Creates the new patient input fields. 
	 */
	private void createFormArea (Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		
	/* First Name */
		Label firstLabel = new Label(container, SWT.NONE);
		firstLabel.setText("First: ");
		
		Text firstText = new Text(container, SWT.BORDER);
		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		firstText.setLayoutData(data);
		
	/* Last Name */
		Label lastLabel = new Label(container, SWT.NONE);
		lastLabel.setText("Last: ");
		
		Text lastText = new Text(container, SWT.BORDER);
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		lastText.setLayoutData(data);		
	}

}
