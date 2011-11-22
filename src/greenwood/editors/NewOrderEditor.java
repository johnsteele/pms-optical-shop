package greenwood.editors;

/**
 * @author John Steele
 * @file   NewOrderEditor.java
 * @date   07/22/2011
 * 
 * <p>A class for creating a new order editor.</p>
 */
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import greenwood.application.WorkbenchImages;
import greenwood.application.WorkbenchPage;
import greenwood.orders.model.Order;
import greenwood.orders.model.StoreAccount;
import greenwood.patients.model.Patient;
import greenwood.providers.StoreAccountLabelProvider;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import sun.awt.HorizBagLayout;

/**
 * <p>A class to represent a new order editor.</p>
 */
public class NewOrderEditor extends EditorPart {
	
	/**
	 * The tab item name and tool-tip.
	 */
	private static final String TAB_NAME = "New order";
	private static final String PATIENT_SEC_DESC = "<form><p>Create a <b>new</b> patient,  or <b>search</b> for an existing one.</p></form>";
	
	
	/**
	 * The order for this editor.
	 */
	private Order my_order;
	
	/**
	 * The patient making the order.
	 */
	private Patient my_patient;

	/**
	 * The editor's form.
	 */
	private ScrolledForm my_form;
	
	/**
	 * Save button action.
	 */
	private Action save_button;
	
	/**
	 * Change support for UI widgets.
	 */
	private PropertyChangeSupport my_propertyChangeSupport = new PropertyChangeSupport(this);
	
	
	/**
	 * Creates a NewOrderEditor with the specified values.
	 * 
	 * @param the_patient The patient who's making the order.
	 * @param the_page The page owning this editor.
	 * @param the_folder The folder owning this editors' tab item.
	 * @param the_toolkit The shared form toolkit factory.
	 */
	public NewOrderEditor(Patient the_patient, WorkbenchPage the_page, 
			CTabFolder the_folder, FormToolkit the_toolkit) {
		
		super(the_page, the_folder, the_toolkit);
		my_patient = the_patient;	
		if (my_patient == null) {
			my_patient = new Patient();
		}
		my_order = new Order();
		my_patient.getOrders().add(my_order);
		
		getTabItem().setText(TAB_NAME);
		getTabItem().setToolTipText(TAB_NAME);
		getTabItem().setShowClose(true);
		my_isDirtyPatient = false;
	}


