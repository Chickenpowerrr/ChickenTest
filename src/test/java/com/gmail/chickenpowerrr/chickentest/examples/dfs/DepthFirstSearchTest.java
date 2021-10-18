package com.gmail.chickenpowerrr.chickentest.examples.dfs;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.gmail.chickenpowerrr.chickentest.generator.GeneratorScanner;
import com.gmail.chickenpowerrr.chickentest.junit.ChickenTestExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChickenTestExtension.class)
public class DepthFirstSearchTest {

  private DepthFirstSearchAlgorithm iterative;
  private DepthFirstSearchAlgorithm recursive;

  @BeforeAll
  public static void beforeAll() {
    GeneratorScanner.getInstance().addPackage("com.gmail.chickenpowerrr.chickentest.examples.dfs");
  }

  @BeforeEach
  public void setUp() {
    this.iterative = new IterativeDepthFirstSearchAlgorithm();
    this.recursive = new RecursiveDepthFirstSearchAlgorithm();
  }

  @RepeatedTest(100)
  public void iterativeAndRecursive_giveSameResults(Vertex vertex) {
    assertThat(recursive.explore(vertex)).isEqualTo(iterative.explore(vertex));
  }
}
