/**
 * @author John Steele
 * @version 06/20/2011
 * @file PatientEditor.java
 * 
 * <p>
 * A class to represent a patient editor. 
 * </p>
 */
package greenwood.editors;

import java.beans.PropertyChangeEvent;

import greenwood.application.Workbench;
import greenwood.application.WorkbenchImages;
import greenwood.application.WorkbenchPage;
import greenwood.patients.model.Patient;
import greenwood.views.PatientNavView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * <p>
 * PatientEditor is the outer-most part for displaying
 * patient information. The patient information it displays
 * corresponds to the current selection of the PatientNavView.
 * </p><p>
 * This editor also contains other composites for displaying 
 * patient information, some information may be edited others
 * may not.
 * </p><p>
 * This editor can conceptually be viewed as a container for all patient
 * information. Just because it is an editor doesn't mean it doesn't contain
 * sub-views.
 * </p>
 * 
 * @see PatientNavView
 */
public class PatientEditor extends EditorPart {
	
	/**
	 * The form for this editor.
	 */
	private ScrolledForm my_form;
	
	/**
	 * The patient being currently being displayed in the editor.
	 */
	private Patient my_patient;
	
	/**
	 * Whether we're loading a patient's info, or creating a new patient.
	 */
	private boolean my_isNewPatient;

	/**
	 * Top toolbar buttons.
	 */
	private Button my_editButton;
	private Action saveAction;
	
