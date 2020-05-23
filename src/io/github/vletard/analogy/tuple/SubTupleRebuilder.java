package io.github.vletard.analogy.tuple;

import java.util.HashMap;
import java.util.Map;

import io.github.vletard.analogy.SubtypeRebuilder;

public abstract class SubTupleRebuilder<E, Subtype extends Tuple<E>> extends SubtypeRebuilder<Tuple<E>, Subtype> {
  private final Map<Object, SubtypeRebuilder<E, ? extends E>> subordinates;
  private final SubtypeRebuilder<E, ? extends E> defaultRebuilder;
  
  public SubTupleRebuilder(Map<Object, SubtypeRebuilder<E, ? extends E>> subordinates) {
    this.subordinates = subordinates;
    this.defaultRebuilder = SubtypeRebuilder.identity();
  }
  
  public SubTupleRebuilder(SubtypeRebuilder<E, ? extends E> defaultRebuilder) {
    this.subordinates = new HashMap<Object, SubtypeRebuilder<E,? extends E>>();
    this.defaultRebuilder = defaultRebuilder;
  }
  
  public SubTupleRebuilder(Map<Object, SubtypeRebuilder<E, ? extends E>> subordinates, SubtypeRebuilder<E, ? extends E> defaultRebuilder) {
    this.subordinates = subordinates;
    this.defaultRebuilder = defaultRebuilder;
  }
  
  public SubtypeRebuilder<E, ? extends E> get(Object key) {
    return this.subordinates.getOrDefault(key, this.defaultRebuilder);
  }
  
  public static <E> SubTupleRebuilder<E, Tuple<E>> tupleIdentity() {
    return new SubTupleRebuilder<E, Tuple<E>>(new HashMap<Object, SubtypeRebuilder<E, ? extends E>>()) {

      @Override
      public Tuple<E> rebuild(Tuple<E> object) {
        return object;
      }
    };
  }
}
