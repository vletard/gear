package io.github.vletard.analogy.tuple;

import java.util.HashMap;
import java.util.Map;

import io.github.vletard.analogy.Relation;
import io.github.vletard.analogy.util.InvalidParameterException;

public class TupleRelation extends Tuple<Relation> implements Relation {
  private static final long serialVersionUID = 5416368724720335976L;

  private TupleRelation(Map<Object, Relation> regularMap, Map<Object, Relation> freeMap) throws InvalidParameterException {
    super(regularMap, freeMap);
  }

  public static TupleRelation newStraightRelation(TupleSolution<?, ?> solution) {
    Map<Object, Relation> regularMap = new HashMap<Object, Relation>();
    for (Object key: solution.getSubSolutions().regularKeys())
      regularMap.put(key, solution.getSubSolutions().get(key).getStraightRelation());

    Map<Object, Relation> freeMap = new HashMap<Object, Relation>();
    for (Object key: solution.getSubSolutions().freeKeys())
      freeMap.put(key, solution.getSubSolutions().get(key).getStraightRelation());
    
    try {
      return new TupleRelation(regularMap, freeMap);
    } catch (InvalidParameterException e) {
      throw new RuntimeException("Unexpected exception.", e);
    }
  }

  public static TupleRelation newCrossedRelation(TupleSolution<?, ?> solution) {
    Map<Object, Relation> regularMap = new HashMap<Object, Relation>();
    for (Object key: solution.getSubSolutions().regularKeys())
      regularMap.put(key, solution.getSubSolutions().get(key).getCrossedRelation());

    Map<Object, Relation> freeMap = new HashMap<Object, Relation>();
    for (Object key: solution.getSubSolutions().freeKeys())
      freeMap.put(key, solution.getSubSolutions().get(key).getCrossedRelation());
    
    try {
      return new TupleRelation(regularMap, freeMap);
    } catch (InvalidParameterException e) {
      throw new RuntimeException("Unexpected exception.", e);
    }
  }

  @Override
  public String toString() {
    String str = "<";
    boolean first = true;
    for (Object key: this.regularKeys()) {
      if (! this.get(key).isIdentity()) {
        if (first)
          first = false;
        else
          str += ", ";
        str += key + "=" + this.get(key);
      }
    }

    if (this.freeKeys().size() > 0) {
      str += " / ";
      first = true;
      for (Object key: this.freeKeys()) {
        if (! this.get(key).isIdentity()) {
          if (first)
            first = false;
          else
            str += ", ";
          str += key + "=" + this.get(key);
        }
      }
    }
    return str + ">";
  }

  @Override
  public boolean isIdentity() {
    for (Object k: this.keySet()) {
      if (!this.get(k).isIdentity())
        return false;
    }
    return true;
  }
}
