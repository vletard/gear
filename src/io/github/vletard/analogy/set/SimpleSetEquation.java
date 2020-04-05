package io.github.vletard.analogy.set;

import io.github.vletard.analogy.SubtypeRebuilder;

public class SimpleSetEquation<T> extends SetEquation<T, ImmutableSet<T>> {
  public SimpleSetEquation(ImmutableSet<T> a, ImmutableSet<T> b, ImmutableSet<T> c) {
    super(a, b, c, SubtypeRebuilder.identity());
  }
}
