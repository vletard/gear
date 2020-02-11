package io.github.vletard.analogy;

import java.util.Iterator;

import io.github.vletard.analogy.sequence.Sequence;
import io.github.vletard.analogy.sequence.SequenceEquation;
import io.github.vletard.analogy.set.ImmutableSet;
import io.github.vletard.analogy.set.SetEquation;
import io.github.vletard.analogy.tuple.Tuple;
import io.github.vletard.analogy.tuple.TupleEquation;

public class DefaultEquation<T, S extends Solution<? extends T>> extends AbstractEquation<T, S> {

  public DefaultEquation(T a, T b, T c) {
    super(a, b, c);
  }

  @Override
  public Iterator<S> iterator() {
    if (this.a instanceof ImmutableSet && this.b instanceof ImmutableSet && this.c instanceof ImmutableSet)
      return (Iterator<S>) new SetEquation<Object>((ImmutableSet<Object>) this.a, (ImmutableSet<Object>) this.b, (ImmutableSet<Object>) this.c).iterator();
    else if (this.a instanceof Tuple && this.b instanceof Tuple && this.c instanceof Tuple)
      return (Iterator<S>) new TupleEquation<Object>((Tuple<Object>) this.a, (Tuple<Object>) this.b, (Tuple<Object>) this.c).iterator();
    else if (this.a instanceof Sequence && this.b instanceof Sequence && this.c instanceof Sequence)
      return (Iterator<S>) new SequenceEquation<Object>((Sequence<Object>) this.a, (Sequence<Object>) this.b, (Sequence<Object>) this.c).iterator();
    else
      return (Iterator<S>) new AtomicEquation<T>(this.a, this.b, this.c).iterator();
  }
}
