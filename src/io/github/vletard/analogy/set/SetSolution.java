package io.github.vletard.analogy.set;

import io.github.vletard.analogy.Solution;

public class SetSolution<E, T extends ImmutableSet<E>> extends Solution<T> {
  private final SetRelation<E, T> straightRelation, crossedRelation;

  public SetSolution(T content, int degree, SetEquation<E, T> equation) {
    super(content, degree);
    this.straightRelation = SetRelation.newStraightRelation(equation);
    this.crossedRelation = SetRelation.newCrossedRelation(equation);
  }

  @Override
  public SetRelation<E, T> getStraightRelation() {
    return this.straightRelation;
  }
  
  @Override
  public SetRelation<E, T> getCrossedRelation() {
    return this.crossedRelation;
  }
}
