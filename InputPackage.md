# Introduction #

As the most important and sensible part of the project, this functionality was reimplemented several times as I tried out different approaches. The one described here is currently the best with which I could come out. The method I'm currently using enables us to handle different functionalities separately and connect them in a simple and standard way.

# Base idea #

Firstly, I've created an interface to access the variable-graph in a lazy way. This means that there is no methods like "list all available nodes or edges", all methods gives only a specific part of the graph at a time. This interface is called IRootedGraphContentProvider. The methods of it are the following:

  * getRoots(): the graph needs to be started somewhere. This method returns a collection of nodes, called roots. A root is the starting point of a weakly connected directed graph component, therefore it is true that every node in the component can be accessed through a directed path from the root. There can be more than one roots, as there can be more than one separated graph components.
  * getChilds(node): This method lists all nodes, which can be reached via a directed edge (one-step path) from the given node. This method is used to retrieve child nodes accessible from nodes, which is known previously.
  * getEdge(nodea,nodeb): list all directed edges from nodea to nodeb.

Using the above methods, one can retrieve the graph fully, or partially. It is the caller's responsibility to handle directed cycles in the graph (which will occur eventually).

Now that we have an interface that can provide us a graph, we can display it, by collecting all nodes and edges for the Zest GraphViewer. This is done by the class called StackFrameGraphContentProvider. This class implements the interface IGraphEntityRelationshipContentProvider and can be added to a GraphViewer as content provider.

But before that, as we do not want to display the whole stack frame with every variables in it, we want to do some transformations on the graph. These transformations consists so-called transformation steps, which are simple, one-purpose transformations connected together in a chain. Every transformation step accepts a graph, provided by the above mentioned IRootedGraphContentProvider interface, and it also provides such interface to let access the transformed graph.

It is essential that the transformation is done lazily also, therefore it should be able to be executed on a part of the graph, as there may be no need to transform the whole graph. The interface of a transformation step is called IGraphTransformationStep, which extends IRootedGraphContentProvider. The methods, with which this interface extends the graph content provider are the following:

  * getParent(): the previous transformation step in the chain. This step transforms the graph provided by its parent.
  * addListener(), removeListener(): handling listeners, which can notify us of changes in the graph.
  * execute(command): The operation of the transformation steps are controlled through commands, which can be given to the last step in the chain. Any step, that recognizes the command, will execute it, and notifies the listeners about the change. If the command is not recognized, it is delegated to the parent of the step, hoping that one of the transformation steps can execute it. These commands are intended to change the inner state of the step, which modifies the operation of the step.
  * getNodeState(node, statedomain): Some of the transformation steps can store "transformation step - specific" informations about nodes, which can be queried using this method. The query is given by the argument called "statedomain", which is recognized by one of the steps, as they do with commands. The step, which recognizes the query will answer it.


# Current chain of transformation steps #

The transformation chain is controlled by the class called StackFrameContextInput. This class is used for input to the GraphViewer.

  * StackFrameRootedGraphContentProvider: This is start of the chain. It is not a transformation step, but an IRootedGraphContentProvider. This class provides the variables in the stack frame as a lazy-loaded graph. The contents are truly lazy-loaded.
  * CacheTransformationStep: This step not really transforms the graph, but caches it, as accessing the debug context can be slow sometimes.
  * ReferenceTrackerTransformationStep: This is neither a real transformation step, it just provides node state queries to get references to a given node.
  * DigInTransformationStep: The first real transformation can store some nodes, which will be handled as they would be the root nodes for the graph, not the original local context. Command for this step are the selection of root nodes, and to clear them, and return to the local context node.
  * OpenCloseTransformationStep: This step can store the open/closed state of the nodes. It also filters the child nodes of the closed nodes. The only command for this step is to toggle the open/closed state of a node. This step also provides a query to get the open/closed state of a node. There are two more special state indicating a root node, which cannot be closed, and a childless state, which does not have any child, therefore can't be opened.
  * ParametersTransformationStep: This step differentiates the variables storing a constant value, from the variables with an object value. Objects does have a unique ID in the debug context, and should be displayed as nodes. Constant values in contrast will not be displayed as nodes, but as parameters of nodes. Therefore this step filters nodes with constant values from the graph, and provides a query to access them separately.
  * HideNodesTransformationStep: This step can hide specific nodes from the graph, which is controlled by commands. The two command controlling this step to hide the selected nodes, and to show the hidden child nodes of the selected nodes (as hidden nodes could not be selected)


That's it so far. More functions will be implemented as transformation steps in the future.