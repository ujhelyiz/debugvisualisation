package hu.cubussapiens.debugvisualisation.views;

import hu.cubussapiens.debugvisualisation.internal.DebugContextListener;
import hu.cubussapiens.debugvisualisation.internal.IStackFrameConsumer;
import hu.cubussapiens.debugvisualisation.internal.VariablesGraphContentProvider;
import hu.cubussapiens.debugvisualisation.internal.VariablesLabelProvider;
import hu.cubussapiens.debugvisualisation.internal.input.DebugContextInputFactory;
import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;
import hu.cubussapiens.debugvisualisation.views.actions.ToggleOpenAction;
import hu.cubussapiens.zestlayouts.LayoutManager;

import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;



public class DebugVisualisationView extends ViewPart implements IStackFrameConsumer {

	/**
	 * The viewer
	 */
	GraphViewer viewer;
	
	/**
	 * A layout manager which can provide layouts registered to the extension point
	 */
	LayoutManager layout = new LayoutManager();
	
	/**
	 * A factory to generate inputs from IStackFrames
	 */
	DebugContextInputFactory inputfactory = new DebugContextInputFactory();
	
	/**
	 * Listening to changes in debug context
	 */
	DebugContextListener listener = null;
	
	/**
	 * Label provider
	 */
	VariablesLabelProvider labelprovider = new VariablesLabelProvider();
	
	/**
	 * Content provider
	 */
	VariablesGraphContentProvider contentprovider = new VariablesGraphContentProvider();
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		viewer = new GraphViewer(parent,SWT.NONE);
		viewer.setLayoutAlgorithm(layout.getDefault());
		viewer.setLabelProvider(labelprovider);
		viewer.setContentProvider(contentprovider);
		viewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		
		MenuManager mm = new MenuManager();
		viewer.getGraphControl().setMenu(mm.createContextMenu(viewer.getGraphControl()));
		
		mm.add(new ToggleOpenAction(viewer));
		
		//getSite().setSelectionProvider(viewer);
		
		//TODO: the previously started debug context could retrieved by this line:
		//IAdaptable dc = DebugUITools.getDebugContext();
		//viewer.setInput(null);
		
		listener = new DebugContextListener(this);
		DebugUITools.getDebugContextManager().addDebugContextListener(listener);
	}

	/**
	 * A new stack frame is given when the debug context is changed
	 */
	public void setStackFrame(IStackFrame stackframe) {
		IDebugContextInput input = inputfactory.getInput(stackframe);
		labelprovider.setInput(input);
		//viewer.setInput(null);
		//viewer.refresh();
		viewer.setInput(input);
		System.out.println("Refreshing.. "+input.getVisibleNodes().size());
		//viewer.refresh();
	}
	
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (listener != null)
			DebugUITools.getDebugContextManager().removeDebugContextListener(listener);
	}
	
}