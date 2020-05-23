package io.github.vletard.analogy.tuple;

public class SimpleTupleEquation<T> extends TupleEquation<T, Tuple<T>> {
  public SimpleTupleEquation(Tuple<T> a, Tuple<T> b, Tuple<T> c) {
    super(a, b, c, SubTupleRebuilder.tupleIdentity());
  }
}
