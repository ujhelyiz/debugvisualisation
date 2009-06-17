/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Action which opens or closes the selected nodes on a graph
 *
 */
public class ToggleOpenAction extends GraphAction {
	
	/**
	 * 
	 */
	public ToggleOpenAction(GraphViewer viewer) {
		super(viewer);
		setText("Open/Close");
		setToolTipText("Opens or closes selected nodes");
	}

	@Override
	public void run() {
		IStructuredSelection sel = getSelection();
		IDebugContextInput input = getInput();
		
		for(Object i : sel.toArray()){
			if (i instanceof Integer)
				input.toggleOpen((Integer)i);
		}
	}

}
