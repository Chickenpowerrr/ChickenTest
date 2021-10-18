package com.gmail.chickenpowerrr.chickentest.examples.dfs;

import com.gmail.chickenpowerrr.chickentest.examples.dfs.Vertex.Edge;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class IterativeDepthFirstSearchAlgorithm implements DepthFirstSearchAlgorithm {

  @Override
  public Set<Vertex> explore(Vertex start) {
    Stack<Vertex> targets = new Stack<>();
    Set<Vertex> visited = new HashSet<>();
    targets.add(start);

    while (!targets.isEmpty()) {
      Vertex cursor = targets.pop();
      visited.add(cursor);

      for (Edge edge : cursor.getOutgoingEdges()) {
        if (!visited.contains(edge.end())) {
          targets.push(edge.end());
        }
      }
    }

    return visited;
  }
}
