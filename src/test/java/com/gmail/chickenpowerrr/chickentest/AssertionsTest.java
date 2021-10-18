package com.gmail.chickenpowerrr.chickentest;

import static com.gmail.chickenpowerrr.chickentest.assertions.Assertions.assertRelation;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

import com.gmail.chickenpowerrr.chickentest.assertions.exception.RelationException;
import org.junit.jupiter.api.Test;

public class AssertionsTest {

  @Test
  public void andRelation_firstErrorSecondError_fails() {
    assertThatThrownBy(() ->
        assertRelation(() -> fail("Fail")).and(() -> fail("Fail")).evaluate())
        .isInstanceOf(RelationException.class);
  }

  @Test
  public void andRelation_firstErrorSecondNormal_fails() {
    assertThatThrownBy(() ->
        assertRelation(() -> fail("Fail")).and(() -> {}).evaluate())
        .isInstanceOf(RelationException.class);
  }

  @Test
  public void andRelation_firstNormalSecondError_fails() {
    assertThatThrownBy(() ->
        assertRelation(() -> {}).and(() -> fail("Fail")).evaluate())
        .isInstanceOf(RelationException.class);
  }

  @Test
  public void andRelation_firstNormalSecondNormal_passes() {
    assertRelation(() -> {}).and(() -> {}).evaluate();
  }

  @Test
  public void orRelation_firstErrorSecondError_fails() {
    assertThatThrownBy(() ->
        assertRelation(() -> fail("Fail")).or(() -> fail("Fail")).evaluate())
        .isInstanceOf(RelationException.class);
  }

  @Test
  public void orRelation_firstErrorSecondNormal_passes() {
    assertRelation(() -> fail("Fail")).or(() -> {}).evaluate();
  }

  @Test
  public void orRelation_firstNormalSecondError_passes() {
    assertRelation(() -> {}).or(() -> fail("Fail")).evaluate();
  }

  @Test
  public void orRelation_firstNormalSecondNormal_passes() {
    assertRelation(() -> {}).or(() -> {}).evaluate();
  }

  @Test
  public void exclusiveOrRelation_firstErrorSecondError_fails() {
    assertThatThrownBy(() ->
        assertRelation(() -> fail("Fail")).exclusiveOr(() -> fail("Fail")).evaluate())
        .isInstanceOf(RelationException.class);
  }

  @Test
  public void exclusiveOrRelation_firstErrorSecondNormal_passes() {
    assertRelation(() -> fail("Fail")).exclusiveOr(() -> {}).evaluate();
  }

  @Test
  public void exclusiveOrRelation_firstNormalSecondError_passes() {
    assertRelation(() -> {}).exclusiveOr(() -> fail("Fail")).evaluate();
  }

  @Test
  public void exclusiveOrRelation_firstNormalSecondNormal_fails() {
    assertThatThrownBy(() ->
        assertRelation(() -> {}).exclusiveOr(() -> {}).evaluate())
        .isInstanceOf(RelationException.class);
  }

  @Test
  public void impliesRelation_firstErrorSecondError_passes() {
    assertRelation(() -> fail("Fail")).implies(() -> fail("Fail")).evaluate();
  }

  @Test
  public void impliesRelation_firstErrorSecondNormal_passes() {
    assertRelation(() -> fail("Fail")).implies(() -> {}).evaluate();
  }

  @Test
  public void impliesRelation_firstNormalSecondError_fails() {
    assertThatThrownBy(() ->
        assertRelation(() -> {}).implies(() -> fail("Fail")).evaluate())
        .isInstanceOf(RelationException.class);
  }

  @Test
  public void impliesRelation_firstNormalSecondNormal_passes() {
    assertRelation(() -> {}).implies(() -> {}).evaluate();
  }
}
