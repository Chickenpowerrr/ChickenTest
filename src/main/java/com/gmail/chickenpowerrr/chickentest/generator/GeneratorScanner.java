package com.gmail.chickenpowerrr.chickentest.generator;

import com.gmail.chickenpowerrr.chickentest.generator.annotations.GeneratorDescription;
import com.gmail.chickenpowerrr.chickentest.reflection.ReflectionHelper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;

/**
 * Stores given {@link Generator}s and can scan packages
 * for new {@link Generator}s if it is given a package.
 * To be a valid {@link Generator} that gets detected, a
 * {@link Generator} needs a {@link GeneratorDescription},
 * a public constructor with as the only argument a
 * {@link ParameterContext}.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public class GeneratorScanner {

  private final Map<GeneratorDescription, Class<?>> descriptions;

  public GeneratorScanner() {
    this.descriptions = new HashMap<>();
  }

  /**
   * Returns the class that has been annotated with the
   * given {@link GeneratorDescription}.
   *
   * @param description the description of the {@link Generator}
   * @return the class that is annotated with the given description
   */
  public Class<?> getAnnotatedClass(GeneratorDescription description) {
    return descriptions.get(description);
  }

  /**
   * Returns all the known descriptions.
   */
  public Set<GeneratorDescription> getDescriptions() {
    return descriptions.keySet();
  }

  /**
   * Adds a {@link Generator} with that has the given class.
   *
   * @param generatorClass the class of the {@link Generator} to
   *                       be added
   * @param <T> the type the {@link Generator} generates
   * @throws ParameterResolutionException if the generator does not have a
   *                                      {@link GeneratorDescription} or does not have
   *                                      a public constructor with a
   *                                      {@link ParameterContext} as the only
   *                                      parameter
   */
  public <T> void addGenerator(Class<? extends Generator<T>> generatorClass)
      throws ParameterResolutionException {
    if (generatorClass.getAnnotation(GeneratorDescription.class) == null) {
      throw new ParameterResolutionException(generatorClass.getName()
          + " needs to have a GeneratorDescription annotation");
    }

    addGeneratorIfValid(generatorClass);
  }

  /**
   * Searches for all the {@link Generator}s that have
   * {@link GeneratorDescription}s. It searches for them in the
   * given package.
   *
   * @throws IOException should not happen
   * @throws ClassNotFoundException should not happen
   * @throws ParameterResolutionException if the {@link Generator} does not
   *                                      have a public constructor with a
   *                                      {@link ParameterContext} as the only
   *                                      parameter
   */
  public void addGenerators(String packageName)
      throws IOException, ClassNotFoundException, ParameterResolutionException {
    for (Class<?> generatorClass : ReflectionHelper.getClasses(packageName,
        clazz -> clazz.getAnnotation(GeneratorDescription.class) != null
            && Generator.class.isAssignableFrom(clazz))) {
      addGeneratorIfValid(generatorClass);
    }
  }

  /**
   * Registers the class for a given {@link Generator} which should
   * have a {@link GeneratorDescription} annotation.
   *
   * @param generatorClass the class to be added
   * @throws ParameterResolutionException if the {@link Generator} does not
   *                                      have a public constructor with a
   *                                      {@link ParameterContext} as the only
   *                                      parameter
   */
  private void addGeneratorIfValid(Class<?> generatorClass) throws ParameterResolutionException {
    if (ReflectionHelper.hasConstructor(generatorClass, ParameterContext.class)) {
      descriptions.put(generatorClass.getAnnotation(GeneratorDescription.class), generatorClass);
    } else {
      throw new ParameterResolutionException(generatorClass.getName()
          + " should have a public constructor with 'ParameterContext' as its parameter");
    }
  }
}
