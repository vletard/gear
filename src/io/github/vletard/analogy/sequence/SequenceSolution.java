package io.github.vletard.analogy.sequence;

import java.util.List;

import io.github.vletard.analogy.Solution;

public class SequenceSolution<T, Subtype extends Sequence<T>> extends Solution<Subtype> {

  private final List<Factor<T, Subtype>> factorization;
  // TODO add here recursive information about sub-solutions while implementing sequence analogical recursion

  public SequenceSolution(Subtype content, int degree, List<Factor<T, Subtype>> factorization) {
    super(content, degree);
    this.factorization = factorization;
  }

  public List<Factor<T, Subtype>> getFactorization() {
    return factorization;
  }

}
