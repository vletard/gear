package io.github.vletard.analogy;

public class AtomicSolution<T> extends Solution<T> {
  private final AtomicRelation straightRelation, crossedRelation;
  
  public AtomicSolution(T content, int degree, AtomicEquation<T> equation) {
    super(content, degree);
    this.straightRelation = AtomicRelation.newStraightRelation(equation);
    this.crossedRelation = AtomicRelation.newCrossedRelation(equation);
  }

  @Override
  public Relation getStraightRelation() {
    return straightRelation;
  }

  @Override
  public Relation getCrossedRelation() {
    return crossedRelation;
  }
}
