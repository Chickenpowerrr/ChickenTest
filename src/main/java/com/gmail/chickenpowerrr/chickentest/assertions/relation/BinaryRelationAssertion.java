package com.gmail.chickenpowerrr.chickentest.assertions.relation;

import com.gmail.chickenpowerrr.chickentest.junit.EvaluationForcer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Represents a binary relational operator.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public class BinaryRelationAssertion extends BaseRelationAssertion {

  private final BaseRelationAssertion left;
  private final BaseRelationAssertion right;
  private final BiFunction<Boolean, Boolean, Boolean> operator;

  /**
   * Sets the values and takes over the responsibility of being
   *
   * @param nextId the id for the next statement
   * @param left the left side of the operator
   * @param right the right side of the operator
   * @param symbol the textual representation of the operator
   * @param operator the effect of the operator on booleans
   */
  public BinaryRelationAssertion(int nextId, BaseRelationAssertion left,
      BaseRelationAssertion right, String symbol, BiFunction<Boolean, Boolean, Boolean> operator) {
    super(nextId, String.format("(%s %s %s)", left.getRelation(), symbol, right.getRelation()));
    this.left = left;
    this.right = right;
    this.operator = operator;

    EvaluationForcer.finished(left);
    EvaluationForcer.finished(right);
  }

  /**
   * Returns the errors of the left and right sides combined
   * and the result of the evaluation of the relation.
   */
  @Override
  protected EvaluationResult evaluatePart() {
    EvaluationResult leftResult = left.evaluatePart();
    EvaluationResult rightResult = right.evaluatePart();

    Map<Integer, AssertionError> errors = new HashMap<>();
    errors.putAll(leftResult.errors());
    errors.putAll(rightResult.errors());

    boolean relationHolds = operator.apply(leftResult.relationHolds(), rightResult.relationHolds());

    return new EvaluationResult(errors, relationHolds);
  }
}
