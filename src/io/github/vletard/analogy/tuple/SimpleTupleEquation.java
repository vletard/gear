package io.github.vletard.analogy.tuple;

import io.github.vletard.analogy.SubtypeRebuilder;

public class SimpleTupleEquation<T> extends TupleEquation<T, Tuple<T>> {
  public SimpleTupleEquation(Tuple<T> a, Tuple<T> b, Tuple<T> c) {
    super(a, b, c, SubtypeRebuilder.identity());
  }
}
