package com.gmail.chickenpowerrr.chickentest.examples.dfs;

import com.gmail.chickenpowerrr.chickentest.generator.Generator;
import com.gmail.chickenpowerrr.chickentest.generator.annotations.GeneratorDescription;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.extension.ParameterContext;

@GeneratorDescription(Vertex.class)
public class VertexGenerator implements Generator<Vertex> {

  private final ParameterContext parameterContext;

  public VertexGenerator(ParameterContext parameterContext) {
    this.parameterContext = parameterContext;
  }

  @Override
  public Vertex generate() {
    int vertexCount = ThreadLocalRandom.current().nextInt(100) + 1;
    int edgeCount = ThreadLocalRandom.current().nextInt(vertexCount * vertexCount);

    Map<Integer, Vertex> vertices = IntStream.range(0, vertexCount)
        .boxed()
        .collect(Collectors.toMap(Function.identity(), id -> new Vertex()));

    for (int i = 0; i < edgeCount; i++) {
      Vertex start = vertices.get(ThreadLocalRandom.current().nextInt(vertexCount));
      Vertex end = vertices.get(ThreadLocalRandom.current().nextInt(vertexCount));
      start.addOutgoingEdge(end);
    }

    return vertices.get(0);
  }
}
