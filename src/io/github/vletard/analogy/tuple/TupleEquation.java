package io.github.vletard.analogy.tuple;

import java.util.Collections;
import java.util.Iterator;

import io.github.vletard.analogy.AbstractEquation;

public class TupleEquation<T> extends AbstractEquation<Tuple<T>, TupleSolution<T>> {

  public TupleEquation(Tuple<T> a, Tuple<T> b, Tuple<T> c) {
    super(a, b, c);
  }

  @Override
  public Iterator<TupleSolution<T>> iterator() {
    if (this.a instanceof Tuple && this.b instanceof Tuple && this.c instanceof Tuple) {
      return new TupleSolutionIterator<T>(this.a, this.b, this.c);
    }
    else
      return Collections.emptyIterator();
  }
}
