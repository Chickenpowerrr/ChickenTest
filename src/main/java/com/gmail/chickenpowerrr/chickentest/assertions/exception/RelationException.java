package com.gmail.chickenpowerrr.chickentest.assertions.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serial;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * Represents that the final evaluation of the relation
 * is false. It makes sure that the underlying {@link AssertionError}s
 * are also printed to simplify debugging.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public class RelationException extends AssertionError {

  @Serial
  private static final long serialVersionUID = 1472082949946180159L;

  private final Map<Integer, AssertionError> errors;

  /**
   * @param sentence the textual representation of the relation
   * @param errors the id of the statements that threw an error
   *               as the key and the error as the value
   */
  public RelationException(String sentence, Map<Integer, AssertionError> errors) {
    super("The following relation did not hold: " + sentence);

    this.errors = errors;
  }

  @Override
  public void printStackTrace(PrintWriter printWriter) {
    printStackTrace(printWriter::println, throwable -> throwable.printStackTrace(printWriter));
  }

  @Override
  public void printStackTrace(PrintStream printStream) {
    printStackTrace(printStream::println, throwable -> throwable.printStackTrace(printStream));
  }

  private void printStackTrace(Consumer<String> printLn, Consumer<Throwable> printStackTrace) {
    printLn.accept(toString());
    Queue<Integer> errorIds = new PriorityQueue<>(errors.keySet());

    while (!errorIds.isEmpty()) {
      int errorId = errorIds.poll();
      printLn.accept("Error for statement " + errorId + ":");
      printStackTrace.accept(errors.get(errorId));
    }
  }
}
