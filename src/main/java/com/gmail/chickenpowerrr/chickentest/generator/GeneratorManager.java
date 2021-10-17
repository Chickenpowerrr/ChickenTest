package com.gmail.chickenpowerrr.chickentest.generator;

import com.gmail.chickenpowerrr.chickentest.generator.number.IntGenerator;
import java.io.Serial;
import java.util.Collection;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;

public class GeneratorManager {

  private final Collection<GeneratorRequirements> requirements;

  public GeneratorManager() {
    this.requirements = new LinkedList<>();

    addGenerators();
  }

  private void addGenerators() {
    requirements.add(new GeneratorRequirements("IntGenerator", 10, IntGenerator::new) {
      @Override
      public boolean supports(ParameterContext context) {
        return context.getParameter().getType().equals(Integer.class)
            || context.getParameter().getType().equals(int.class);
      }
    });
  }

  public GeneratorRequirements getRequirements(ParameterContext parameterContext)
      throws AmbiguousGeneratorException {
    Queue<GeneratorRequirements> generatorRequirements = new PriorityQueue<>();
    for (GeneratorRequirements requirement : requirements) {
      if (requirement.supports(parameterContext)) {
        generatorRequirements.offer(requirement);
      }
    }

    if (generatorRequirements.isEmpty()) {
      return null;
    }

    return checkGeneratorAmbiguity(generatorRequirements);
  }

  private GeneratorRequirements checkGeneratorAmbiguity(Queue<GeneratorRequirements> requirements)
      throws AmbiguousGeneratorException {
    GeneratorRequirements cursor = requirements.poll();
    LinkedList<GeneratorRequirements> highestPriority = new LinkedList<>();
    int priority = cursor.getPriority();
    highestPriority.add(cursor);

    while (!requirements.isEmpty() && (cursor = requirements.poll()).getPriority() == priority) {
      highestPriority.add(cursor);
    }

    if (highestPriority.size() == 1) {
      return highestPriority.getFirst();
    }

    throw new AmbiguousGeneratorException(highestPriority);
  }

  public static class AmbiguousGeneratorException extends ParameterResolutionException {

    @Serial
    private static final long serialVersionUID = 7519852343555195749L;

    public AmbiguousGeneratorException(Collection<GeneratorRequirements> ambiguous) {
      super("Multiple Generators available: " + ambiguous);
    }
  }
}
