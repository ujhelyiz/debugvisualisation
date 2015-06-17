# Input #

The purpose of the DebugContextInput is to provide content for the graph viewer. The variables in the stack frame is given by the debug framework in a tree form. The nodes of the trees is representing values in the context, which can be constants or objects. The different between the two type of nodes that objects always has a unique id, and can have child nodes. Edges in the tree are variables, which are references to objects or constants. An object can have multiple references in it, therefore this object appears in the tree multiple times.

From the stack frame the DebugContextInput creates a graph, where all appearances of an object is displayed as one node in the graph. Constant values, which does not have unique ids, are have only one reference and appears in the tree only one. These values are handled as parameters of their parent node, as they have a simple value and should not have their own nodes.

## Functions ##

Beside converting the tree into a graph, we should provide some functionalities:

  * Lazy loading: in many cases, the models are big. We should not go through it all at every change in the debug context.
  * show/hide child nodes: open and close nodes, which shows or hides their out-edges. If a node has zero in-edges, it should be hidden. (except the root node, which always has zero in-edges)
  * show/hide specific nodes: mark nodes to be never displayed in any case (until this mark is released)
  * filtering: this will be a more complex functionality, I will describe it on a different wiki page.
  * ((Dig in: ability to start the graph from an other node than the local context. This means that we select a branch in the input tree(!). All other branches of the input tree should be ignored.)) Not sure this is a correct action on the user level. I propose instead add new nodes. Stampie
  * Add nodes: add new nodes to the graph. It may or may not be connected to previously added nodes; it may be used for building a different part, that subtree might be unrelated to the previous one.


Are there other functions we should take into consideration?


## Implementation ##

I propose defining a View model. Elements of the View model consist of a reference to the variables and various other parameters (defining e.g. that the node is open, hidden, etc.).

This View model is built from the original, tree-based input using a series of transformation steps. These series shall contain the filter functionality.

The transformation steps should be built in an incremental manner, so it should be possible to alter an existing model space instead of rebuilding it (typical commands: open a closed node, add new nodes).

The Content Provider should transfer the information of this View model into the GraphViewer component for display.