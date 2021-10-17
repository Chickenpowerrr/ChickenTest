package com.gmail.chickenpowerrr.chickentest.assertions;

import java.util.Map;
import java.util.Optional;

/**
 * Internals to implement the {@link RelationAssertion} but without
 * exposing intern functions that are irrelevant for end-users.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public abstract class BaseRelationAssertion implements RelationAssertion {

  private final int nextId;
  private final String relation;

  public BaseRelationAssertion(int nextId, String relation) {
    this.relation = relation;
    this.nextId = nextId;
  }

  public String getRelation() {
    return relation;
  }

  @Override
  public RelationAssertion and(Sentence sentence) {
    return new BinaryRelationAssertion(nextId + 1, this, new Statement(nextId, sentence),
        "&&", (a, b) -> a && b);
  }

  @Override
  public RelationAssertion or(Sentence sentence) {
    return new BinaryRelationAssertion(nextId + 1, this, new Statement(nextId, sentence),
        "||", (a, b) -> a || b);
  }

  @Override
  public RelationAssertion exclusiveOr(Sentence sentence) {
    return new BinaryRelationAssertion(nextId + 1, this, new Statement(nextId, sentence),
        "^", (a, b) -> (a || b) && !(a && b));
  }

  @Override
  public RelationAssertion implies(Sentence sentence) {
    return new BinaryRelationAssertion(nextId + 1, this, new Statement(nextId, sentence),
        "->", (a, b) -> !a || b);
  }

  @Override
  public void evaluate() {
    EvaluationResult evaluationResult = evaluatePart();
    if (!evaluationResult.relationHolds()) {
      throw new RelationException(relation, evaluationResult.errors());
    }
  }

  /**
   * Returns an {@link AssertionError} if it is thrown during
   * the evaluation of the {@link Sentence}, nothing otherwise.
   *
   * @param sentence the {@link Sentence} to be evaluated
   * @return an {@link AssertionError} if it is thrown during
   *         the evaluation of the {@link Sentence}, nothing otherwise
   */
  protected Optional<AssertionError> getAssertionError(Sentence sentence) {
    try {
      sentence.accept();
      return Optional.empty();
    } catch (AssertionError e) {
      return Optional.of(e);
    }
  }

  /**
   * Returns the {@link EvaluationResult} that represents the
   * execution of the current {@link RelationAssertion}.
   */
  protected abstract EvaluationResult evaluatePart();

  /**
   * The result of the evaluation of a relation.
   *
   * @param errors the id of the statements that threw an error
   *               as the key and the error as the value
   * @param relationHolds whether the underlying relation holds
   *
   * @author Mark van Wijk
   * @since 1.0.0
   */
  protected static record EvaluationResult(Map<Integer, AssertionError> errors,
                                           boolean relationHolds) {

  }
}
