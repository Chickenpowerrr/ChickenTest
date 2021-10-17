package com.gmail.chickenpowerrr.chickentest.junit;

import com.gmail.chickenpowerrr.chickentest.generator.Generator;
import com.gmail.chickenpowerrr.chickentest.generator.GeneratorManager;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class ChickenTestExtension implements ParameterResolver {

  private final GeneratorManager generatorManager;
  private final Map<ParameterContext, Generator<?>> cachedGenerators;

  public ChickenTestExtension() {
    this.generatorManager = new GeneratorManager();
    this.cachedGenerators = new HashMap<>();
  }

  @Override
  public boolean supportsParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws ParameterResolutionException {
    return cachedGenerators.containsKey(parameterContext) ||
        generatorManager.getRequirements(parameterContext) != null;
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws ParameterResolutionException {
    if (cachedGenerators.containsKey(parameterContext)) {
      return cachedGenerators.get(parameterContext);
    }

    return generatorManager.getRequirements(parameterContext)
        .getGenerator(parameterContext)
        .generate();
  }
}
