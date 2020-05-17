package io.github.vletard.analogy.tuple;

import java.util.HashMap;

import io.github.vletard.analogy.Solution;
import io.github.vletard.analogy.SubtypeRebuilder;
import io.github.vletard.analogy.util.InvalidParameterException;

public class TupleSolution<E, T extends Tuple<E>> extends Solution<T> {
  private final TupleEquation<E, T> equation;
  private final Tuple<Solution<E>> solutionTuple;
  private final TupleRelation relation;
  
  public TupleSolution(Tuple<Solution<E>> solutionTuple, int degree, TupleEquation<E, T> equation) {
    super(extractContent(solutionTuple, equation.getRebuilder()), degree);
    this.equation = equation;
    this.solutionTuple = solutionTuple;
    try {
      this.relation = new TupleRelation(this);
    } catch (InvalidParameterException e) {
      throw new RuntimeException("Unexpected exception.", e);
    }
  }
  
  private static <E, T extends Tuple<E>> T extractContent(Tuple<Solution<E>> solutionTuple, SubtypeRebuilder<Tuple<E>, T> rebuilder) {
    HashMap<Object, E> regular = new HashMap<Object, E>();
    for (Object key: solutionTuple.keySet())
      regular.put(key, solutionTuple.get(key).getContent());
    
    HashMap<Object, E> free = new HashMap<Object, E>();
    for (Object key: solutionTuple.freeKeys())
      regular.put(key, solutionTuple.get(key).getContent());
    try {
      return rebuilder.rebuild(new Tuple<E>(regular, free));
    } catch (InvalidParameterException e) {
      throw new RuntimeException("Unexpected exception.", e);
    }
  }
  
  protected Tuple<Solution<E>> getSubSolutions() {
    return this.solutionTuple;
  }

  public TupleSolution<E, T> dual() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }

  @Override
  public TupleRelation getRelation() {
    return this.relation;
  }
}
