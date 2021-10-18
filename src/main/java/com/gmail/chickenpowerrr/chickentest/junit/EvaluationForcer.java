package com.gmail.chickenpowerrr.chickentest.junit;

import com.gmail.chickenpowerrr.chickentest.assertions.relation.RelationAssertion;
import com.gmail.chickenpowerrr.chickentest.assertions.exception.NotEvaluatedException;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Used to ensure that developers do not forget to do a
 * {@link RelationAssertion#evaluate()} call on the assertions.
 * It is not ideal, but it does do this by maintaining a static
 * set with the remaining assertions that have not been
 * evaluated yet.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public class EvaluationForcer implements BeforeEachCallback, AfterEachCallback {

  private static Set<RelationAssertion> nonEvaluatedAssertions;

  /**
   * Marks an assertion as not yet evaluated.
   *
   * @param assertion the assertion to mark as not yet evaluated
   * @throws PluginNotEnabledException if the {@link ChickenTestExtension} has not
   *                                   been added as an extension for the test class
   */
  public static void addAssertion(RelationAssertion assertion) throws PluginNotEnabledException {
    if (nonEvaluatedAssertions == null) {
      throw new PluginNotEnabledException();
    }

    nonEvaluatedAssertions.add(assertion);
  }

  /**
   * Marks an assertion as evaluated.
   *
   * @param assertion the assertion to mark as evaluated
   * @throws PluginNotEnabledException if the {@link ChickenTestExtension} has not
   *                                   been added as an extension for the test class
   */
  public static void finished(RelationAssertion assertion) {
    if (nonEvaluatedAssertions == null) {
      throw new PluginNotEnabledException();
    }

    nonEvaluatedAssertions.remove(assertion);
  }

  /**
   * Resets the not evaluated assertions.
   *
   * @param extensionContext the context of the extension
   */
  @Override
  public void beforeEach(ExtensionContext extensionContext) {
    nonEvaluatedAssertions = new HashSet<>();
  }

  /**
   * Throws an {@link NotEvaluatedException} if there is still an assertion
   * which has not been evaluated after the test has finished.
   *
   * @param extensionContext the context of the extension
   * @throws NotEvaluatedException if there is still and unevaluated assertion
   */
  @Override
  public void afterEach(ExtensionContext extensionContext) throws NotEvaluatedException {
    if (!nonEvaluatedAssertions.isEmpty()) {
      nonEvaluatedAssertions.clear();
      throw new NotEvaluatedException();
    }
  }
}
