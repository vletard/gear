package io.github.vletard.analogy.tuple;

import java.util.HashMap;
import java.util.Map;

import io.github.vletard.analogy.SubtypeRebuilder;

public abstract class SubTupleRebuilder<E, Subtype extends Tuple<E>> extends SubtypeRebuilder<Tuple<E>, Subtype> {
  private final Map<Object, SubtypeRebuilder<?, ?>> subordinates;
  private final SubtypeRebuilder<?, ?> defaultRebuilder;
  
  public SubTupleRebuilder(Map<Object, SubtypeRebuilder<?, ?>> subordinates) {
    this.subordinates = subordinates;
    this.defaultRebuilder = SubtypeRebuilder.identity();
  }
  
  public SubTupleRebuilder(SubtypeRebuilder<?, ?> defaultRebuilder) {
    this.subordinates = new HashMap<Object, SubtypeRebuilder<?, ?>>();
    this.defaultRebuilder = defaultRebuilder;
  }
  
  public SubTupleRebuilder(Map<Object, SubtypeRebuilder<?, ?>> subordinates, SubtypeRebuilder<?, ?> defaultRebuilder) {
    this.subordinates = subordinates;
    this.defaultRebuilder = defaultRebuilder;
  }
  
  public SubtypeRebuilder<?, ?> get(Object key) {
    return this.subordinates.getOrDefault(key, this.defaultRebuilder);
  }
  
  public static <E> SubTupleRebuilder<E, Tuple<E>> tupleIdentity() {
    return new SubTupleRebuilder<E, Tuple<E>>(new HashMap<Object, SubtypeRebuilder<?, ?>>()) {
      @Override
      public Tuple<E> rebuild(Tuple<E> object) {
        return object;
      }
    };
  }
}
