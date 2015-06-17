# Variable filters #

Variable filters are used to filter variables displayed as nodes in the graph to hide irrelevant information and reduce node quantity to a sane value. Filters are assigned to types which can occur during the debug process.

For example, if we take a look at the java.lang.Integer type, which can appear in a JDT debug process. This class has many fields, but only one field contains the value of the contained integer. To create a filter for this task, we should implement the hu.gbalage.debugvisualisation.filters.IFilter interface:

```
public interface IFilter {

	public IVariable[] apply(IVariable[] vars) throws CoreException;
	
}
```

When a complex node (with hidden child-nodes) is opened, the filter given for the type of the node is applied to the array of sub-variables of the value represented by the node. For a java.lang.Integer typed node, the following filter is applied:

```
public class PrimitiveTypeFilter implements IFilter {

	public IVariable[] apply(IVariable[] vars) throws CoreException {
		return FilterManager.filterbynames(vars, new String[]{"value"});
	}

}
```

The helper function "filterbynames" selects the given variables by searching for the names given in the array of String. This filter is applied to the java.lang.Integer type through the extension entry:

```
<extension point="hu.gbalage.debugvisualisation.filters">
      <filter
            filter="hu.gbalage.debugvisualisation.filters.PrimitiveTypeFilter"
            id="hu.gbalage.debugvisualisation.filters.filter5"
            name="Integer"
            use_on="java.lang.Integer">
      </filter>
</extension>
```

As you can see, you can connect the one filter to multiple types by making multiple extension entries with the same filter but different type (use\_on).