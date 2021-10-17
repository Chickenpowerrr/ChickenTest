package com.gmail.chickenpowerrr.chickentest.generator.number;

import com.gmail.chickenpowerrr.chickentest.generator.restrictions.ParameterRestriction;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@ParameterRestriction
public @interface IntRange {

  int DEFAULT_MIN = 0;
  int DEFAULT_MAX = Integer.MAX_VALUE;

  int min() default DEFAULT_MIN;

  int max() default DEFAULT_MAX;
}
