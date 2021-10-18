package com.gmail.chickenpowerrr.chickentest.generator;

import com.gmail.chickenpowerrr.chickentest.generator.annotations.GeneratorDescription;
import com.gmail.chickenpowerrr.chickentest.reflection.ReflectionHelper;
import java.io.IOException;
import java.io.Serial;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;

/**
 * Manages which generators are present and find which
 * ones are needed for certain values to be inserted.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public class GeneratorManager {

  private final Map<GeneratorDescription, Class<?>> descriptions;

  /**
   * Loads the descriptions from the {@link GeneratorScanner}.
   */
  public GeneratorManager() {
    try {
      this.descriptions = GeneratorScanner.getInstance().getGeneratorDescriptions();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns a fresh instance of a {@link Generator}, the type gets determined
   * by the {@link GeneratorDescription} according to
   * {@link #getGeneratorDescription(ParameterContext)}.
   * The {@link Generator} should have a public constructor with
   * {@link ParameterContext} as its only parameter.
   *
   * @param parameterContext the context of the value to be injected
   * @param <T> the type of value that will be injected
   * @return a fresh instance of a {@link Generator} that can generate
   *         the requested type of value
   * @throws ParameterResolutionException if the {@link Generator} class does not
   *                                      have a public constructor with only the
   *                                      {@link ParameterContext} as a parameter
   * @see #getGeneratorDescription(ParameterContext)
   */
  @SuppressWarnings("unchecked")
  public <T> Generator<T> getGenerator(ParameterContext parameterContext) throws ParameterResolutionException {
    GeneratorDescription description = getGeneratorDescription(parameterContext);
    Class<?> generatorClass = descriptions.get(description);
    try {
      return (Generator<T>) ReflectionHelper.getInstance(generatorClass,
          new Class[] { ParameterContext.class }, parameterContext);
    } catch (NoSuchMethodException e) {
      throw new ParameterResolutionException(generatorClass.getName()
          + " should have a public constructor with 'ParameterContext' as its parameter");
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new ParameterResolutionException(e.getClass().getName() + ": " + e.getMessage());
    }
  }

  /**
   * Finds all {@link Generator}s that can inserted a value with the
   * given {@link ParameterContext}. If there is none, it will return
   * {@code null}. If there is only one, it will return this one. If
   * there are multiple {@link Generator}s it will take the one with
   * the highest priority. If there are multiple {@link Generator}s with
   * the same, high priority, it will throw an {@link AmbiguousGeneratorException}.
   *
   * @param parameterContext the context of the value to be injected
   * @return {@code null} if there is no suitable {@link Generator},
   *         otherwise the {@link Generator} with the highest priority
   * @throws AmbiguousGeneratorException if there are multiple {@link Generator}s
   *                                     which have the highest priority
   */
  public GeneratorDescription getGeneratorDescription(ParameterContext parameterContext)
      throws AmbiguousGeneratorException {
    Queue<GeneratorDescription> descriptions =
        new PriorityQueue<>((a, b) -> Integer.compare(b.priority(), a.priority()));

    for (GeneratorDescription description : this.descriptions.keySet()) {
      if (canProvideValue(description, parameterContext)) {
        descriptions.offer(description);
      }
    }

    if (descriptions.isEmpty()) {
      return null;
    }

    return checkGeneratorAmbiguity(descriptions);
  }

  /**
   * Returns whether the {@link Generator} can provide a value
   * which is described by the {@link ParameterContext}.
   *
   * @param description the description of the {@link Generator}
   * @param parameterContext the context of the value to be injected
   * @return whether the {@link Generator} can provide a value
   *         which is described by the {@link ParameterContext}
   */
  private boolean canProvideValue(GeneratorDescription description, ParameterContext parameterContext) {
    Class<?> targetType = parameterContext.getParameter().getType();
    for (Class<?> cursor : description.value()) {
      if (cursor.equals(targetType)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns the {@link GeneratorDescription} which has the highest priority,
   * if there are multiple {@link Generator}s with that same priority,
   * it will throw an {@link AmbiguousGeneratorException}.
   *
   * @param descriptions the {@link GeneratorDescription}s of the
   *                     {@link Generator}s that are suitable
   * @return the {@link GeneratorDescription} with the highest priority
   * @throws AmbiguousGeneratorException if there are multiple {@link Generator}s
   *                                     with the highest priority
   */
  private GeneratorDescription checkGeneratorAmbiguity(Queue<GeneratorDescription> descriptions)
      throws AmbiguousGeneratorException {
    GeneratorDescription cursor = descriptions.poll();
    LinkedList<GeneratorDescription> highestPriority = new LinkedList<>();
    int priority = Objects.requireNonNull(cursor).priority();
    highestPriority.add(cursor);

    while (!descriptions.isEmpty() && (cursor = descriptions.poll()).priority() == priority) {
      highestPriority.add(cursor);
    }

    if (highestPriority.size() == 1) {
      return highestPriority.getFirst();
    }

    throw new AmbiguousGeneratorException(highestPriority);
  }

  /**
   * Notifies the user that there are multiple {@link Generator}s
   * with the same priority, so it is not possible to select one.
   *
   * @author Mark van Wijk
   * @since 1.0.0
   */
  public static class AmbiguousGeneratorException extends ParameterResolutionException {

    @Serial
    private static final long serialVersionUID = 7519852343555195749L;

    public AmbiguousGeneratorException(Collection<GeneratorDescription> ambiguous) {
      super("Multiple Generators available: " + ambiguous);
    }
  }
}
