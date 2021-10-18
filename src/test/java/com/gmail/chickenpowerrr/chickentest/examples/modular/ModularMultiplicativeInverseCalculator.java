package com.gmail.chickenpowerrr.chickentest.examples.modular;

public class ModularMultiplicativeInverseCalculator {

  public Integer multiplicativeInverse(int a, int modulo) {
    ExtendedEuclideanResult euclideanResult = extendedEuclidean(a, modulo);

    if (euclideanResult.gcd() != 1) {
      return null;
    }

    return euclideanResult.x();
  }

  private ExtendedEuclideanResult extendedEuclidean(int a, int b) {
    if (a == 0) {
      return new ExtendedEuclideanResult(b, 0, 1);
    }

    ExtendedEuclideanResult result = extendedEuclidean(b % a, a);
    return new ExtendedEuclideanResult(result.gcd(), result.y() - (b / a) * result.x(), result.x());
  }

  private record ExtendedEuclideanResult(int gcd, int x, int y) {

  }
}
