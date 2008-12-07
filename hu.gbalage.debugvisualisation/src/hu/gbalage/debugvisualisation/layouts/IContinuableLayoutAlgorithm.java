/**
 * 
 */
package hu.gbalage.debugvisualisation.layouts;

import org.eclipse.zest.layouts.LayoutAlgorithm;

/**
 * A Layout algorithm can implement this interface if it can be
 * continued. It is recommended for slow algorithms, therefore they
 * can be executed in small steps.
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public interface IContinuableLayoutAlgorithm extends LayoutAlgorithm{

	/**
	 * Determines if the algorithm should still be recalled after the
	 * last run.
	 * @return true if the algorithm should be recalled again.
	 */
	public boolean needsRecall();
	
}