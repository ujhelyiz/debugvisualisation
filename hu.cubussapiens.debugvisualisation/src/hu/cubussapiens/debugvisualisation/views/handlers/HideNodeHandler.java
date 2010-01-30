/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.handlers;

import hu.cubussapiens.debugvisualisation.internal.api.IHiddenNodes;
import hu.cubussapiens.debugvisualisation.internal.api.IRootControl;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVVariable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 *
 */
public class HideNodeHandler extends AbstractGraphCommandHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IStructuredSelection ss = (IStructuredSelection)HandlerUtil.getCurrentSelection(event);
		
		List<IDVValue> hidevalues = new ArrayList<IDVValue>();
		List<IDVValue> removeroot = new ArrayList<IDVValue>();
		
		Iterator<?> i = ss.iterator();
		while (i.hasNext()) {
			Object o = i.next();
			if (o instanceof IDVVariable) {
				o = ((IDVVariable) o).getValue();
			}

			if (o instanceof IDVValue) {
				IDVValue v = (IDVValue) o;
				if (v.isLocalContext())
					removeroot.add(v);
				else
					hidevalues.add(v);
			}
		}
		
		if (!hidevalues.isEmpty()) {
			IHiddenNodes hidden = (IHiddenNodes) getInput().getAdapter(
					IHiddenNodes.class);
			hidden.hideNodes(hidevalues);
		}

		if (!removeroot.isEmpty()) {
			IRootControl rootc = (IRootControl) getInput().getAdapter(
					IRootControl.class);
			rootc.removeRoots(removeroot);
		}

		return null;
	}

}
