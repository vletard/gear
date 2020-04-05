package io.github.vletard.analogy.sequence;

import io.github.vletard.analogy.SubtypeRebuilder;

public class SimpleSequenceEquation<T> extends SequenceEquation<T, Sequence<T>> {

  public SimpleSequenceEquation(Sequence<T> a, Sequence<T> b, Sequence<T> c) {
    super(a, b, c, SubtypeRebuilder.identity());
  }

}