	/**
	 * Creates the UI controls for this editor.
	 */
	@Override
	public void createPartControl() {
		GridLayout layout   = new GridLayout();
		layout.marginWidth  = 0;
		layout.marginHeight = 0;
		super.getComposite().setLayout(layout);
		super.getComposite().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		my_form = getFormToolkit().createScrolledForm(super.getComposite());
		my_form.setLayout(new GridLayout());
		my_form.setLayoutData(new GridData(GridData.FILL_BOTH));
		my_form.setText("New Order");
		my_form.setBackgroundImage(WorkbenchImages.getImageRegistry().get("banner"));
		
		/* Patient and Store info columns. */
		layout = new GridLayout(2, true);
		my_form.getBody().setLayout(layout);
		
		save_button = new Action() {
			@Override
			public void run() {
				System.out.println("Performing save...");
				setToolTipText("Save changes");
			}
		};
		try {
			save_button.setImageDescriptor(ImageDescriptor.createFromURL(new URL("file:images/Save.gif")));
		} catch (MalformedURLException e) {
			throw new RuntimeException("Malformed URL - " + "file:images/Save");
		}
		save_button.setEnabled(false);
		
		my_form.getToolBarManager().add(save_button);
		my_form.getToolBarManager().update(true);
		
		/* Patient section. */
		Section patientSection = setupPatientSection();
		
		/* Store account section. */
		Section accountSection = setupAccountSection ();
		
		accountSection.descriptionVerticalSpacing = patientSection.getTextClientHeightDifference();
		
		/* Order type. */
		Section lensSection = setupLensSection ();
		
		// This call is needed because the section will compute
		// the bold version based on the parent.
		Dialog.applyDialogFont(my_form.getBody());		
	}
	
	
	/**
	 * Creates the top section (i.e., the store and patient information section).
	 * 
	 * @param section The section to setup.
	 * @return The patient section.
	 */
	private Section setupPatientSection () {
		
		Section section = getFormToolkit().createSection(my_form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		//section.setDescription(PATIENT_SEC_DESC);
		//FormText description = getFormToolkit().createFormText(my_form, false);
		//description.setText("", true, false); 	
		//section.setDescriptionControl(description);
	
		GridData data1 = new GridData (GridData.FILL_BOTH);
		section.setLayoutData(data1);
		section.setText("Patient");
		
		/* Client composite */
		Composite client = getFormToolkit().createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout ();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		client.setLayout(layout);
		getFormToolkit().paintBordersFor(client);
		
		/* The patient information composite */
		Composite patientComp = getFormToolkit().createComposite(client, SWT.WRAP);
		patientComp.setLayout(new GridLayout(2, false));
		patientComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		getFormToolkit().paintBordersFor(patientComp);
		
		// last name 
		Label lastName = getFormToolkit().createLabel(patientComp, "Last Name:");
		lastName.setLayoutData(new GridData (GridData.VERTICAL_ALIGN_BEGINNING));
	
		Text  lastText = getFormToolkit().createText(patientComp, "");
		lastText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		lastText.setEnabled(false);
		
		// first name
		Label firstName = getFormToolkit().createLabel(patientComp, "First Name:");
		Text  firstText = getFormToolkit().createText(patientComp, "");
		firstText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		firstText.setEnabled(false);
		
		/* The button Column. */
		Composite buttonComp = getFormToolkit().createComposite(client);
		buttonComp.setLayout(new GridLayout(1, false));
		buttonComp.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		Button search = getFormToolkit().createButton(buttonComp, "Search", SWT.PUSH);
		search.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Button newPatient = getFormToolkit().createButton(buttonComp, "New", SWT.PUSH);
		newPatient.setLayoutData(new GridData (GridData.FILL_HORIZONTAL));
		
		
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				my_form.reflow(false);
			}
		});
		
		section.setClient(client);
		return section;
	}
	
	
	/**
	 * Creates the store account section.
	 * 
	 * @return The store section.
	 */
	private Section setupAccountSection () {
		Section section = getFormToolkit().createSection(my_form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		GridData data1 = new GridData (GridData.FILL_BOTH);
		section.setLayoutData(data1);
		section.setText("Store Account");
		
		/* Client composite. */
		Composite client = getFormToolkit().createComposite(section,  SWT.WRAP);
		client.setLayout(new GridLayout(2, false));
		getFormToolkit().paintBordersFor(client);
		
		/* Account selection tree - Left column */
//		Composite comp = getFormToolkit().createComposite(client, SWT.BORDER | SWT.WRAP);
//		comp.setLayout(new GridLayout (1, false));
//		comp.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, true));
		
		// Table for viewer.
		Table table = getFormToolkit().createTable(client, SWT.H_SCROLL | SWT.V_SCROLL | SWT.CHECK | SWT.SINGLE);
		GridData data = new GridData (GridData.FILL_BOTH);
		data.widthHint = 20;
		table.setLayoutData(data);
		
		// The table viewer.
		final TableViewer viewer = new TableViewer(table);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new StoreAccountLabelProvider());
		List<StoreAccount> input = new ArrayList<StoreAccount>();
		input.add(new StoreAccount(3351, "UW Optical", "1342 North 85th", "Seattle", "WA", 98105)); 
		input.add(new StoreAccount(4358, "UW Optical", "5519 Sounth Brooklyn", "Seattle", "WA", 90815));
		input.add(new StoreAccount(1639, "UW Optical", "44 West Madison", "Bellevue", "WA", 99025));
		input.add(new StoreAccount(1639, "UW Optical", "25 East Olympic", "Olympia", "WA", 98025));
		viewer.setInput(input);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (!viewer.getSelection().isEmpty()) {
					IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
					StoreAccount account = (StoreAccount)selection.getFirstElement();
					System.out.println(account.toString());
				}
			}
		});
		
		// TODO: Figure out to only allow one check selected.
		
		/* Button column - Right column */
		Composite comp = getFormToolkit().createComposite(client, SWT.WRAP);
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		comp.setLayout(layout);
		comp.setLayoutData(new GridData (GridData.VERTICAL_ALIGN_BEGINNING));
		
		// Search account button.
		Button searchButton = getFormToolkit().createButton(comp, "Search", SWT.PUSH);
		searchButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING));
		
		// New account button.
		Button newButton = getFormToolkit().createButton(comp, "New", SWT.PUSH);
		newButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		section.setClient(client);
		
		
		return section;
	}
	
	
	/**
	 * Creates the lens section.
	 * 
	 * @return The lens section.
	 */
	private Section setupLensSection () {
			
		Section section = getFormToolkit().createSection(my_form.getBody(), Section.TITLE_BAR);
		section.setActiveToggleColor(getFormToolkit().getHyperlinkGroup().getActiveBackground());
		section.setToggleColor(getFormToolkit().getColors().getColor(IFormColors.SEPARATOR));
		section.setText("Order Type");
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 2;
		section.setLayoutData(data);
		
		/* Client composite. */
		Composite client = getFormToolkit().createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout (2, false));

		
		/* Job type */		
		// Uncut or Finished.
		getFormToolkit().createLabel(client, "Job Type: ");
		Composite typeGroup = getFormToolkit().createComposite(client, SWT.WRAP);
		typeGroup.setLayout(new GridLayout (4, false));		
		Button unCutButton = getFormToolkit().createButton(typeGroup, "Uncut", SWT.RADIO);
		Button cutButton   = getFormToolkit().createButton(typeGroup, "Finished", SWT.RADIO);
		typeGroup.pack();
		getFormToolkit().paintBordersFor(typeGroup);
		
		// Lens
		getFormToolkit().createLabel(client, "Lens: ");
		Composite lensGroup = getFormToolkit().createComposite(client, SWT.SHADOW_NONE);
		lensGroup.setLayout(new GridLayout (4, false));
		Button bothLensButton = getFormToolkit().createButton(lensGroup, "Both Lenses", SWT.RADIO);
		Button rightLensButton = getFormToolkit().createButton(lensGroup, "Right Lens", SWT.RADIO);
		Button leftLensButton  = getFormToolkit().createButton(lensGroup, "Left Lens", SWT.RADIO);
		lensGroup.pack();
		getFormToolkit().paintBordersFor(lensGroup);
		
		/* Design */
		getFormToolkit().createLabel(client, "Brand: ");
		Composite brandGroup = getFormToolkit().createComposite(client, SWT.SHADOW_NONE);
		brandGroup.setLayout(new GridLayout (4, false));
		Combo brand = new Combo (brandGroup, SWT.POP_UP | SWT.READ_ONLY);
		brand.add("Indo");
		brand.add("Digital 5.0");
		brand.add("Seiko");
		brand.add("Shamir");
		brand.select(0);
		brandGroup.pack();
		getFormToolkit().paintBordersFor(brandGroup);
		
		/* Lense design */
		getFormToolkit().createLabel(client, "Design: ");
		Composite designGroup = getFormToolkit().createComposite(client, SWT.SHADOW_NONE);
		designGroup.setLayout(new GridLayout (4, false));
		Combo design = new Combo(designGroup, SWT.POP_UP | SWT.READ_ONLY);
		design.add("LifeMade Drive (Progressive)");
		design.add("LifeMade Expert (Progressive)");
		design.add("LifeMade Inicia (Progressive)");
		design.add("LifeMade Work (Progressive)");
		design.select(0);
		designGroup.pack();
		getFormToolkit().paintBordersFor(designGroup);
		
		/* Options */
		getFormToolkit().createLabel(client, "Options: ");
		Composite optionGroup = getFormToolkit().createComposite(client, SWT.SHADOW_NONE);
		optionGroup.setLayout(new GridLayout (4, false));
		Button clearButton = getFormToolkit().createButton(optionGroup, "Clear", SWT.RADIO);
		Button polorizedButton = getFormToolkit().createButton(optionGroup, "Polorized", SWT.RADIO);
		Button transistionsButton = getFormToolkit().createButton(optionGroup, "Transitions", SWT.RADIO);
		optionGroup.pack();
		getFormToolkit().paintBordersFor(optionGroup);
		
		/* Coating */
		getFormToolkit().createLabel(client, "Coating: ");
		Composite coatGroup = getFormToolkit().createComposite(client, SWT.SHADOW_NONE);
		coatGroup.setLayout(new GridLayout (4, false));
		Button arCoating = getFormToolkit().createButton(coatGroup, "AR coating", SWT.RADIO);
		Button noArCoating = getFormToolkit().createButton(coatGroup, "No AR Coating", SWT.RADIO);
		coatGroup.pack();
		getFormToolkit().paintBordersFor(coatGroup);
		
		/* Mirror */
		getFormToolkit().createLabel(client, "Mirror Coating: ");
		Composite mirrorGroup = getFormToolkit().createComposite(client, SWT.SHADOW_NONE | SWT.NONE);
		mirrorGroup.setLayout(new GridLayout (4, false));
		Button yesMirrorCoating = getFormToolkit().createButton(mirrorGroup, "Yes", SWT.RADIO);
		Button noMirrorCoating  = getFormToolkit().createButton(mirrorGroup, "No", SWT.RADIO);
		getFormToolkit().paintBordersFor(mirrorGroup);
		
		getFormToolkit().paintBordersFor(client);
		section.setClient(client);
		section.pack();
		return section;
	}


	@Override
	public void setFocus() {
	}

	@Override
	public void dispose() {
	}


	/**
	 * Property change callback.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String propertyName = event.getPropertyName();
		if (propertyName.equalsIgnoreCase("my_isDitryPatient")) {
			//if (my_isDirtyPatient)
				//my_saveButton.setEnabled(true);
		}
	}

}
