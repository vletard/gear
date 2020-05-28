package io.github.vletard.analogy.sequence;

import io.github.vletard.analogy.Relation;
import io.github.vletard.analogy.Solution;

public class SequenceSolution<T, Subtype extends Sequence<T>> extends Solution<Subtype> {
  private final Factorization<T, Subtype> factorization;
  // TODO add here recursive information about sub-solutions while implementing sequence analogical recursion
  private final SequenceRelation<T, Subtype> straightRelation, crossedRelation;

  public SequenceSolution(Subtype content, int degree, Factorization<T, Subtype> factorization) {
    super(content, degree);
    this.factorization = factorization;
    this.straightRelation = SequenceRelation.newStraightRelation(factorization);
    this.crossedRelation = SequenceRelation.newCrossedRelation(factorization);
  }

  public Factorization<T, Subtype> getFactorization() {
    return factorization;
  }

  @Override
  public Relation getStraightRelation() {
    return this.straightRelation;
  }

  @Override
  public Relation getCrossedRelation() {
    return this.crossedRelation;
  }
}
