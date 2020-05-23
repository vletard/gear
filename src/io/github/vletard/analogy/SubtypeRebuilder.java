package io.github.vletard.analogy;

import io.github.vletard.analogy.sequence.Sequence;
import io.github.vletard.analogy.set.ImmutableSet;
import io.github.vletard.analogy.tuple.Tuple;

/**
 * While in simple situations developers can use predefined {@link Sequence}, {@link Tuple} and {@link ImmutableSet} from this library,
 * more complex cases may require subclassing those.
 * However, when an analogical equation is computed over subtypes, the solution can only be generated as the corresponding base type.
 * To address this issue, SubtypeRebuilders can be submitted to the analogical equation constructors in order to construct solutions
 * based on the subtype instead.
 * 
 * Note that only fields of the subtype that can be inferred from fields of its corresponding base type can be successfully filled,
 * since nothing else will be taken into account by the analogical equation solving process.
 * @author Vincent Letard
 *
 * @param <Type> base type to rebuild from (typically {@link Sequence}, {@link Tuple} or {@link ImmutableSet}).
 * @param <Subtype> subtype to be rebuilt.
 */
public abstract class SubtypeRebuilder<Type, Subtype extends Type> {
  public abstract Subtype rebuild(Type object);
  
  public static <T> SubtypeRebuilder<T, T> identity() {
    return new SubtypeRebuilder<T, T>() {

      @Override
      public T rebuild(T object) {
        return object;
      }
    };
  }
}
