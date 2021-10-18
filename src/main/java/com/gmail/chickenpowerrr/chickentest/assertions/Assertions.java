package com.gmail.chickenpowerrr.chickentest.assertions;

import com.gmail.chickenpowerrr.chickentest.assertions.relation.RelationAssertion;
import com.gmail.chickenpowerrr.chickentest.assertions.relation.Statement;

/**
 * Contains the methods to create assertion on the relations
 * between statements, where a statement is a collection of
 * ordinary asserts.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public final class Assertions {

  public static final int DEFAULT_ID = 0;

  /**
   * Creates an assertion on a collection of ordinary asserts
   *
   * @param sentence the collection of ordinary asserts
   * @return the assertion
   */
  public static RelationAssertion assertRelation(Sentence sentence) {
    return new Statement(DEFAULT_ID, sentence);
  }

  private Assertions() {

  }
}
