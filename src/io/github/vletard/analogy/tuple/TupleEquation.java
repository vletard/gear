package io.github.vletard.analogy.tuple;

import java.util.Collections;
import java.util.Iterator;

import io.github.vletard.analogy.DefaultEquation;
import io.github.vletard.analogy.Solution;

public class TupleEquation<T> extends DefaultEquation<Tuple<T>, Solution<Tuple<T>>> {

  public TupleEquation(Tuple<T> a, Tuple<T> b, Tuple<T> c) {
    super(a, b, c);
  }

  @Override
  public Iterator<Solution<Tuple<T>>> iterator() {
    if (this.a instanceof Tuple && this.b instanceof Tuple && this.c instanceof Tuple) {
      return new TupleSolutionIterator<T>(this.a, this.b, this.c);
    }
    else
      return Collections.emptyIterator();
  }
}
