/**
 * 
 */
package hu.gbalage.debugvisualisation.model;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class VariableEdge implements Edge {

	protected final Model model;
	
	protected final Node from;
	
	protected final Node to;
	
	protected final String name;
	
	protected EdgeCaptionListener listener = null;
	
	public VariableEdge(Model model,Node from, Node to,String name){
		this.model = model;
		this.from = from;
		this.to = to;
		this.name = name;
		
		from.addOut(this);
		to.addIn(this);
	}
	
	/**
	 * @see hu.gbalage.debugvisualisation.model.Edge#dispose()
	 */
	public void dispose() {
		model.removeEdge(this);
		from.removeOut(this);
		to.removeIn(this);
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Edge#getFrom()
	 */
	public Node getFrom() {
		return from;
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Edge#getTo()
	 */
	public Node getTo() {
		return to;
	}

	public String getName() {
		return name;
	}

	public void setDisplayCaptionListener(EdgeCaptionListener listener) {
		this.listener = listener;
	}
	
	public void showCaption(){
		if (listener != null) 
			listener.displayCaption(true);
	}
	
	public void hideCaption(){
		if (listener != null) 
			listener.displayCaption(false);
	}

}
