package io.github.vletard.analogy.set;

import java.util.HashSet;
import io.github.vletard.analogy.Relation;

public class SetRelation<Item, Subtype extends ImmutableSet<Item>> implements Relation {
  private final HashSet<Item> added, removed;


  public static <Item, Subtype extends ImmutableSet<Item>> SetRelation<Item, Subtype> newStraightRelation(SetEquation<Item, Subtype> equation) {
    return new SetRelation<Item, Subtype>(equation, false);
  }

  public static <Item, Subtype extends ImmutableSet<Item>> SetRelation<Item, Subtype> newCrossedRelation(SetEquation<Item, Subtype> equation) {
    return new SetRelation<Item, Subtype>(equation, true);
  }

  private SetRelation(SetEquation<Item, Subtype> equation, boolean crossed) {
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((added == null) ? 0 : added.hashCode());
    result = prime * result + ((removed == null) ? 0 : removed.hashCode());
    return result;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SetRelation other = (SetRelation) obj;
    if (added == null) {
      if (other.added != null)
        return false;
    } else if (!added.equals(other.added))
      return false;
    if (removed == null) {
      if (other.removed != null)
        return false;
    } else if (!removed.equals(other.removed))
      return false;
    return true;
  }
}
