package com.gmail.chickenpowerrr.chickentest.generator;

import com.gmail.chickenpowerrr.chickentest.generator.number.IntGenerator;
import java.io.Serial;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
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

  private final Collection<GeneratorDescription> descriptions;

  public GeneratorManager() {
    this.descriptions = new LinkedList<>();

    addGenerators();
  }

  /**
   * Adds the default {@link Generator}s.
   */
  private void addGenerators() {
    descriptions.add(new GeneratorDescription("IntGenerator", 1, IntGenerator::new) {
      @Override
      public boolean supports(ParameterContext context) {
        return context.getParameter().getType().equals(Integer.class)
            || context.getParameter().getType().equals(int.class);
      }
    });
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
    Queue<GeneratorDescription> descriptions = new PriorityQueue<>(Comparator.reverseOrder());
    for (GeneratorDescription description : this.descriptions) {
      if (description.supports(parameterContext)) {
        descriptions.offer(description);
      }
    }

    if (descriptions.isEmpty()) {
      return null;
    }

    return checkGeneratorAmbiguity(descriptions);
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
    int priority = cursor.getPriority();
    highestPriority.add(cursor);

    while (!descriptions.isEmpty() && (cursor = descriptions.poll()).getPriority() == priority) {
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
