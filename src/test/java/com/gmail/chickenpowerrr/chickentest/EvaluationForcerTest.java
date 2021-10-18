package com.gmail.chickenpowerrr.chickentest;

import static com.gmail.chickenpowerrr.chickentest.assertions.Assertions.assertRelation;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.gmail.chickenpowerrr.chickentest.assertions.exception.NotEvaluatedException;
import com.gmail.chickenpowerrr.chickentest.junit.ChickenTestExtension;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChickenTestExtension.class)
public class EvaluationForcerTest {

  @Test
  public void relation_withEvaluation_doesNotThrow() {
    assertRelation(() -> assertThat(5).isEqualTo(5)).evaluate();
  }

  @Test
  public void relation_withoutEvaluation_doesThrow() throws IOException, ClassNotFoundException {
    ChickenTestExtension chickenTestExtension = new ChickenTestExtension();

    assertRelation(() -> assertThat(5).isEqualTo(5));

    assertThatThrownBy(() ->
        chickenTestExtension.afterEach(null))
        .isInstanceOf(NotEvaluatedException.class);
  }
}
