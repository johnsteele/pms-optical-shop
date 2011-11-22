/**
 * @author John Steele
 * @version 07/13/2011
 * @file OrdersView.java
 * 
 * <p>
 * A class to represent a order view. 
 * </p>
 */

package greenwood.views;

import java.beans.PropertyChangeEvent;
import java.util.Iterator;
import java.util.List;

import greenwood.application.Workbench;
import greenwood.application.WorkbenchImages;
import greenwood.application.WorkbenchPage;
import greenwood.patients.model.Patient;
import greenwood.providers.OrdersNavContentProver;
import greenwood.providers.OrdersNavLabelProvider;
import greenwood.views.filters.OrderNavViewFilter;
import greenwood.views.sorters.OrderNavViewComparator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * OrdersView displays the all the patient orders. 
 */
public class OrdersNavView extends ViewPart {
	
	/**
	 * The name to be displayed in the tab item.
	 */
	private static final String TAB_TITLE = "Orders";
	private static final String TAB_TOOLTIP = "Search Orders";
	private static final String INITIAL_FILTER_TEXT = "type filter text";
	
	/**
	 * The tab item for this view.
	 */
	private CTabItem my_tabItem;
	
	/**
	 * The viewer with all the orders.
	 */
	private TreeViewer my_orderViewer;
	
	/**
	 * The filtered text box.
	 */
	private Text my_filterText;
	
	/**
	 * The viewer's filter.
	 */
	private OrderNavViewFilter my_viewerFilter;
	
	
	/**
	 * Creates a OrderView with the specified values. 
	 * 
	 * @param the_page The page containing this view.
	 * @param the_folder The parent composite tab folder.
	 * @param the_toolkit The shared toolkit factory.
	 */
	public OrdersNavView(WorkbenchPage the_page, CTabFolder the_folder,
							FormToolkit the_toolkit) {
		super(the_page, the_folder, the_toolkit);
		
		getTabItem().setText(TAB_TITLE);
		getTabItem().setToolTipText(TAB_TOOLTIP);
		getTabItem().setImage(WorkbenchImages.getImageRegistry().get("order"));
		getTabItem().setShowClose(false);
		getTabItem().setControl(super.getComposite());
		
		my_viewerFilter = new OrderNavViewFilter();
	}

	
	/**
	 * Sets focus to the search text.
	 */
	@Override
	public void setFocus() {
		
	}

	
	/**
	 * Creates the controls for this view.
	 */
	@Override
	public void createPartControl() {
		
		GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		super.getComposite().setLayout(layout);
		
		
		createToolBar();
		
		Composite parent = new Composite(super.getComposite(), SWT.BORDER);
		parent.setBackground(super.getComposite().getParent().getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		
		GridLayout layout2 = new GridLayout();
		parent.setLayout(layout2);
		
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		createSearchText(parent);
		createOrderViewer (parent);
	}

	
	/**
	 * Creates the toolbar above the filtered tree.
	 */
	private void createToolBar () {
		
		ToolBar toolBar = new ToolBar(super.getComposite(), SWT.HORIZONTAL | SWT.FLAT);
		
		ToolItem item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(WorkbenchImages.getImageRegistry().get("add"));
		item.setToolTipText("New order");
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getWorkbenchPage().openNewOrderEditor();
			}
		});
		
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(WorkbenchImages.getImageRegistry().get("delete"));
		item.setToolTipText("Delete order");
		
		item = new ToolItem (toolBar, SWT.SEPARATOR);
	}
	
	
	/**
	 * Creates the search text.
	 */
	private void createSearchText (Composite the_parent) {
		my_filterText = new Text(the_parent, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		my_filterText.setLayoutData(data);
		
		my_filterText.setText(INITIAL_FILTER_TEXT);
		my_filterText.selectAll();
		
		// Delete the filter text once it gains focus.
		my_filterText.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				if (my_filterText.getText().equals(INITIAL_FILTER_TEXT)) {
					my_filterText.setText("");
				}
			}
	
			@Override
			public void focusLost(FocusEvent e) {
				if (my_filterText.getText().equals("")) {
					my_filterText.setText(INITIAL_FILTER_TEXT);
				}
			}
		});
		
		my_filterText.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {	// called b/f key released.	
								
				if (e.keyCode == SWT.ARROW_DOWN) {
					
					if (my_orderViewer.getTree().getItemCount() > 0) {
						Tree tree = my_orderViewer.getTree();
						
						// If the first item has children, select the first child.
						TreeItem item = tree.getItem(0);						
						boolean setChild = false;
						if (item.getItemCount() > 0) {
							// Only select the child if the child tree is expanded.
							if (item.getExpanded()) {
								tree.setSelection(item.getItems()[0]);
								setChild = true;
							}
							
							// We didn't select a child.
							else {
								if (tree.getItemCount() > 1) {
									tree.setSelection(tree.getItem(1));
								}
							}
						}					
					}
					my_orderViewer.getTree().setFocus();
				}
				
				if (e.keyCode == SWT.CR) {
					if (!my_orderViewer.getSelection().isEmpty()) {
						IStructuredSelection selection = (IStructuredSelection) my_orderViewer.getSelection();
						if (selection.getFirstElement() instanceof Patient) {
							Patient p = (Patient) selection.getFirstElement();
							getWorkbenchPage().openOrderEditor(p);
						}
					}
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				my_viewerFilter.setSearchPattern(my_filterText.getText());
				my_orderViewer.refresh();
				if (my_orderViewer.getTree().getItemCount() > 0) {
					Tree tree = my_orderViewer.getTree();
					my_orderViewer.getTree().setSelection(tree.getItem(0));
				}
			}
		});
	}
	
	
	/**
	 * Creates the order tree viewer.
	 */
	private void createOrderViewer (Composite parent) {
		my_orderViewer = new TreeViewer(parent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		my_orderViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		my_orderViewer.setContentProvider(new OrdersNavContentProver());
		my_orderViewer.setLabelProvider(new OrdersNavLabelProvider());
		
		List<Patient> patients = Workbench.getInstance().getDatabase().getPatients();
		my_orderViewer.setInput(patients);
		
		my_orderViewer.setComparator(new OrderNavViewComparator());
		my_orderViewer.addFilter(my_viewerFilter);
		
		my_orderViewer.getTree().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					if (!my_orderViewer.getSelection().isEmpty()) {
						IStructuredSelection selection = (IStructuredSelection) my_orderViewer.getSelection();
						if (selection.getFirstElement() instanceof Patient) {
							Patient p = (Patient) selection.getFirstElement();
							getWorkbenchPage().openOrderEditor(p);
						}
					}
				}
			}
		});
		
		my_orderViewer.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if (!my_orderViewer.getSelection().isEmpty()) {
					IStructuredSelection selection = (IStructuredSelection) my_orderViewer.getSelection();
					Object obj = (Object) selection.getFirstElement();
					if (obj instanceof Patient) {
						getWorkbenchPage().openOrderEditor((Patient)obj);
					}
				}
			}
		});
	}
	
	
	/**
	 * Releases critical resources and does clean up.
	 */
	@Override
	public void dispose() {
		
	}


	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
