package com.gmail.chickenpowerrr.chickentest;

import com.gmail.chickenpowerrr.chickentest.junit.ChickenTestExtension;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChickenTestExtension.class)
public class IntGeneratorTest {

  @RepeatedTest(100)
  public void generateIntegers(int value) {
    System.out.println(value);
  }
}
