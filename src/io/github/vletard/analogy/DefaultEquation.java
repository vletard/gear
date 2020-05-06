package io.github.vletard.analogy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.vletard.analogy.sequence.Sequence;
import io.github.vletard.analogy.sequence.SimpleSequenceEquation;
import io.github.vletard.analogy.set.ImmutableSet;
import io.github.vletard.analogy.set.SimpleSetEquation;
import io.github.vletard.analogy.tuple.SimpleTupleEquation;
import io.github.vletard.analogy.tuple.Tuple;

/**
 * This abstract class gives the basis for the behaviour of an analogical equation.
 * It is meant to be subclassed by specific equation types that behave accordingly to their
 * specific item types.
 * 
 * All three items are public for quick access, as they are considered non mutable.
 * @author Vincent Letard
 *
 * @param <T> the type of items that are considered in this equation
 * @param <S> the solution type for this type of items (the generic is useful when one uses a subclass of {@link Solution})
 */
public abstract class DefaultEquation<T, S extends Solution<T>> implements Iterable<S> {

  public final T a, b, c;

  /**
   * Simple constructor that assigns each item to a field.
   * @param a item
   * @param b item
   * @param c item
   */
  protected DefaultEquation(T a, T b, T c){
    this.a = a;
    this.b = b;
    this.c = c;
  }

  /**
   * Analyzes the runtime types of the provided items and builds the suitable equation subtype,
   * then returns the created instance as a DefaultEquation.
   * @param <E> the type of items in the expected DefaultEquation
   * @param a item
   * @param b item
   * @param c item
   * @return a newly created DefaultEquation
   */
  public static <E> DefaultEquation<E, ? extends Solution<E>> factory(E a, E b, E c) {
    if (a instanceof ImmutableSet && b instanceof ImmutableSet && c instanceof ImmutableSet) {
      ImmutableSet<Object> setA;
      return (DefaultEquation<E, ? extends Solution<E>>)(DefaultEquation<?, ? extends Solution<?>>) new SimpleSetEquation<Object>((ImmutableSet<Object>) a, (ImmutableSet<Object>) b, (ImmutableSet<Object>) c);
    } else if (a instanceof Tuple && b instanceof Tuple && c instanceof Tuple)
      return (DefaultEquation<E, ? extends Solution<E>>)(DefaultEquation<?, ? extends Solution<?>>) new SimpleTupleEquation<Object>((Tuple<Object>) a, (Tuple<Object>) b, (Tuple<Object>) c);
    else if (a instanceof Sequence && b instanceof Sequence && c instanceof Sequence)
      return (DefaultEquation<E, ? extends Solution<E>>)(DefaultEquation<?, ? extends Solution<?>>) new SimpleSequenceEquation<Object>((Sequence<Object>) a, (Sequence<Object>) b, (Sequence<Object>) c);
    else
      return (DefaultEquation<E, ? extends Solution<E>>)(DefaultEquation<?, ? extends Solution<?>>) new AtomicEquation<Object>(a, b, c);
  }

  /**
   * Builds and returns an {@link Iterable} wrapper for the solutions of this equation
   * where duplicates are ignored.
   * Solutions are enumerated in ascending order of degree.
   * @return an Iterable object providing every unique solution of this equation on demand.
   */
  public final Iterable<S> uniqueSolutions() {
    return new Iterable<S>() {

      @Override
      public Iterator<S> iterator() {
        return new Iterator<S>() {
          private final Iterator<S> it = DefaultEquation.this.iterator();
          private final HashSet<S> listed = new HashSet<S>();
          private S current = null;

          @Override
          public boolean hasNext() {
            while ((this.current == null || this.listed.contains(this.current)) && this.it.hasNext())
              this.current = this.it.next();
            if (this.listed.contains(this.current)) {
              assert(!this.it.hasNext());
              this.current = null;
            }
            return this.current != null;
          }

          @Override
          public S next() {
            if (this.hasNext()) {
              S solution = this.current;
              this.listed.add(solution);
              this.current = null;
              return solution;
            }
            else
              throw new NoSuchElementException();
          }
        };
      }
    };
  }
  
  /**
   * Builds and returns an {@link Iterable} wrapper for the solutions of this equation,
   * limiting the number of distinct degrees.
   * Solutions are enumerated in ascending order of degree.
   * @param degreeCounter the global number of distinct degrees that will be enumerated.
   * @return an Iterable object providing every solution of this equation on demand until degreeCounter distinct degrees have been exhausted.
   */
  public final Iterable<S> nBestDegreeSolutions(int degreeCounter) {
    return new Iterable<S>() {

      @Override
      public Iterator<S> iterator() {
        return new Iterator<S>() {
          private final Iterator<S> it = DefaultEquation.this.iterator();
          private int nBestCounter = 0;
          private int currentDegree = 0;
          private S currentSolution = null;

          @Override
          public boolean hasNext() {
            if (this.currentSolution == null && this.it.hasNext()) {
              S s = this.it.next();
              int degree = s.getDegree();
              if (degree > this.currentDegree) {
                this.nBestCounter ++;
                this.currentDegree = degree;
              }
              this.currentSolution = s;
            }
            if (this.nBestCounter > degreeCounter)
              return false;
            else 
              return this.currentSolution != null;
          }

          @Override
          public S next() {
            if (this.hasNext()) {
              S s = this.currentSolution;
              this.currentSolution = null;
              return s;
            }
            else
              throw new NoSuchElementException();
          }
        };
      }
    };
  }
}
