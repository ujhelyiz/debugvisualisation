package hu.cubussapiens.zestlayouts.creators;

import hu.cubussapiens.zestlayouts.ILayoutAlgorithmCreator;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;

/**
 * Creator for HorizontalTreeLayoutAlgorithm
 * 
 */
public class HorizontalTreeLayoutCreator implements ILayoutAlgorithmCreator {

	/**
	 * {@inheritDoc}
	 */
	public LayoutAlgorithm create() {
		return new HorizontalTreeLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	}

}
