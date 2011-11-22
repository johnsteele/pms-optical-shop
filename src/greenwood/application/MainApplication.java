/**
 * @author John Steele
 * @version 06/20/2011
 * @file MainApplication.java
 * 
 * <p>
 * A class to represent the main application.
 * </p>
 */
package greenwood.application;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * <p>
 * MainApplication is responsible for starting the UI thread
 * and opening the application.
 * </p>
 */
public class MainApplication {
	
	/**
	 * The title of this application to display on top-most window.
	 */
	public static final String APPLICATION_TITLE = "Optical Shop";
	
	/**
	 * The applications workbench. 
	 */
	private Workbench my_workbench;

	/**
	 * Creates a MainApplication object with the specified values. 
	 */
	public MainApplication(Display the_display, Shell the_shell) {
		my_workbench = new Workbench (the_display, the_shell);
		
	}
	
	public static void main (String [] args) {
		
		Display display = new Display();
//		Shell parent = new Shell( display, SWT.RESIZE);
//		//parent.setLayout(new FillLayout());	
//		parent.setSize(400, 200);
//		parent.setLayout (new GridLayout (1, false));
//		parent.setLayoutData(new GridData (GridData.FILL_BOTH));
////		
////		Composite comp = new Composite (parent, SWT.BORDER | SWT.WRAP);
////		comp.setLayout (new GridLayout ());
////		comp.setLayoutData(new GridData (GridData.FILL_BOTH));
////		Button b = new Button(comp, SWT.PUSH);
////		b.setText("Testing 1, 2, 3, 4");
////		b.setLayoutData(new GridData (GridData.FILL_BOTH));
////		
//
//		Composite comp2 = new Composite(parent, SWT.BORDER );
//		comp2.setLayout (new GridLayout ());
//		comp2.setLayoutData(new GridData (GridData.FILL_BOTH));
//		
//		
//		FormToolkit formToolkit = new FormToolkit( parent.getDisplay() );
//		//Form form = new Form(comp2, SWT.None); //formToolkit.createForm(comp2); //( comp2, SWT.NONE);
//		ScrolledForm form = formToolkit.createScrolledForm(comp2);
//		
//		form.setLayout(new GridLayout ());
//		form.setLayoutData(new GridData (GridData.FILL_BOTH));
//
//		
//		
//		
//		form.getBody().setLayout(new GridLayout ());
//		form.getBody().setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, true));
////		GridData dd = new GridData ();
////		dd.grabExcessHorizontalSpace = true;
////		dd.grabExcessVerticalSpace = true;
////		dd.horizontalAlignment = SWT.FILL;
////		dd.verticalAlignment = SWT.FILL;
////		form.getBody().setLayoutData(dd);
//		
//		
//		
//		//form.setSize( 100, 100 );
//		form.setBackground( formToolkit.getColors().getBackground() );
//		//form.setExpandHorizontal( true );
//		//form.setExpandVertical( true );
//
//		
//		CTabFolder tabFolder = new CTabFolder( form.getBody(), SWT.BORDER );
//		
//		//tabFolder.setLayout(new GridLayout());
//		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		tabFolder.setSimple(false);
//		
//		Composite composite = new Composite( tabFolder, SWT.NONE );
//		composite.setLayout(new GridLayout ());
//		composite.setLayoutData (new GridData (GridData.FILL_BOTH));		
//		CTabItem item = new CTabItem( tabFolder, SWT.NONE );
//		item.setText( "Tab 1" );
//		item.setControl( composite );
//		new Button(composite, SWT.PUSH).setText("Tesing");
//		item = new CTabItem( tabFolder, SWT.NONE );
//		composite = new Composite( tabFolder, SWT.NONE );
//		composite.setLayout(new GridLayout ());
//		composite.setLayoutData (new GridData (GridData.FILL_BOTH));
//		item.setText( "Tab 2" );
//		new Button(composite, SWT.PUSH).setText("Tesing");
//		item.setControl(composite);
//		item = new CTabItem( tabFolder, SWT.NONE );
//		composite = new Composite( tabFolder, SWT.NONE );
//		composite.setLayout(new GridLayout ());
//		composite.setLayoutData (new GridData (GridData.FILL_BOTH));
//		item.setText( "Tab 3" );
//		new Button(composite, SWT.PUSH).setText("Tesing");
//		item.setControl(composite);
//		item = new CTabItem( tabFolder, SWT.NONE );
//		composite = new Composite( tabFolder, SWT.NONE );
//		composite.setLayout(new GridLayout ());
//		composite.setLayoutData (new GridData (GridData.FILL_BOTH));
//		item.setText( "Tab 4" );
//		new Button(composite, SWT.PUSH).setText("Tesing");
//		item.setControl(composite);
//		item = new CTabItem( tabFolder, SWT.NONE );
//		composite = new Composite( tabFolder, SWT.NONE );
//		composite.setLayout(new GridLayout ());
//		composite.setLayoutData (new GridData (GridData.FILL_BOTH));
//		item.setControl(composite);
//		item.setText( "Tab 2" );
//		new Button(composite, SWT.PUSH).setText("Tesing");
//		item = new CTabItem( tabFolder, SWT.NONE );
//		composite = new Composite( tabFolder, SWT.NONE );
//		composite.setLayout(new GridLayout ());
//		composite.setLayoutData (new GridData (GridData.FILL_BOTH));
//		item.setControl(composite);
//		item.setText( "Tab 3" );
//		new Button(composite, SWT.PUSH).setText("Tesing");
//		//tabFolder.setMinimizeVisible( true );
//		//tabFolder.setMaximizeVisible( true );
//
//		//parent.pack();
//		tabFolder.pack();
//		form.getBody().pack();
//		
//		parent.open();
//		
//		
//		while (!parent.isDisposed()) {
//			// Process next event, wait when non available.
//			if (!display.readAndDispatch()) display.sleep();
//		}
//		
		/* Display */
		//	Once an SWT application begins running, its Display class sorts through this queue
		//	using its readAndDispatch() method and msg field, which acts as a handle to the
		//	underlying OS message queue. If it finds anything relevant, it sends the event to
		//	its top-level Shell object, which determines which widget should receive the
		//	event.
		//Display display = new Display ();
		
	/* Realm - Needed for observable's to communicate. Conceptually, it can
	 * be looked at as a thread. 
	 */
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			
			@Override
			public void run() {
				
				Display display = Display.getCurrent();
				/* Shell */
				//	The Shell then sends the event to the widget that the user acted on, which
				//	transfers this information to an associated interface called a listener. One of the lis-
				//	tener’s methods performs the necessary processing or invokes another method to
				//	handle the user’s action, called an event handler.
				Shell shell = new Shell (display, SWT.SHELL_TRIM);
				shell.setImage(WorkbenchImages.getImageRegistry().get("greenwoodOptical"));
				
				MainApplication main = new MainApplication(display, shell);
				main.open (); 
				
				shell.pack();
				shell.open();
				
				/*
				 * The UI-Thread event loop.
				 */
				while (!shell.isDisposed()) {
					// Process next event, wait when non available.
					if (!display.readAndDispatch()) display.sleep();
				}
				
			}
		});
		
	
		
		display.dispose();
	}
	
	
	/**
	 * Creates and opens the application. 
	 */
	private void open () {
		my_workbench.open ();
	}
}
