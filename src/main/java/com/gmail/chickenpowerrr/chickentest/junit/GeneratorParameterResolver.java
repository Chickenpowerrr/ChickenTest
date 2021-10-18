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
 * Makes sure parameters in test cases can be injected
 * by using the corresponding {@link Generator}s.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
class GeneratorParameterResolver implements ParameterResolver {

  private final GeneratorManager generatorManager;
  private final Map<ParameterContext, Generator<?>> cachedGenerators;

  public GeneratorParameterResolver() {
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
    if (cachedGenerators.containsKey(parameterContext)) {
      return true;
    }

    Generator<?> generator = generatorManager.getGenerator(parameterContext);

    if (generator == null) {
      return false;
    }

    cachedGenerators.put(parameterContext, generator);
    return true;
  }

  /**
   * Assumes that {@link #supportsParameter(ParameterContext, ExtensionContext)}
   * has already cached the {@link Generator}.
   *
   * @param parameterContext the parameter context
   * @param extensionContext the extension context
   * @return the value that should be injected
   */
  @Override
  public Object resolveParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) {
    return cachedGenerators.get(parameterContext).generate();
  }
}
