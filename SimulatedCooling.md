# Simulated Cooling Layout #

The simulated cooling algorithm is a well-known algorithm for automatic graph layouts. There was no built-in implementation for Zest, so I tried to make one. The basic idea behind the algorithm is to find the best possible graph layout (so-called "configuration"). The goodness of a configuration is given by several criteria functions. A criteria function assigns a numeric value to every graph configuration. A lower value means a better graph configuration according to a criteria. The overall goodness for a configuration is given by the sum of the results of all available criteria function for the configuration.

The algorithm finds a nearly optimal configuration by applying random node moves on the graph. If the new move is not better than the previous then the move is undone. The algorithm is called simulated cooling because it starts at high temperature, when the random moves are "long", making large changes in the graph. The temperature decreases progressively, making smaller and smaller changes. This ensures that a roughly good configuration is found in large steps, then it is refined in smaller moves.

## Criteria functions ##

A criteria function evaluates the graph configuration according to a well-defined aspect of graph layout beauty. This can be explained by examples, so here is a list of implemented criteria functions (they can be found in the `hu.cubussapiens.zestlayouts.simulatedcooling.criteria` package):

  * `PointDistribution`: We want the nodes to be as far from each other as possible. Therefore this criteria returns higher value for closer nodes.
  * `EdgeLength`: We want the edges in the graph be as short as possible. This criteria returns higher value for longer egdes. Note that this and the previous criteria are in conflict with each other. With carefully chosen parameters the algorithm finds a balanced configuration which will be "good" according to both criterias.
  * `InBounds`: We want the graph to be visible in the given bounds, so we punish nodes outside of visible bounds with high values.
  * `EdgeIntersection`: When edges intersect, the graph becomes complex and it is hard to see the structure in it. This criteria punishes edge intersections to ensure that the algorithm tries to avoid these.