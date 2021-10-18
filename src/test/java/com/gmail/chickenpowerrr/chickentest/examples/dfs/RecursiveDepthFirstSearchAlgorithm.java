package com.gmail.chickenpowerrr.chickentest.examples.dfs;

import com.gmail.chickenpowerrr.chickentest.examples.dfs.Vertex.Edge;
import java.util.HashSet;
import java.util.Set;

public class RecursiveDepthFirstSearchAlgorithm implements DepthFirstSearchAlgorithm {

  @Override
  public Set<Vertex> explore(Vertex start) {
    return explore(start, new HashSet<>());
  }

  private Set<Vertex> explore(Vertex start, Set<Vertex> visited) {
    visited.add(start);

    for (Edge edge : start.getOutgoingEdges()) {
      if (!visited.contains(edge.end())) {
        explore(edge.end(), visited);
      }
    }

    return visited;
  }
}
