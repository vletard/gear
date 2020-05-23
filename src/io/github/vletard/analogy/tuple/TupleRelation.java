package io.github.vletard.analogy.tuple;

import java.util.HashMap;
import java.util.Map;

import io.github.vletard.analogy.Relation;
import io.github.vletard.analogy.Solution;
import io.github.vletard.analogy.util.InvalidParameterException;

public class TupleRelation extends Tuple<Relation> implements Relation {
  private static final long serialVersionUID = 5416368724720335976L;
  private final TupleSolution<?, ?> solution;

  public TupleRelation(TupleSolution<?, ?> solution) throws InvalidParameterException {
    super(extractRegular(solution), extractFree(solution));
    this.solution = solution;
  }
  
  private static HashMap<?, ? extends Relation> extractRegular(TupleSolution<?, ?> solution) {
    HashMap<Object, Relation> map = new HashMap<Object, Relation>();
    for (Object key: solution.getSubSolutions().regularKeys())
      map.put(key, solution.getSubSolutions().get(key).getRelation());
    return map;
  }
  
  private static HashMap<?, ? extends Relation> extractFree(TupleSolution<?, ?> solution) {
    HashMap<Object, Relation> map = new HashMap<Object, Relation>();
    for (Object key: solution.getSubSolutions().freeKeys())
      map.put(key, solution.getSubSolutions().get(key).getRelation());
    return map;
  }
  
  @Override
  public TupleRelation dual() {
    try {
      return new TupleRelation(this.solution.dual());
    } catch (InvalidParameterException e) {
      throw new RuntimeException("Unexpected exception.", e);
    }
  }

  @Override
  public String displayStraight() {
    String str = "<";
    boolean first = true;
    for (Object key: this.regularKeys()) {
      if (first)
        first = false;
      else
        str += ", ";
      str += this.get(key).displayStraight();
    }

    str += " / ";
    first = true;
    for (Object key: this.freeKeys()) {
      if (first)
        first = false;
      else
        str += ", ";
      str += this.get(key).displayStraight();
    }
    return str + ">";
  }

  @Override
  public String displayCrossed() {
    String str = "<";
    boolean first = true;
    for (Object key: this.keySet()) {
      if (first)
        first = false;
      else
        str += ", ";
      str += this.get(key).displayCrossed();
    }

    str += " / ";
    first = true;
    for (Object key: this.freeKeys()) {
      if (first)
        first = false;
      else
        str += ", ";
      str += this.get(key).displayCrossed();
    }
    return str + ">";
  }
}
