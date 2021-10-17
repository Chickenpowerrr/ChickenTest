package com.gmail.chickenpowerrr.chickentest.generator;

/**
 * Can generate a random value.
 *
 * @param <T> the type of the value to be generated
 * @author Mark van Wijk
 * @since 1.0.0
 */
public interface Generator<T> {

  /**
   * Returns a randomly generated value.
   */
  T generate();
}
