package com.gmail.chickenpowerrr.chickentest.generator;

import java.util.function.Function;
import org.junit.jupiter.api.extension.ParameterContext;

public abstract class GeneratorRequirements implements Comparable<GeneratorRequirements> {

  private final String name;
  private final int priority;
  private final Function<ParameterContext, Generator<?>> generatorConstructor;

  public GeneratorRequirements(String name, int priority,
      Function<ParameterContext, Generator<?>> generatorConstructor) {
    this.name = name;
    this.priority = priority;
    this.generatorConstructor = generatorConstructor;
  }

  public abstract boolean supports(ParameterContext context);

  public String getName() {
    return name;
  }

  public int getPriority() {
    return priority;
  }

  @SuppressWarnings("unchecked")
  public <T> Generator<T> getGenerator(ParameterContext parameterContext) {
    return (Generator<T>) generatorConstructor.apply(parameterContext);
  }

  @Override
  public int compareTo(GeneratorRequirements other) {
    return Integer.compare(priority, other.priority);
  }
}
