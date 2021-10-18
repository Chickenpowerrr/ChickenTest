package com.gmail.chickenpowerrr.chickentest.junit;

import java.io.Serial;

/**
 * Thrown when someone uses ChickenTest features without adding
 * the {@link ChickenTestExtension} to the test class.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public class PluginNotEnabledException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 5970265583508341424L;

  public PluginNotEnabledException() {
    super("You should add '@ExtendWith(ChickenTestExtension.class)' at the top of a test class "
        + "before using ChickenTest features");
  }
}
