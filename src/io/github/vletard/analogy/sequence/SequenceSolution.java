package io.github.vletard.analogy.sequence;

import io.github.vletard.analogy.Solution;

public class SequenceSolution<T, Subtype extends Sequence<T>> extends Solution<Subtype> {

  private final Factorization<T, Subtype> factorization;
  // TODO add here recursive information about sub-solutions while implementing sequence analogical recursion
  private final SequenceRelation<T, Subtype> relation;

  public SequenceSolution(Subtype content, int degree, Factorization<T, Subtype> factorization) {
    super(content, degree);
    this.factorization = factorization;
    this.relation = new SequenceRelation<T, Subtype>(factorization);
  }

  public Factorization<T, Subtype> getFactorization() {
    return factorization;
  }

  @Override
  public SequenceRelation<T, Subtype> getRelation() {
    return this.relation;
  }
}
