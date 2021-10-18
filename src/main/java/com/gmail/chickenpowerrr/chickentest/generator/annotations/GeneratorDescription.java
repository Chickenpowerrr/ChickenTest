package com.gmail.chickenpowerrr.chickentest.generator.annotations;

import com.gmail.chickenpowerrr.chickentest.generator.Generator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The description of a {@link Generator} such that
 * it can be determined whether a certain type of
 * {@link Generator} can generate a certain value
 * without actually creating an instance.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneratorDescription {

  String DEFAULT_NAME = "NoNameProvided";
  int DEFAULT_PRIORITY = 1;

  String name() default DEFAULT_NAME;

  /**
   * Returns the priority. The priority is used to resolve
   * conflicts with multiple generators generating the same
   * types of values. A higher priority gives it more priority.
   */
  int priority() default DEFAULT_PRIORITY;

  /**
   * Returns the types of values that the {@link Generator}
   * can produce.
   */
  @SuppressWarnings("rawtypes")
  Class[] value();
}
