package io.github.vletard.analogy.set;

import java.util.HashSet;
import io.github.vletard.analogy.Relation;

public class SetRelation<Item, Subtype extends ImmutableSet<Item>> implements Relation {
  private final SetEquation<Item, Subtype> equation;
  private final HashSet<Item> added, removed;


  public static <Item, Subtype extends ImmutableSet<Item>> SetRelation<Item, Subtype> newStraightRelation(SetEquation<Item, Subtype> equation) {
    return new SetRelation<Item, Subtype>(equation, false);
  }

  public static <Item, Subtype extends ImmutableSet<Item>> SetRelation<Item, Subtype> newCrossedRelation(SetEquation<Item, Subtype> equation) {
    return new SetRelation<Item, Subtype>(equation, true);
  }

  private SetRelation(SetEquation<Item, Subtype> equation, boolean crossed) {
    this.equation = equation;
    this.added = new HashSet<Item>();
    this.removed = new HashSet<Item>();
    
    if (crossed) {
      for (Item i: equation.c)
        if (!equation.a.contains(i))
          this.added.add(i);
      
      for (Item i: equation.a)
        if (!equation.c.contains(i))
          this.removed.add(i);
    } else {
      for (Item i: equation.b) 
        if (!equation.a.contains(i))
          this.added.add(i);
      
      for (Item i: equation.a)
        if (!equation.b.contains(i))
          this.removed.add(i);
    }
  }

  @Override
  public String toString() {
    String output = "[";

    for (Item i: this.added)
      output += " +" + i;
    for (Item i: this.removed)
      output += " -" + i;
    
    return output + "]";
  }

  @Override
  public boolean isIdentity() {
    return this.added.isEmpty() && this.removed.isEmpty();
  }
}
