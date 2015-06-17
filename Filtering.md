This page is deprecated - a new implementation based on the "Logical Structure" function is already present in the SVN tree in an early alpha form.

# Filtering #

Filtering means a context-aware and automatic restructuring the input tree based on rules which are provided by contributors or the user. According to DebugContextInput, this functionality should be implemented as a step in the transformation chain.

This proposal provides a rule language syntax and semantics for the filter functionality.

## Rules ##

A rule is identified by a fully qualified type name, which is matched to the type of the node, and if the two types are equal, the filter rule should be applied to the subgraph beginning on the selected node. Therefore a rule expression should be like the following:

```
<qualified_type>":"<filter_expression>
```

Where `<qualified_type>` is a string containing the type which on this filter rule should be applied. For the filter expression, the following syntax should be applied:

```
<filter_expression> := <branch_filter> | <or_filter> | <and_filter>

<and_filter> := "("[<filter_expression>]*")"
<or_filter> := "{"[<filter_expression>]*"}"

<branch_filter> := <edge_filter> | <edge_filter>"."<branch_filter>
<edge_filter> := <name_filter>["'"] | <programmed_filter>["'"] | <or_filter> | <and_filter>
```

## Applying rules ##

A rule is applied to a subgraph, beginning with the selected node, and consists all nodes and edges which can be reached on directed path from the selected node. A filter expression selects depth-limited subtree of the subgraph, and restructures it to a tree, which has only one level under the selected node (which is also the root node of the tree).

To make this clear, let's see a simple example:

We want to create a filter on `java.util.ArrayList<E>`. This class has several fields, but only the array named elementData contains interesting data to us. This array also can contain null values, which are again not interesting. On the result, we want to display only the not null children of this array as child nodes of the `ArrayList` object.

Using the syntax above, the filter description is very straightforward, saying that the child nodes of the array list node will be the not null child nodes of the child node named `elementData` of the selected node:

```
java.util.ArrayList<E>:
    elementData.@notnull
```

In the example, the filter expression of the rule is a branch filter, which means that it can select nodes on multiple levels in the subgraph. The first element in the branch expression is a name filter, which filters the child nodes of the selected nodes to match the given name "elementData". This filter selects only one node actually. The next element in the branch filter filters the child nodes of the nodes which were selected by the previous filter. In the example we select the values, which are not null, using a built-in programmed filter.

If we want to display the size of the list also, we can use the following rule instead. The size value is stored in a child node called size. To accomplish this, we can use conjunctive parenthesis of filters:

```
java.utils.ArrayList<E>:{
    elementData.@notnull
    size
}
```

### Names of selected child nodes ###

By default, the filter does not change the name of the selected child nodes. But, when a branch filter is applied, the name of the child node will be the concatenated names of the variable in the path selected by the branch filter. For example the selected child nodes of a `java.util.ArrayList<E>` by the `elementData.@notnull` filter will be named as `elementData[0]`, `elementData[1]`, `elementData[2]`, and so on. If we want, we can remove the "elementData" part of the name by adding a "'" character to the "elementData" name filter. Generally, adding such character to a filter will disable it to add any name to the selected nodes. To get a usable result, you must ensure that the selected variables will have a correct and unique name. In the example, if you want to name the items in the list as `[0]`, `[1]`, etc, use the following rule:

```
java.util.ArrayList<E>:
    elementData'.@notnull
```


## Proposals of built-in filters for special classes ##

  * primitive types
  * `java.util.ArrayList`
  * `java.util.LinkedList`
  * `java.util.HashMap`
  * `java.util.HashMap$Entry`
  * `java.math.BigInteger`
  * `java.math.BigDecimal`
  * `java.lang.Enum`

## More examples ##

to be continued..