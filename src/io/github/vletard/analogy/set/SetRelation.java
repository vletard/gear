package io.github.vletard.analogy.set;

import java.util.HashSet;
import io.github.vletard.analogy.Relation;

public class SetRelation<Item, Subtype extends ImmutableSet<Item>> implements Relation {
  private final SetEquation<Item, Subtype> equation;
  private final SetSolution<Item, Subtype> solution;
  private final HashSet<Item> straightAdd, straightRemove, crossedAdd, crossedRemove;
  

  public SetRelation(SetEquation<Item, Subtype> equation, SetSolution<Item, Subtype> solution) {
    this.equation = equation;
    this.straightAdd = new HashSet<Item>();
    this.straightRemove = new HashSet<Item>();
    this.crossedAdd = new HashSet<Item>();
    this.crossedRemove = new HashSet<Item>();
    this.solution = solution;
    
    for (Item i: solution.getContent()) {
      if (!equation.c.contains(i))
        this.straightAdd.add(i);
      if (!equation.b.contains(i))
        this.crossedAdd.add(i);
    }
    
    for (Item i: equation.a) {
      if (!equation.b.contains(i))
        this.straightRemove.add(i);
      if (!equation.c.contains(i))
        this.crossedRemove.add(i);
    }
  }

  @Override
  public Relation dual() {
    return new SetRelation<Item, Subtype>(this.equation.dual(), this.solution);
  }

  @Override
  public String displayStraight() {
    String output = "[";

    for (Item i: this.straightAdd)
      output += " +" + i;
    for (Item i: this.straightRemove)
      output += " -" + i;
    
    return output + "]";
  }

  @Override
  public String displayCrossed() {
    String output = "[";

    for (Item i: this.crossedAdd)
      output += " +" + i;
    for (Item i: this.crossedRemove)
      output += " -" + i;
    
    return output + "]";
  }

  @Override
  public boolean isIdentityStraight() {
    return this.straightAdd.isEmpty() && this.straightRemove.isEmpty();
  }

  @Override
  public boolean isIdentityCrossed() {
    return this.crossedAdd.isEmpty() && this.crossedRemove.isEmpty();
  }
}
