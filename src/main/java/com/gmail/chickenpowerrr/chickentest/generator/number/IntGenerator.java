package com.gmail.chickenpowerrr.chickentest.generator.number;

import com.gmail.chickenpowerrr.chickentest.generator.Generator;
import com.gmail.chickenpowerrr.chickentest.generator.annotations.GeneratorDescription;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.extension.ParameterContext;

/**
 * Generates a random value between
 * min (included) and max (excluded).
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
@GeneratorDescription({Integer.class, int.class})
public class IntGenerator implements Generator<Integer> {

  private final int min;
  private final int max;

  /**
   * Sets the min and max equal to the values in the
   * {@link IntRange} annotation for the value to be inserted.
   * If it is not present, it will use the default values for
   * the {@link IntRange}.
   *
   * @param parameterContext the context of the value to be inserted
   */
  public IntGenerator(ParameterContext parameterContext) {
    var intRange = parameterContext.findAnnotation(IntRange.class);
    if (intRange.isPresent()) {
      this.min = intRange.get().min();
      this.max = intRange.get().max();
    } else {
      this.min = IntRange.DEFAULT_MIN;
      this.max = IntRange.DEFAULT_MAX;
    }
  }

  /**
   * Returns a random value between min (included) and max (excluded).
   */
  @Override
  public Integer generate() {
    return ThreadLocalRandom.current().nextInt(max - min) + min;
  }
}
