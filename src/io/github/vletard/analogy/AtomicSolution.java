package io.github.vletard.analogy;

public class AtomicSolution<T> extends Solution<T> {
  private final AtomicRelation relation;
  
  public AtomicSolution(T content, int degree, AtomicEquation<T> equation) {
    super(content, degree);
    this.relation = new AtomicRelation(equation);
  }

  @Override
  public AtomicRelation getRelation() {
    return this.relation;
  }

}
