package com.gmail.chickenpowerrr.chickentest.generator;

import com.gmail.chickenpowerrr.chickentest.generator.annotations.GeneratorDescription;
import com.gmail.chickenpowerrr.chickentest.reflection.ReflectionHelper;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;

/**
 * Scans the given packages for {@link Generator}s.
 * To be a valid {@link Generator} that gets detected, a
 * {@link Generator} needs a {@link GeneratorDescription},
 * a public constructor with as the only argument a
 * {@link ParameterContext} and a parent package needs to be
 * registered via {@link #addPackage(String)}.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public class GeneratorScanner {

  private static GeneratorScanner instance = new GeneratorScanner();
  private final Set<String> packagesToScan;

  /**
   * Resets the current {@link GeneratorScanner}.
   */
  public static void resetInstance() {
    instance = new GeneratorScanner();
  }

  public static GeneratorScanner getInstance() {
    return instance;
  }

  /**
   * Registers the project's default package.
   */
  public GeneratorScanner() {
    this.packagesToScan = new HashSet<>();

    addPackage("com.gmail.chickenpowerrr.chickentest");
  }

  /**
   * Registers a package such that it will scan it and its
   * child packages for {@link Generator}s.
   *
   * @param packageName the name of the package
   */
  public void addPackage(String packageName) {
    packagesToScan.add(packageName);
  }

  /**
   * Returns all the {@link Generator}s that have
   * {@link GeneratorDescription}s. It searches for them in the
   * packages registered via {@link #addPackage(String)}.
   *
   * @return all the found {@link GeneratorDescription}s
   * @throws IOException should not happen
   * @throws ClassNotFoundException should not happen
   * @throws ParameterResolutionException if the {@link Generator} does not
   *                                      have a public constructor with a
   *                                      {@link ParameterContext} as the only
   *                                      parameter
   */
  public Map<GeneratorDescription, Class<?>> getGeneratorDescriptions()
      throws IOException, ClassNotFoundException, ParameterResolutionException {
    Map<GeneratorDescription, Class<?>> descriptions = new HashMap<>();
    for (String packageName : packagesToScan) {
      for (Class<?> descriptionClass : ReflectionHelper.getClasses(packageName,
          clazz -> clazz.getAnnotation(GeneratorDescription.class) != null
              && Generator.class.isAssignableFrom(clazz))) {
        if (ReflectionHelper.hasConstructor(descriptionClass, ParameterContext.class)) {
          descriptions.put(descriptionClass.getAnnotation(GeneratorDescription.class),
               descriptionClass);
        } else {
          throw new ParameterResolutionException(descriptionClass.getName()
              + " should have a public constructor with 'ParameterContext' as its parameter");
        }
      }
    }

    return descriptions;
  }
}
