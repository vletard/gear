package io.github.vletard.analogy.tuple;

import java.util.HashMap;

import io.github.vletard.analogy.RebuildException;
import io.github.vletard.analogy.Relation;
import io.github.vletard.analogy.Solution;
import io.github.vletard.analogy.SubtypeRebuilder;
import io.github.vletard.analogy.util.InvalidParameterException;

public class TupleSolution<E, T extends Tuple<E>> extends Solution<T> {
  private final Tuple<Solution<E>> solutionTuple;
  private final TupleRelation straightRelation, crossedRelation;

  public TupleSolution(Tuple<Solution<E>> solutionTuple, int degree, SubtypeRebuilder<Tuple<E>, T> rebuilder) throws RebuildException {
    super(extractContent(solutionTuple, rebuilder), degree);
    this.solutionTuple = solutionTuple;
    this.straightRelation = TupleRelation.newStraightRelation(this);
    this.crossedRelation = TupleRelation.newCrossedRelation(this);
  }

  private static <E, T extends Tuple<E>> T extractContent(Tuple<Solution<E>> solutionTuple, SubtypeRebuilder<Tuple<E>, T> rebuilder) throws RebuildException {
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

  @Override
  public Relation getStraightRelation() {
    return this.straightRelation;
  }

  @Override
  public Relation getCrossedRelation() {
    return this.crossedRelation;
  }
}
