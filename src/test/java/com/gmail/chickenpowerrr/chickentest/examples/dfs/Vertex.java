package com.gmail.chickenpowerrr.chickentest.examples.dfs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Vertex {

  private final Set<Edge> outgoingEdges;

  public Vertex() {
    this.outgoingEdges = new HashSet<>();
  }

  public void addOutgoingEdge(Vertex target) {
    this.outgoingEdges.add(new Edge(this, target));
  }

  public Set<Edge> getOutgoingEdges() {
    return Collections.unmodifiableSet(outgoingEdges);
  }

  public static record Edge(Vertex start, Vertex end) {

  }
}
