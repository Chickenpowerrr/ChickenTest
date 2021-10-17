package com.gmail.chickenpowerrr.chickentest.generator;

import java.util.function.Function;
import org.junit.jupiter.api.extension.ParameterContext;

/**
 * Describes a {@link Generator}.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public abstract class GeneratorDescription implements Comparable<GeneratorDescription> {

  private final String name;
  private final int priority;
  private final Function<ParameterContext, Generator<?>> generatorConstructor;

  public GeneratorDescription(String name, int priority,
      Function<ParameterContext, Generator<?>> generatorConstructor) {
    this.name = name;
    this.priority = priority;
    this.generatorConstructor = generatorConstructor;
  }

  /**
   * Returns whether the described {@link Generator} can generate
   * values for the given {@link ParameterContext}.
   *
   * @param context the context of the value to be injected
   * @return whether the described {@link Generator} can generate
   *         values for the given {@link ParameterContext}
   */
  public abstract boolean supports(ParameterContext context);

  public String getName() {
    return name;
  }

  public int getPriority() {
    return priority;
  }

  /**
   * Returns the described {@link Generator} that can inject the value
   * that is described by the given {@link ParameterContext}.
   *
   * @param parameterContext the context of the value to be injected
   * @param <T> the type of the value to be injected
   * @return the described {@link Generator} that can inject the value
   *         that is described by the given {@link ParameterContext}
   */
  @SuppressWarnings("unchecked")
  public <T> Generator<T> getGenerator(ParameterContext parameterContext) {
    return (Generator<T>) generatorConstructor.apply(parameterContext);
  }

  /**
   * Compares the descriptions by the priority.
   *
   * @param other the description of the other {@link Generator}
   * @return 0 if priority == other.priority,
   *         a value above 0 if priority > other.priority,
   *         a value below 0 if priority < other.priority
   * @see Integer#compare(int, int)
   */
  @Override
  public int compareTo(GeneratorDescription other) {
    return Integer.compare(priority, other.priority);
  }
}
