package com.gmail.chickenpowerrr.chickentest.junit;

import com.gmail.chickenpowerrr.chickentest.assertions.exception.NotEvaluatedException;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * The main extension that should be added to the JUnit classes
 * in order to make sure that the parameters can be injected
 * and the evaluation of assertions can be checked.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public class ChickenTestExtension implements ParameterResolver, BeforeEachCallback,
    AfterEachCallback {

  private final EvaluationForcer evaluationForcer;
  private final GeneratorParameterResolver parameterResolver;

  public ChickenTestExtension() {
    this.evaluationForcer = new EvaluationForcer();
    this.parameterResolver = new GeneratorParameterResolver();
  }

  @Override
  public boolean supportsParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws ParameterResolutionException {
    return parameterResolver.supportsParameter(parameterContext, extensionContext);
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws ParameterResolutionException {
    return parameterResolver.resolveParameter(parameterContext, extensionContext);
  }

  @Override
  public void beforeEach(ExtensionContext extensionContext) {
    evaluationForcer.beforeEach(extensionContext);
  }

  @Override
  public void afterEach(ExtensionContext extensionContext) throws NotEvaluatedException {
    evaluationForcer.afterEach(extensionContext);
  }
}
