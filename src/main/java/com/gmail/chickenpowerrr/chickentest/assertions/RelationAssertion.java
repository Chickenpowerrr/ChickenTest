package com.gmail.chickenpowerrr.chickentest.assertions;

import com.gmail.chickenpowerrr.chickentest.assertions.exception.RelationException;
import com.gmail.chickenpowerrr.chickentest.assertions.sentence.Sentence;

/**
 * Tests whether certain relations between collections of
 * ordinary asserts hold. Should always be closed with
 * an {@link #evaluate()}.
 *
 * @author Mark van Wijk
 * @since 1.0.9
 */
public interface RelationAssertion {

  /**
   * Tests whether the current sentence and the given
   * sentence both hold.
   *
   * @param sentence the other sentence
   * @return the and combination of both sentences
   */
  RelationAssertion and(Sentence sentence);

  /**
   * Tests whether the current sentence and the given
   * sentence have at least one that holds.
   *
   * @param sentence the other sentence
   * @return the or combination of both sentences
   */
  RelationAssertion or(Sentence sentence);

  /**
   * Tests whether the current sentence and the given
   * sentence have exactly one that holds.
   *
   * @param sentence the other sentence
   * @return the exclusive or combination of both sentences
   */
  RelationAssertion exclusiveOr(Sentence sentence);

  /**
   * Tests whether if the current sentence holds,
   * the other sentence also holds.
   *
   * @param sentence the other sentence
   * @return the implies combination of both sentences
   */
  RelationAssertion implies(Sentence sentence);

  /**
   * Makes sure the relations are evaluated.
   * If they don't hold, a {@link RelationException}
   * will be thrown.
   */
  void evaluate();
}
