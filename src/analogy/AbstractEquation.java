package analogy;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractEquation<T, S extends Solution<? extends T>> implements Iterable<S> {

  public final T a, b, c;

  public AbstractEquation(T a, T b, T c){
    this.a = a;
    this.b = b;
    this.c = c;
  }
  
  public final Iterable<S> nBestDegreeSolution(int degreeCounter) {
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
