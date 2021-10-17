package com.gmail.chickenpowerrr.chickentest.junit;

import com.gmail.chickenpowerrr.chickentest.generator.Generator;
import com.gmail.chickenpowerrr.chickentest.generator.GeneratorManager;
import com.gmail.chickenpowerrr.chickentest.generator.GeneratorManager.AmbiguousGeneratorException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * The main extension that should be added to the JUnit classes
 * in order to make sure that the parameters can be injected.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public class ChickenTestExtension implements ParameterResolver {

  private final GeneratorManager generatorManager;
  private final Map<ParameterContext, Generator<?>> cachedGenerators;

  public ChickenTestExtension() {
    this.generatorManager = new GeneratorManager();
    this.cachedGenerators = new HashMap<>();
  }

  /**
   * Checks whether the generator is already cached, if so
   * it is possible to inject the value, otherwise it will need
   * to verify whether it is possible to get a generator from
   * the {@link GeneratorManager}.
   *
   * @param parameterContext the parameter context
   * @param extensionContext the extension context
   * @return whether the value can be injected
   * @throws AmbiguousGeneratorException if multiple generators have the same
   *                                     priority and can all be used
   */
  @Override
  public boolean supportsParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws AmbiguousGeneratorException {
    return cachedGenerators.containsKey(parameterContext) ||
        generatorManager.getGeneratorDescription(parameterContext) != null;
  }

  /**
   * Checks whether the generator is already cached, if so
   * it uses this generator, otherwise it will use a new generator
   * from the {@link GeneratorManager} to inject the value.
   *
   * @param parameterContext the parameter context
   * @param extensionContext the extension context
   * @return the value that should be injected
   */
  @Override
  public Object resolveParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) {
    if (cachedGenerators.containsKey(parameterContext)) {
      return cachedGenerators.get(parameterContext);
    }

    return generatorManager.getGeneratorDescription(parameterContext)
        .getGenerator(parameterContext)
        .generate();
  }
}
