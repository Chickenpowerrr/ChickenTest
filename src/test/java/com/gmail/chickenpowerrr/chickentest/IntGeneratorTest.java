package com.gmail.chickenpowerrr.chickentest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.gmail.chickenpowerrr.chickentest.generator.number.IntRange;
import com.gmail.chickenpowerrr.chickentest.junit.ChickenTestExtension;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChickenTestExtension.class)
public class IntGeneratorTest {

  @RepeatedTest(100)
  public void generateInt(int value) {
    assertThat(value).isBetween(IntRange.DEFAULT_MIN, IntRange.DEFAULT_MAX);
  }

  @RepeatedTest(100)
  public void generateIntInRange(@IntRange(min = -10, max = 10) int value) {
    assertThat(value).isBetween(-10, 10);
  }

  @RepeatedTest(100)
  public void generateInteger(Integer value) {
    assertThat(value).isBetween(IntRange.DEFAULT_MIN, IntRange.DEFAULT_MAX);
  }

  @RepeatedTest(100)
  public void generateIntegerInRange(@IntRange(min = -10, max = 10) Integer value) {
    assertThat(value).isBetween(-10, 10);
  }
}
