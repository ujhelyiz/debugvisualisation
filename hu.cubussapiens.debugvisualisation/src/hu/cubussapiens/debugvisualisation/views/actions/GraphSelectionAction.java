/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Defines an action which should be executed on all of the selected nodes
 * in the graph one by one.
 */
public abstract class GraphSelectionAction extends GraphAction {

	/**
	 * @param viewer
	 */
	public GraphSelectionAction(GraphViewer viewer) {
		super(viewer);
	}

	@Override
	public final void run() {
		IStructuredSelection sel = getSelection();
		IDebugContextInput input = getInput();
		
		for(Object i : sel.toArray()){
			if (i instanceof Integer)
				run((Integer)i,input);
		}
	}
	
	protected abstract void run(Integer node,IDebugContextInput input); 
	
}