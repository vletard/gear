package io.github.vletard.analogy.tuple;

import java.util.Collections;
import java.util.Iterator;

import io.github.vletard.analogy.DefaultEquation;
import io.github.vletard.analogy.Solution;

public class TupleEquation<T, Subtype extends Tuple<T>> extends DefaultEquation<Subtype, Solution<Subtype>> {

  private final SubTupleRebuilder<T, Subtype> rebuilder;
  
  public TupleEquation(Subtype a, Subtype b, Subtype c, SubTupleRebuilder<T, Subtype> rebuilder) {
    super(a, b, c);
    this.rebuilder = rebuilder;
  }

  protected SubTupleRebuilder<T, Subtype> getRebuilder() {
    return this.rebuilder;
  }

  @Override
  public Iterator<Solution<Subtype>> iterator() {
    if (this.a instanceof Tuple && this.b instanceof Tuple && this.c instanceof Tuple) {
      return new TupleSolutionIterator<T, Subtype>(this);
    }
    else
      return Collections.emptyIterator();
  }

  @Override
  public TupleEquation<T, Subtype> dual() {
    return new TupleEquation<T, Subtype>(this.a, this.c, this.b, this.rebuilder);
  }
}
