package io.github.vletard.analogy.tuple;

import java.util.Collections;
import java.util.Iterator;

import io.github.vletard.analogy.DefaultEquation;
import io.github.vletard.analogy.Solution;
import io.github.vletard.analogy.SubtypeRebuilder;

public class TupleEquation<T, Subtype extends Tuple<T>> extends DefaultEquation<Subtype, Solution<Subtype>> {

  private final SubtypeRebuilder<Tuple<T>, Subtype> rebuilder;
  
  public TupleEquation(Subtype a, Subtype b, Subtype c, SubtypeRebuilder<Tuple<T>, Subtype> rebuilder) {
    super(a, b, c);
    this.rebuilder = rebuilder;
  }

  @Override
  public Iterator<Solution<Subtype>> iterator() {
    if (this.a instanceof Tuple && this.b instanceof Tuple && this.c instanceof Tuple) {
      return new TupleSolutionIterator<T, Subtype>(this.a, this.b, this.c, rebuilder);
    }
    else
      return Collections.emptyIterator();
  }
}
