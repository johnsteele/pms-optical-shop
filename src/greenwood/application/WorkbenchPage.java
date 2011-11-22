/**
 * @author John Steele
 * @version 06/20/2011
 * @file WorkbenchPage.java
 * 
 * <p>
 * A class to represent a workbench page, which is responsible
 * for managing the views and editors.
 * </p>
 */
package greenwood.application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import greenwood.editors.EditorPart;
import greenwood.editors.IEditorPart;
import greenwood.editors.NewOrderEditor;
import greenwood.editors.OrderEditor;
import greenwood.editors.PatientEditor;
import greenwood.patients.model.Patient;
import greenwood.views.IViewPart;
import greenwood.views.OrdersNavView;
import greenwood.views.PatientNavView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * Manages the collection of parts (views and editors), and 
 * essentially serves as the controller for those parts.
 */
public class WorkbenchPage {
	
	/**
	 * The parent container for the sash form.
	 */
	private Composite my_composite;
	
	/**
	 * The container for the patient navigation view and the patient editor.
	 */
	private SashForm my_sash;
	
	/**
	 * The patient navigation view. 
	 */
	private PatientNavView my_patientView;
	
	/**
	 * The orders view.
	 */
	private OrdersNavView my_ordersView;
	
	/**
	 * The patient editor.
	 */
	private PatientEditor my_patientEditor;
	
	/**
	 * The shared factory for all views and editors.
	 */
	private FormToolkit my_toolkit;
	
	/**
	 * The views' tab folder. Each view is a tab within this folder.
	 */
	private CTabFolder my_navTabFolder;
	
	/**
	 * The editors' tab folder. Each editor is a tab within this folder.
	 */
	private CTabFolder my_editorTabFolder;
	
	/**
	 * The active navigation view folder tab.
	 */
	private CTabItem my_activeNavTab;
	
	/**
	 * The list of opened editors.
	 */
	private List<EditorPart> my_openEditors;
	
	/**
	 * The list of patients open in editors.
	 */
	private List<PatientEditor> my_openPatientEditors;
	
	/**
	 * The list of open NewOrderEditor's.
	 */
	private List<NewOrderEditor> my_openNewOrderEditors;
	
	/**
	 * The list of open patient-order editors.
	 */
	private List<OrderEditor> my_openOrderEditors;
	
	/**
	 * The active editor part.
	 */
	private IEditorPart my_activeEditor;
	
