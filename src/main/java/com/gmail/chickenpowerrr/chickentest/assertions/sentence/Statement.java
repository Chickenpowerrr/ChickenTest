package com.gmail.chickenpowerrr.chickentest.assertions.sentence;

import com.gmail.chickenpowerrr.chickentest.assertions.BaseRelationAssertion;
import java.util.Map;

/**
 * Represents a collection of ordinary asserts.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public class Statement extends BaseRelationAssertion {

  private final int id;
  private final Sentence sentence;

  public Statement(int id, Sentence sentence) {
    super(id + 1, Integer.toString(id));
    this.id = id;
    this.sentence = sentence;
  }

  /**
   * Returns the {@link EvaluationResult} with the error if the
   * assertion fails, an empty map otherwise.
   */
  @Override
  public EvaluationResult evaluatePart() {
    return getAssertionError(sentence)
        .map(assertionError -> new EvaluationResult(Map.of(id, assertionError), false))
        .orElseGet(() -> new EvaluationResult(Map.of(), true));
  }
}
