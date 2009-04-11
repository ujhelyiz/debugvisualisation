/**
 * 
 */
package hu.gbalage.debugvisualisation.model;

import hu.gbalage.debugvisualisation.ValueUtils;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 */
public class ObjectNode extends AbstractNode {

	protected final int ID;

	protected final Model model;

	protected IValue value;

	protected boolean visible = true;

	public ObjectNode(Model model, int ID, IValue value) {
		this.ID = ID;
		this.model = model;
		this.value = value;
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#changeValue(org.eclipse.debug.core.model.IValue)
	 */
	public void changeValue(IValue value) {
		if (this.value.equals(value)) return;
		this.value = value;
		if (open) {
			try {
				this.setVariables(value.getVariables());
			} catch (DebugException e) {
				e.printStackTrace();
			}
		}
		notifychange();
	}

	/**
	 * Processes the string value by trimming the middle of the string if the
	 * string is too long, and escapes \n sequences.
	 * @param string The string to process.
	 * @return The trimmed and escaped string.
	 */
	public String getProcessedValue(String string) {
		String newString = string;
		int length = newString.length();
		if (length > 20) {
			newString = string.substring(0, 8) + "..."
					+ string.substring(length - 9, length - 1);
		}
		return newString.replace("\n", "\\n");
	}

	public String getCaption() {
		String result = getVarName() + ": " + ValueUtils.getValueString(value);
		if (open) {
			try {
				IVariable[] vars = model.filtermanager.apply(value, null);

				for (IVariable v : vars) {
					int id = ValueUtils.getID(v.getValue());
					if (id == -1) {
						result += "\n"
								+ v.getName()
								+ " = "
								+ getProcessedValue(v.getValue()
										.getValueString());
					}
				}
			} catch (DebugException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#getID()
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#getType()
	 */
	public String getType() {
		try {
			return value.getReferenceTypeName();
		} catch (DebugException e) {
			e.printStackTrace();
			return "Error!";
		}
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#isUnique()
	 */
	public boolean isUnique() {
		return true;
	}

	public void refreshVariables() {
		setVariables(cachedvars);
	}

	private IVariable[] cachedvars = new IVariable[0];

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#setVariables(org.eclipse.debug.core.model.IVariable[])
	 */
	public void setVariables(IVariable[] variables) {
		IVariable[] vars = variables;
		cachedvars = vars;
		try {
			if (open) {
				vars = model.filtermanager.apply(value, vars);

				Set<Edge> unEdges = new HashSet<Edge>();
				unEdges.addAll(outs);
				for (IVariable var : vars) {
					IValue value = var.getValue();
					if (ValueUtils.getID(value) != -1) {
						// check if we already have this edge
						Node other = model.getNodeForValue(value);
						Edge e = getOutEdgeForNode(other, var.getName());
						if (e == null) {
							// new variable
							e = model.getEdge(this, other, var.getName());
						} else {
							// we already have this
							// we still need this, so it is necessary
							unEdges.remove(e);
							e.getTo().changeValue(value);
						}
						e.getTo().setPath(getPath() + "/" + var.getName());
						model.stateStore.dropNodeState(e.getTo());
					}
				}
				// remove unnecessary edges
				for (Edge e : unEdges)
					e.dispose();
				unEdges.clear();
			}
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#toggleOpen()
	 */
	public void toggleOpen() {
		if (open) {
			this.disposeOuts();
			open = false;
		} else {
			open = true;
			try {
				if (value != null) this.setVariables(value.getVariables());
			} catch (DebugException e) {
				e.printStackTrace();
			}
		}
		notifychange();
	}

	@Override
	public void dispose() {
		closeChilds();
		super.dispose();
		model.disposeObjectNode(this);
	}

	protected void closeChilds() {
		for (Edge e : outs) {
			Node n = e.getTo();
			if (n.isOpen()) n.toggleOpen();
		}
	}

	public NodeState getState() {
		try {
			if (value.getVariables().length == 0) return NodeState.Primitive;
		} catch (DebugException e) {
			e.printStackTrace();
		}
		if (!visible) return NodeState.Hidden;
		return (open) ? NodeState.Open : NodeState.Closed;
	}

	public void toggleVisibility() {
		if (visible)
			hide();
		else
			show();
	}

	protected void hide() {
		// we cant hide the root node
		if (model.rootNode.equals(this)) return;

		// close node if open
		if (open) toggleOpen();

		this.visible = false;
		model.hideNode(this);
	}

	protected void show() {
		this.visible = true;
		model.showNode(this);
	}

	public boolean isVisible() {
		return visible;
	}

	public void showHiddenChildNodes() {
		model.showHiddenChildsOfNode(this);
	}

}
