# ChickenTest
It can be quite hard to write good test cases for some algorithms, 
while it is easy to check whether the output is correct for the given input. 
For this reason ChickenTest  can generate random inputs for tests and 
adds more advanced assertions, to allow for better testing of the 
aforementioned type of algorithms.

## Features
### Randomized Test Input
The main feature of ChickenTest are the randomized tests.
A test case can ask for a random value in its parameters
and it if there is a Generator available for that type it
will be filled with a random value.

```java
@ExtendWith(ChickenTestExtension.class)
public class DivisionTest {

  @RepeatedTest(100)
  public void divide(@IntRange(min = -1000, max = 1000) int value) {
    if (value % 2 == 0) {
      assertEquals(2 * value / 2, value);
    }
  }
}
```

There are built-in Generators, but you can also create your
own. You can do this by implementing the Generator interface,
adding the GeneratorDescription annotation. If the Generator
has a public constructor with only a ParameterContext as an
argument, it is possible to feed it to the GeneratorScanner.

```java
@GeneratorDescription(Custom.class)
public class CustomGenerator implements Generator<Custom> {

  public VertexGenerator(ParameterContext parameterContext) {
    
  }

  @Override
  public Custom generate() {
    return new Custom();
  }
}
```

```java
@ExtendWith(ChickenTestExtension.class)
public class CustomTest {

  @BeforeAll
  public static void beforeAll(GeneratorScanner generatorScanner) {
    generatorScanner.addGenerator(CustomGenerator.class);
  }
}
```

### Assertions on relations
With random examples, it is common that you want to express relations between
certain assertions. For example, in the DivisionTest above, there is a special
if statement to make sure that only the even numbers are checked.

For this reason, ChickenTest also comes with assertions on relations. The DivisionTest
could for example also be written as:

```java
@ExtendWith(ChickenTestExtension.class)
public class DivisionTest {

  @RepeatedTest(100)
  public void divide(@IntRange(min = -1000, max = 1000) int value) {
    assertRelation(() -> assertEquals(0, value % 2))
        .implies(() -> assertEquals(value, 2 * value / 2)).evaluate();
  }
}
```

For the DivisionTest, this is of course just another way of writing the 
if statement, but in other cases, it might be necessary. Consider for 
example the case where the provided input is either prime or composite.
In this case we are looking for a way to verify that exactly one of those
things holds. We do this by using the exclusiveOr relation. For example:

```java
@ExtendWith(ChickenTestExtension.class)
public class PrimeTest {

  @RepeatedTest(100)
  public void divide(@IntRange(min = 2) int value) {
    assertRelation(() -> assertTrue(isPrime(value)))
        .exclusiveOr(() -> assertTrue(isComposite(value))).evaluate();
  }
}
```

## Examples
### Custom Generator
For an example of a Custom Generator, you can go to the 
[Depth-First Search](src/test/java/com/gmail/chickenpowerrr/chickentest/examples/dfs)
example. In this example both an iterative and recursive approach are run
on a randomly generated graph. Their output is then compared.

You can find the Depth-First Search example 
[here](src/test/java/com/gmail/chickenpowerrr/chickentest/examples/dfs).

### Relation Assertion
For an example of how to use the relation assertion, you can go to the
[Modular Multiplicative Inverse](src/test/java/com/gmail/chickenpowerrr/chickentest/examples/modular)
example. In this example an exclusive or test is used to test whether
the calculated modular multiplicative inverse 
(https://en.wikipedia.org/wiki/Modular_multiplicative_inverse) 
is correct.

You can find the Modular Multiplicative Inverse example
[here](src/test/java/com/gmail/chickenpowerrr/chickentest/examples/modular).

## Prerequisites
To use ChickenTest, there are only three things you need in your project:
- Java 17 or greater
- Maven
- JUnit 5