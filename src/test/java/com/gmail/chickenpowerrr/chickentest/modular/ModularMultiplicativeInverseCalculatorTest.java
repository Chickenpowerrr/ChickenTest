package com.gmail.chickenpowerrr.chickentest.modular;

import static com.gmail.chickenpowerrr.chickentest.assertions.Assertions.assertRelation;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.gmail.chickenpowerrr.chickentest.generator.number.IntRange;
import com.gmail.chickenpowerrr.chickentest.junit.ChickenTestExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChickenTestExtension.class)
public class ModularMultiplicativeInverseCalculatorTest {

  private ModularMultiplicativeInverseCalculator calculator;

  @BeforeEach
  public void setUp() {
    this.calculator = new ModularMultiplicativeInverseCalculator();
  }

  @RepeatedTest(100)
  public void multiplicativeInverse(@IntRange(min = 1, max = 1000) int a,
      @IntRange(min = 1, max = 2000) int modulo) {
    Integer result = calculator.multiplicativeInverse(a, modulo);

    assertRelation(() -> assertThat(isMultiplicativeInverse(result, a, modulo)).isTrue())
        .exclusiveOr(() -> assertThat(result).isNull()).evaluate();
  }

  private boolean isMultiplicativeInverse(Integer candidateInverse, int a, int modulo) {
    if (candidateInverse == null) {
      return false;
    }

    if (candidateInverse >= 0) {
      return (candidateInverse * a) % modulo == 1;
    }

    return isMultiplicativeInverse((candidateInverse % modulo) + modulo, a, modulo);
  }
}
