/**
 * @author John Steele
 * @version 06/26/2011
 * @file IWorkbenchPart.java
 * 
 * <p>
 * An interface to represent a workbench part.  
 * </p>
 */
package greenwood.ui.internal;

/**
 * A workbench part is a visual component within a workbench page. 
 * There are two sub-types: view and editor, as defined by
 * <code>IViewPart</code> and <code>IEditorPart</code> 
 * 
 * <p>A view is typically used to navigate a hierarchy of information, 
 * open and editor, or display properties for the active editor.
 * Modifications made in a view are saved automatically.
 * </p><p>
 * An editor is typically used to edit or browse a document or input object.
 * The input is identified by using an <code>IEditorInput</code>.
 * Modifications made in an editor part follow open-save-close life cycle.
 * </p><p>
 * This interface may be implemented directly; however, for convenience, a 
 * base implementation <code>WorkbenchPart</code> has been defined.
 * </p>
 * <p>
 * The life cycle of a workbench part is as follows:
 * <ul>
 * 	<li> The part is instantiated and becomes visible in the workbench.
 * 		<ul>
 * 			<li>Add part to presentation by calling
 * 				<code>part.createPartControl(parent)</code> to create actual widgets
 * 			</li>
 * 			<li>File a <code>partOpened</code> event to all listeners.
 * 			</li>
 * 		</ul>
 * 	</li>
 * 	<li> When the part is activated or gets focus.
 * 		<ul>
 * 			<li>Call <code>part.setFocus()</code></li>
 * 			<li>Fire <code>partActivated()</code> to all listeners.</li>
 * 		</ul>
 * 	</li>
 * <li> When the part is closed.
 * 		<ul>
 * 			<li>If save is needed, do save; if it fails or canceled return.</li>
 * 			<li>If part is active, decativate it.</li>
 * 			<li>Remove part from presentation; part controls are disposed as
 * 			    part of the SWT widget tree. 
 * 	 		</li>
 * 			<li>Call <code>part.dispose()</code>.
 * 		</ul>
 * </li>
 * </ul>
 */
public interface IWorkbenchPart {	
	
    /**
     * Asks this part to take focus within the workbench. Parts must
     * assign focus to one of the controls contained in the part's
     * parent composite.
     * <p>
     */
	public void setFocus ();
	
	/**
	 * Creates the SWT controls for this workbench part. 
	 */
	public void createPartControl ();
	
	/**
	 * Disposes the workbench recourses, fonts, images, etc.&nbsp;
	 * Also, unregister any listeners from the workbench.
	 */
	public void dispose ();
}
