package io.github.vletard.analogy.set;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import io.github.vletard.analogy.DefaultEquation;
import io.github.vletard.analogy.AtomicEquation;
import io.github.vletard.analogy.NoSolutionException;
import io.github.vletard.analogy.SubtypeRebuilder;
import io.github.vletard.analogy.Solution;

public class SetEquation<Item, Subtype extends ImmutableSet<Item>> extends DefaultEquation<Subtype, Solution<Subtype>> {

  private final SubtypeRebuilder<ImmutableSet<Item>, Subtype> rebuilder;
  
  public SetEquation(Subtype a, Subtype b, Subtype c, SubtypeRebuilder<ImmutableSet<Item>, Subtype> rebuilder) {
    super(a, b, c);
    this.rebuilder = rebuilder;
  }
  
  @Override
  public Iterator<Solution<Subtype>> iterator() {
    HashSet<Item> union = new HashSet<Item>();
    union.addAll(this.a.asSet());
    union.addAll(this.b.asSet());
    union.addAll(this.c.asSet());
    try {
      HashSet<Item> solution = new HashSet<Item>();
      for (Item item: union)
        if (new AtomicEquation<Boolean>(a.contains(item), b.contains(item), c.contains(item)).getSolution().getContent())
          solution.add(item);
      
      return Collections.singleton(new Solution<Subtype>(rebuilder.rebuild(new ImmutableSet<Item>(solution)), 1)).iterator();
    } catch (NoSolutionException e) { // if one of the objects does not respect the analogical constraint, the equation fails
      return Collections.emptyIterator();
    }
  }
}
