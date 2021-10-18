package com.gmail.chickenpowerrr.chickentest.assertions.exception;

import com.gmail.chickenpowerrr.chickentest.assertions.relation.RelationAssertion;
import java.io.Serial;

/**
 * Thrown when {@link RelationAssertion#evaluate()} isn't called
 * when it should have been.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public class NotEvaluatedException extends Exception {

  @Serial
  private static final long serialVersionUID = -8469280018176944898L;

  public NotEvaluatedException() {
    super("Didn't evaluate an assertion");
  }
}