	/**
	 * The active navigation view.
	 */
	private IViewPart my_activeView;
	
	
	/**
	 * Creates a WorkbenchPage with the specified shell. 
	 * 
	 * @param the_shell The top-level shell.
	 */
	public WorkbenchPage (Composite the_parent) {
		
		my_activeNavTab    = null;
		my_activeEditor    = null;
		my_activeView      = null;
		
		my_openNewOrderEditors = new ArrayList<NewOrderEditor>();
		my_openEditors         = new ArrayList<EditorPart>();
		my_openPatientEditors  = new ArrayList<PatientEditor>();
		my_openOrderEditors    = new ArrayList<OrderEditor>();
		
		my_composite = new Composite(the_parent, SWT.NONE);
		my_composite.setLayout(new FillLayout());
		my_composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		my_sash    = new SashForm(my_composite, SWT.HORIZONTAL);
		my_toolkit = new FormToolkit (the_parent.getDisplay());
		
		initNavFolder(the_parent);
		initEditorFolder(the_parent);
	}
	
	
	/**
	 * If there was a previous session, it's state is 
	 * restored.
	 */
	private void loadPreviousSession () {
		my_navTabFolder.setSelection(my_patientView.getTabItem());
	}
	
	
	/**
	 * Initializes the left side navigation tab folder.
	 */
	private void initNavFolder (Composite the_parent) {
		my_navTabFolder = new CTabFolder(my_sash, SWT.BORDER);
		my_navTabFolder.setSimple(false);
		my_navTabFolder.setLayout(new GridLayout(1, false));
		my_navTabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		my_navTabFolder.setSelectionBackground(the_parent.getShell().getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
	}
	
	
	/**
	 * Initializes the right side editor tab folder.
	 * 
	 * @param the_parent The parent composite.
	 */
	private void initEditorFolder (Composite the_parent) {
		my_editorTabFolder = new CTabFolder(my_sash, SWT.BORDER);
		my_editorTabFolder.setSimple(false);
		my_editorTabFolder.setLayout(new GridLayout(1, false));
		my_editorTabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		my_editorTabFolder.setSelectionBackground(the_parent.getShell().getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
	}

	
	/**
	 * Opens this workbench page.
	 */
	public void open () {
		createLeftPanel();
		createRightPanel();
		loadPreviousSession();
	}
	
	
	/**
	 * Creates the left side of the sash form (i.e., the navigation views).
	 */
	private void createLeftPanel () {
		createPatientNavView();
		createOrdersView();
	}
	
	
	/**
	 * Creates the right side of the sash form. (i.e., the editors).
	 */
	private void createRightPanel () {
		createEditorArea();
	}
	
	
	/**
	 * Creates right side editor area.
	 */
	private void createEditorArea () {
		
	}
	
	/**
	 * Creates the patient navigation view. 
	 */
	private void createPatientNavView () {
		my_patientView = new PatientNavView(this, my_navTabFolder, my_toolkit);
		my_patientView.createPartControl();		
	}

	
	/**
	 * Creates the orders view. 
	 */
	private void createOrdersView () {
		my_ordersView = new OrdersNavView(this, my_navTabFolder, my_toolkit);
		my_ordersView.createPartControl();
	}
	
	
	/**
	 * Positions the workbench parts based off of last session
	 * or default if no session has been saved. 
	 */
	private void positionParts () {
		//my_sash.setWeights(new int [] {800, 800});
	}
	
	
	/**
	 * Returns the patient editor.
	 * 
	 * @return The patient editor.
	 */
	public PatientEditor getPatientEditor () {
		return my_patientEditor;
	}
	
	
	/**
	 * Returns the patient navigation view.
	 * 
	 * @return The patient navigation view.
	 */
	public PatientNavView getPatientNavView () {
		return my_patientView;
	}
	
	
	/**
	 * Opens an editor with the provided patient as it's
	 * input. If there's an editor already open with the
	 * patient that editor is given focus.
	 * 
	 * @param the_patient The patient used as the input to the editor.
	 */
	public void openPatientEditor (Patient the_patient) {

		PatientEditor editor;
		
		// If the patient is already open in an editor, just set focus to that editor.
		if ((editor = isPatientEditorOpen(the_patient)) != null) {
			my_editorTabFolder.setSelection(editor.getTabItem());
		}
		
		// Otherwise, open a new editor with the patient.
		else {
			editor = new PatientEditor(the_patient, this, my_editorTabFolder, my_toolkit);
			my_openPatientEditors.add(editor);
			editor.createPartControl();
			setActiveEditor (editor);
			addTabListeners (editor);
		}

	}
	
	
	/**
	 * Opens the new order editor.
	 */
	public void openNewOrderEditor () {
		final NewOrderEditor editor = new NewOrderEditor(null, this, my_editorTabFolder, my_toolkit);
		editor.getTabItem().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				my_openNewOrderEditors.remove(editor);
			}
		});
		editor.createPartControl();
		setActiveEditor(editor);
		addTabListeners(editor);
		my_openNewOrderEditors.add(editor);
	}
	
	
	/**
	 * Opens the order editor with the provided order as it's 
	 * input. If there's already an order editor open, it's input
	 * is set to the new patient and the editor is refreshed.
	 * 
	 * @param the_patient The patient to open.
	 */
	public void openOrderEditor (Patient the_patient) {
		OrderEditor e;
		
		// First see if the patient is already open in an OrderEditor.
		if ((e = isOrderEditorOpen(the_patient)) != null) {
			my_editorTabFolder.setSelection(e.getTabItem());
		}
		
		// Otherwise, create a new editor.
		else {
			OrderEditor editor = new OrderEditor(this, my_editorTabFolder, my_toolkit, the_patient);
			editor.createPartControl();
			setActiveEditor(editor);
			addTabListeners(editor);
			my_openOrderEditors.add(editor);
		}
	}
	
	
	/**
	 * Closes all the tab items, which contains the provided patient. 
	 * Callers of this method would typically do so because the patient
	 * was deleted.
	 * 
	 * @param the_patient The patient 
	 */
	public void closePatientTabs (Patient the_patient) {
		PatientEditor editor;
		if ((editor = isPatientEditorOpen(the_patient)) != null) {
			editor.getTabItem().dispose();
		}
		
		OrderEditor oEditor;
		if ((oEditor = isOrderEditorOpen(the_patient)) != null) {
			oEditor.getTabItem().dispose();
		}
	}
	
	
	/**
	 * Adds a close tab listener to the provided editor's tab item, so that
	 * when it's closed it will be removed from the list of open editors.
	 * 
	 * @param the_editor The editor part to add the listener to.
	 */
	private void addTabListeners (final EditorPart the_editor) {
		
			the_editor.getTabItem().addDisposeListener(new DisposeListener() {	
				@Override
				public void widgetDisposed(DisposeEvent e) {
					
					if (the_editor instanceof PatientEditor) {
						my_openPatientEditors.remove(the_editor);
					}
					
					else if (the_editor instanceof OrderEditor) {
						my_openOrderEditors.remove(the_editor);
					}
					
					else if (the_editor instanceof NewOrderEditor) {
						my_openNewOrderEditors.remove(the_editor);
					}
				}
			});
		
		// TODO: Add listener for tab item so that the corresponding patient/order
		// is selected within the navigation view.
	}
	
	
	/**
	 * Checks if the provided patient is already open in another patient editor. If so,
	 * a reference to that editor is returned. Null otherwise.
	 * 
	 * @param the_patient The patient to check.
	 * @return The editor with the patient already open in, null otherwise.
	 */
	private PatientEditor isPatientEditorOpen (Patient the_patient) {
		
		// Check if already open.
		Iterator<PatientEditor> iter = my_openPatientEditors.iterator();
		while (iter.hasNext()) {
			PatientEditor editor = (PatientEditor) iter.next();
			Patient p = editor.getPatient();
			if (p.equals(the_patient)) return editor;
		}
		
		// Not already open.
		return null;
	}
	
	
	/**
	 * Checks if the provided patient is already open in another order editor. 
	 * If so, a reference to that editor is returned, <code>null</code> otherwise.
	 * 
	 * @param the_patient The patient to check.
	 * @return The order editor if already open, null otherwise.
	 */
	private OrderEditor isOrderEditorOpen (Patient the_patient) {
		
		Iterator<OrderEditor> iter = my_openOrderEditors.iterator();
		while (iter.hasNext()) {
			OrderEditor e = iter.next();
			Patient p = e.getPatient();
			if (p.equals(the_patient)) return e;
		}
		
		// Not already open.
		return null;
	}
	
	
	/**
	 * Sets the provided editor to the active editor.
	 * 
	 * @param the_editor The editor to set focus to.
	 */
	private void setActiveEditor (EditorPart the_editor) {
		my_editorTabFolder.setSelection(the_editor.getTabItem());
	}
}
