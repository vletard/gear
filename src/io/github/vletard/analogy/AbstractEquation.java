package io.github.vletard.analogy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractEquation<T, S extends Solution<? extends T>> implements Iterable<S> {

  public final T a, b, c;

  public AbstractEquation(T a, T b, T c){
    this.a = a;
    this.b = b;
    this.c = c;
  }
  
  public final Iterable<S> uniqueSolutions() {
    return new Iterable<S>() {

      @Override
      public Iterator<S> iterator() {
        return new Iterator<S>() {
          private final Iterator<S> it = AbstractEquation.this.iterator();
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
  
  public final Iterable<S> nBestDegreeSolutions(int degreeCounter) {
    return new Iterable<S>() {

      @Override
      public Iterator<S> iterator() {
        return new Iterator<S>() {
          private final Iterator<S> it = AbstractEquation.this.iterator();
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
