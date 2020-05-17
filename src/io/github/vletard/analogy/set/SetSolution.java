package io.github.vletard.analogy.set;

import io.github.vletard.analogy.Solution;

public class SetSolution<E, T extends ImmutableSet<E>> extends Solution<T> {
  private final SetRelation<E, T> relation;

  public SetSolution(T content, int degree, SetEquation<E, T> equation) {
    super(content, degree);
    this.relation = new SetRelation<E, T>(equation, this);
  }

  @Override
  public SetRelation<E, T> getRelation() {
    return this.relation;
  }
}
