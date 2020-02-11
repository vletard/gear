package io.github.vletard.analogy.set;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import io.github.vletard.analogy.AbstractEquation;
import io.github.vletard.analogy.AtomicEquation;
import io.github.vletard.analogy.NoSolutionException;
import io.github.vletard.analogy.Solution;

public class SetEquation<T> extends AbstractEquation<ImmutableSet<T>, Solution<ImmutableSet<T>>> {

  public SetEquation(ImmutableSet<T> a, ImmutableSet<T> b, ImmutableSet<T> c) {
    super(a, b, c);
  }

  @Override
  public Iterator<Solution<ImmutableSet<T>>> iterator() {
    HashSet<T> union = new HashSet<T>();
    union.addAll(this.a.asSet());
    union.addAll(this.b.asSet());
    union.addAll(this.c.asSet());
    HashSet<T> solution = new HashSet<T>();
    for (T item: union) {
      try {
        if (new AtomicEquation<Boolean>(a.contains(item), b.contains(item), c.contains(item)).getSolution().getContent())
          solution.add(item);
      } catch (NoSolutionException e) { // if one of the objects does not respect the analogical constraint, the equation has no solution
        return Collections.emptyIterator();
      }
    }
    return Collections.singleton(new Solution<ImmutableSet<T>>(new ImmutableSet<T>(solution), 1)).iterator();
  }
}
