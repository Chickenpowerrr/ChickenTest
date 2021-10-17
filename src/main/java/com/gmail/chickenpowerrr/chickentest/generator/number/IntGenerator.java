package com.gmail.chickenpowerrr.chickentest.generator.number;

import com.gmail.chickenpowerrr.chickentest.generator.Generator;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.extension.ParameterContext;

public class IntGenerator implements Generator<Integer> {

  private final int min;
  private final int max;

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

  @Override
  public Integer generate() {
    return ThreadLocalRandom.current().nextInt(max - min) + min;
  }
}