	/**
	 * Creates a PatientEditor object with the specified parent Composite, 
	 * and shared form toolkit.
	 * 
	 * @param the_patient The patient to render in this editor.
	 * @param the_page The workbench page containing this editor part.
	 * @param the_folder The parent composite tab folder for the tab item.
	 * @param the_toolkit The shared form toolkit factory.
	 */
    public PatientEditor(Patient the_patient, WorkbenchPage the_page, CTabFolder the_folder, FormToolkit the_toolkit) {
		super(the_page, the_folder, the_toolkit);
		
		my_isDirtyPatient = false;

		getTabItem().setImage(WorkbenchImages.getImageRegistry().get("person"));
		getTabItem().setShowClose(true);
		
		if (the_patient != null) {
			my_patient = the_patient;
		}
		
		else {
			my_isNewPatient = true;
			my_patient = new Patient ();
			// adds patient to database, and the cache.
			Workbench.getInstance().getDatabase().insertPatient(my_patient);
		}
		my_patient.addPropertyChangeListener("my_firstName", this);
		my_patient.addPropertyChangeListener("my_lastName", this);
	}

	
	/**   
	 * Creates the SWT controls for this editor. 
	 */
	@Override
	public void createPartControl() {
		
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		super.getComposite().setLayout(layout);
		super.getComposite().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		my_form = getFormToolkit().createScrolledForm(getComposite());	
		my_form.setLayout(new GridLayout(2, true));
		my_form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		layout = new GridLayout (2, true);
		my_form.getBody().setLayout(layout);
		
		/* Form header */
		setFormHeader ();
		createFormToolbar ();
		paintHeaderBackground ();
		
		/* Patient Details */
		createPersonalSection ();
		createWebsiteSection ();
		
		/* Eye care information */
		createDoctorSection ();
		
		/* Contact details */
		createContactSection ();
		
		Dialog.applyDialogFont(my_form.getBody());
		setFormHeader();
		my_form.reflow(true);
	}
	
	
	/**
	 * Sets the header text on the form.
	 */
	private void setFormHeader () {
		int lastLength = my_patient.getLastName().length();
		int firstLength = my_patient.getFirstName().length();
		String lastName = my_patient.getLastName();
		String firstName = my_patient.getFirstName();
		
		if (lastLength > 0 && firstLength > 0) {
			my_form.setText(lastName + ", " + firstName);
			getTabItem().setText(lastName + ", " + firstName + " - Profile");
			getTabItem().setToolTipText(lastName + ", " + firstName + " - Profile");
		}
		
		else if (lastLength > 0 && firstLength == 0) {
			my_form.setText(lastName);
			getTabItem().setText(lastName + " - Profile");
			getTabItem().setToolTipText(lastName + " - Profile");
		}
		
		else if (firstLength > 0 && lastLength == 0) {
			my_form.setText(firstName);
			getTabItem().setText(firstName);
			getTabItem().setToolTipText(firstName + " - Profile");
		}
		
		else {
			my_form.setText("New Patient");
			getTabItem().setText("New Patient");
			getTabItem().setToolTipText("New Patient - Profile");
		}
	}
	
	
	/**
	 * Creates the form toolbar.
	 */
	private void createFormToolbar () {
		
		// You can create the action and add it to the manager, which in tern will
		// create the tool item.
		saveAction = new Action() {
			@Override
			public void run() {
				System.out.println("Performing save...");
			}
		};
		saveAction.setEnabled(true);
		saveAction.setImageDescriptor(ImageDescriptor.createFromURL(WorkbenchImages.newURL("file:images/Save.gif")));
		my_form.getToolBarManager().add(saveAction);
		
		
		// Or you can create the contribution item and explicitly give it the action.
//		ActionContributionItem item = new ActionContributionItem(save);
//		my_form.getToolBarManager().add(item);
		
		// This contribution is specific to toolbars.
//		ControlContribution save2 = new ControlContribution("save") {
//			@Override
//			protected Control createControl(Composite parent) {
//				Button b = new Button (parent, SWT.PUSH);
//				b.setImage(WorkbenchImages.getImageRegistry().get("save"));
//				b.setToolTipText("Save changes");
//				//b.setEnabled(false);
//				return b;
//			}
//		};
//		my_form.getToolBarManager().add(save2);
		my_form.getToolBarManager().update(true);
	}
	
	
	/**
	 * Creates the personal information section. 
	 */
	private void createPersonalSection () {
		Section section = getFormToolkit().createSection(my_form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		section.setActiveToggleColor(getFormToolkit().getHyperlinkGroup().getActiveBackground());
		section.setToggleColor(getFormToolkit().getColors().getColor(IFormColors.SEPARATOR));
		section.setText("Personal Details");
		section.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite client = getFormToolkit().createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout(2, false));
		client.setLayoutData(new GridData (GridData.FILL_BOTH));
		client.pack();
		section.setClient(client);
		getFormToolkit().paintBordersFor(client);
		
		/* First, Last, Gender, DOB */		
		getFormToolkit().createLabel(client, "First Name: ");
		final Text firstText = getFormToolkit().createText(client, my_patient.getFirstName());
		firstText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		firstText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.setFirstName(firstText.getText());
				
			}
		});
		
		getFormToolkit().createLabel(client, "Last Name: ");
		final Text lastText = getFormToolkit().createText(client, my_patient.getLastName());
		lastText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		lastText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.setLastName(lastText.getText());
			}
		});
		
		getFormToolkit().createLabel(client, "Gender: ");
		final Combo genderCombo = new Combo(client, SWT.READ_ONLY | SWT.POP_UP);
		genderCombo.add("Male");
		genderCombo.add("Female");
		genderCombo.select(my_patient.getGender() == true ? 0 : 1);
		genderCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.setGender(genderCombo.getSelectionIndex() == 0);
			}
		});
		
		getFormToolkit().createLabel(client, "Date of Birth: ");
		final Text dobText = getFormToolkit().createText(client, my_patient.getDOB());
		dobText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		dobText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.setDOB(dobText.getText());
			}
		});
		
		
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				my_form.reflow(true);
			}
		});
		section.pack();
	}
	
	
	/**
	 * Creates the website information.
	 */
	private void createWebsiteSection () {
		Section section = getFormToolkit().createSection(my_form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		section.setText("Website Access");
		section.setActiveToggleColor(getFormToolkit().getHyperlinkGroup().getActiveBackground());
		section.setToggleColor(getFormToolkit().getColors().getColor(IFormColors.SEPARATOR));
		section.setLayoutData(new GridData (GridData.FILL_BOTH));
				
		Composite client = getFormToolkit().createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout (2, false));
		client.setLayoutData(new GridData (GridData.FILL_BOTH));
		client.pack();
		getFormToolkit().paintBordersFor(client);
		section.setClient(client);
		
		/* Email, Username, Password */
		getFormToolkit().createLabel(client, "Email: ");
		final Text emailText = getFormToolkit().createText(client, my_patient.getWebInfo().getEmail());
		emailText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		emailText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getWebInfo().setEmail(emailText.getText());
			}
		});
		
		getFormToolkit().createLabel (client, "Username: ");
		final Text userText = getFormToolkit().createText (client, my_patient.getWebInfo().getUsername());
		userText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		userText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getWebInfo().setUsername(userText.getText());
			}
		});
		
		getFormToolkit().createLabel (client, "Password: ");
		final Text passText = getFormToolkit().createText(client, my_patient.getWebInfo().getPassword());
		passText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		passText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getWebInfo().setPassword(passText.getText());
			}
		});
		
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				my_form.reflow(true);
			}
		});
		section.pack();
	}
	
	
	/**
	 * Creates the doctor section.
	 */
	private void createDoctorSection () {
		Section section = getFormToolkit().createSection(my_form.getBody(), Section.TWISTIE | Section.DESCRIPTION | Section.TITLE_BAR);
		GridData data = new GridData (GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		section.setLayoutData(data);
		section.setText("Eye Care Information");
		section.setExpanded(true);
		
		Composite client = getFormToolkit().createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout (2, true));
		client.setLayoutData(new GridData (GridData.FILL_BOTH));
		getFormToolkit().paintBordersFor(client);
		section.setClient(client);
		
		/* Practice info */
		Composite practiceComp = getFormToolkit().createComposite(client, SWT.WRAP);
		practiceComp.setLayout(new GridLayout (2, false));
		practiceComp.setLayoutData(new GridData (GridData.FILL_BOTH));
		getFormToolkit().paintBordersFor(practiceComp);
		
		getFormToolkit().createLabel(practiceComp, "Practice: ");
		getFormToolkit().createText(practiceComp, "UW Optical", SWT.READ_ONLY).
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
		getFormToolkit().createLabel(practiceComp, "Location: ");
		final Combo locationCombo = new Combo(practiceComp, SWT.READ_ONLY | SWT.POP_UP);
		locationCombo.add("1342 North 85th Seattle WA 98105");
		locationCombo.add("5519 Sounth Brooklyn Seattle WA 90815");
		locationCombo.add("44 West Madison Bellevue WA 99025");
		locationCombo.add("25 East Olympic Olympia WA 98025");
		locationCombo.add("--");
		locationCombo.select(my_patient.getEyeInfo().getLocationIndex());
		locationCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				my_patient.getEyeInfo().setLocationIndex(locationCombo.getSelectionIndex());
			}
		});
		
		
		getFormToolkit().createLabel(practiceComp, "Doctor: ");
		final Text docText = getFormToolkit().createText(practiceComp, my_patient.getEyeInfo().getDoctor());
		docText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		docText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getEyeInfo().setDoctor(docText.getText());
			}
		});
		
		/* Exam section */
		Composite examComp = getFormToolkit().createComposite(client, SWT.WRAP);
		examComp.setLayout(new GridLayout (2, false));
		examComp.setLayoutData(new GridData (GridData.FILL_BOTH));
		getFormToolkit().paintBordersFor(examComp);
		
		getFormToolkit().createLabel(examComp, "Patient Discount (%): ");
		final Text discountText = getFormToolkit().createText(examComp, String.valueOf(my_patient.getEyeInfo().getDiscount()));
		discountText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		discountText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String discount = discountText.getText();
				if (discount.length() > 0) { 
					my_patient.getEyeInfo().setDiscount(Integer.valueOf(discount));
				}
			}
		});
		
		getFormToolkit().createLabel(examComp, "Last Exam Date: ");
		final Text lastExamText = getFormToolkit().createText(examComp, my_patient.getEyeInfo().getLastExam());
		lastExamText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		lastExamText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				my_patient.getEyeInfo().setLastExam(lastExamText.getText());
			}
		});
		
		getFormToolkit().createLabel(examComp, "Next Exam Date: ");
		final Text nextExamText = getFormToolkit().createText(examComp, my_patient.getEyeInfo().getNextExam());
		nextExamText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		nextExamText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getEyeInfo().setNextExam(nextExamText.getText());
			}
		});
		
		getFormToolkit().createLabel(examComp, "Email notification?");
		Composite emailGroup = getFormToolkit().createComposite(examComp, SWT.WRAP);
		emailGroup.setLayout(new GridLayout(2, false));
		emailGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		boolean notify = my_patient.getEyeInfo().isEmailNotification();
		
		final Button yesButton = getFormToolkit().createButton(emailGroup, "Yes", SWT.CHECK);
		yesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				my_patient.getEyeInfo().setEmailNotification(true);
			}
		});
		
		final Button noButton  = getFormToolkit().createButton(emailGroup, "No", SWT.CHECK);
		noButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				my_patient.getEyeInfo().setEmailNotification(false);
			}
		});
		if (notify) {
			yesButton.setSelection(true);
			noButton.setSelection(false);
		}
		else {
			noButton.setSelection(true);
			yesButton.setSelection(false);
		}
		
			
		emailGroup.pack();
		
		Label notesLabel = getFormToolkit().createLabel(client, "Practioner Notes");
		data = new GridData (GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		data.verticalAlignment = SWT.BOTTOM;
		notesLabel.setLayoutData(data);
		notesLabel.pack();
		
		
		final Text notesText = getFormToolkit().createText(client, my_patient.getEyeInfo().getNotes() + "\n\n\n", SWT.V_SCROLL | SWT.MULTI);
		data = new GridData (GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		notesText.setLayoutData(data);
		notesText.setSize(200, 400);
		notesText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getEyeInfo().setNotes(notesText.getText());
			}
		});
		
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				my_form.reflow(true);
			}
		});
		
		//section.pack();
	}
	
	
	/**
	 * Creates the contact section. 
	 */
	private void createContactSection () {
		Section section = getFormToolkit().createSection(my_form.getBody(), Section.TWISTIE | Section.DESCRIPTION | Section.TITLE_BAR);
		GridData data = new GridData (GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		section.setLayoutData(data);
		section.setExpanded(true);
		section.setText("Patient Contact");
		
		/* Client composite */
		Composite client = getFormToolkit().createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout (2, true));
		client.setLayoutData(new GridData(GridData.FILL_BOTH));
		section.setClient(client);
		getFormToolkit().paintBordersFor(client);
		
		/* Address info */
		Composite addressComp = getFormToolkit().createComposite(client, SWT.WRAP);
		addressComp.setLayout(new GridLayout (2, false));
		addressComp.setLayoutData(new GridData (GridData.FILL_BOTH));
		getFormToolkit().paintBordersFor(addressComp);
		
		// Address1
		getFormToolkit().createLabel(addressComp, "Address1: ");
		final Text addr1Text = getFormToolkit().createText(addressComp, my_patient.getContactInfo().getAddress1());
		addr1Text.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		addr1Text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getContactInfo().setAddress1(addr1Text.getText());
			}
		});
		
		// Address2
		getFormToolkit().createLabel(addressComp, "Address2: ");
		final Text addr2Text = getFormToolkit().createText(addressComp, my_patient.getContactInfo().getAddress2());
		addr2Text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		addr2Text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getContactInfo().setAddress2(addr2Text.getText());
			}
		}); 
		
		// Address3
		getFormToolkit().createLabel(addressComp, "Address3: ");
		final Text addr3Text = getFormToolkit().createText(addressComp, my_patient.getContactInfo().getAddress3());
		addr3Text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		addr3Text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getContactInfo().setAddress3(addr3Text.getText());
			}
		});
		
		// City
		getFormToolkit().createLabel(addressComp, "City: ");
		final Text cityText = getFormToolkit().createText(addressComp, my_patient.getContactInfo().getCity());
		cityText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		cityText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getContactInfo().setCity(cityText.getText());
			}
		});
		
		// State
		getFormToolkit().createLabel(addressComp, "State: ");
		final Text stateText = getFormToolkit().createText(addressComp, my_patient.getContactInfo().getState());
		stateText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		stateText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getContactInfo().setState(stateText.getText());
			}
		});
		
		// Zip
		getFormToolkit().createLabel(addressComp, "Zipcode: ");
		final Text zipText = getFormToolkit().createText(addressComp, String.valueOf(my_patient.getContactInfo().getZip()));
		zipText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));	
		zipText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getContactInfo().setZip(Integer.valueOf(zipText.getText()));
			}
		});
		
		/* Contact info */
		Composite contactComp = getFormToolkit().createComposite(client, SWT.WRAP);
		contactComp.setLayout(new GridLayout (2, false));
		contactComp.setLayoutData(new GridData (GridData.FILL_BOTH));
		getFormToolkit().paintBordersFor(contactComp);
		
		// Phone
		getFormToolkit().createLabel(contactComp, "Phone: ");
		final Text phoneText = getFormToolkit().createText(contactComp, my_patient.getContactInfo().getHome_phone());
		phoneText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		phoneText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getContactInfo().setHome_phone(phoneText.getText());
			}
		});
		
		// Mobile Phone
		getFormToolkit().createLabel(contactComp, "Mobile Phone: ");
		final Text mobileText = getFormToolkit().createText(contactComp, my_patient.getContactInfo().getCell_phone());
		mobileText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		mobileText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getContactInfo().setCell_phone(mobileText.getText());
			}
		});
		
		// Business Phone
		getFormToolkit().createLabel(contactComp, "Business: ");
		final Text businessText = getFormToolkit().createText(contactComp, my_patient.getContactInfo().getWork_phone());
		businessText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		businessText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getContactInfo().setWork_phone(businessText.getText());
			}
		});
		
		// Fax
		getFormToolkit().createLabel(contactComp, "Fax: ");
		final Text faxText = getFormToolkit().createText(contactComp, my_patient.getContactInfo().getFax());
		faxText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		faxText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				my_patient.getContactInfo().setFax(faxText.getText());
			}
		});
		
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				my_form.reflow(true);
			}
		});
		section.pack();
	}
	
	
	/**
	 * Paints the gradient on the header text background.
	 */
	private void paintHeaderBackground () {
		getFormToolkit().decorateFormHeading(my_form.getForm());
//		FormColors colors = getFormToolkit().getColors();
//		Color top = colors.getColor(IFormColors.H_GRADIENT_END);
//		Color bot = colors.getColor(IFormColors.H_GRADIENT_START);
//		my_form.getForm().setTextBackground(new Color [] {top, bot}, new int [] {100}, true);
	}
	
	
	/**
     * Asks this part to take focus within the workbench. Parts must
     * assign focus to one of the controls contained in the part's
     * parent composite.
     */
	@Override
	public void setFocus() {
		
	}

	
	/**
	 * Disposes the workbench recourses, fonts, images, etc.&nbsp;
	 * Also, unregister any listeners from the workbench.
	 */
	@Override
	public void dispose() {
		
	}
	
	
	/**
	 * Returns the patient being displayed in this editor.
	 * 
	 * @return The patient displayed in this editor.
	 */
	public Patient getPatient () {
		return my_patient;
	}


	/**
	 * Property change callback.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		
		if (e.getPropertyName().toString().equals("my_firstName")) {
			getTabItem().setText(my_patient.getLastName() + ", " + e.getNewValue() + " - Profile");
			getTabItem().setToolTipText(my_patient.getLastName() + ", " + e.getNewValue() + " - Profile");
		}
			
		else if (e.getPropertyName().toString().equals("my_lastName")) {
			getTabItem().setText(e.getNewValue() + ", " + my_patient.getFirstName() + " - Profile");
			getTabItem().setToolTipText(e.getNewValue() + ", " + my_patient.getFirstName() + " - Profile");
	
		}
			
	}
}
