package io.github.vletard.analogy;

import java.util.ArrayList;
import java.util.Iterator;

public class AtomicEquation<T> extends DefaultEquation<T, Solution<T>> {

  public AtomicEquation(T A, T B, T C) {
    super(A, B, C);
  }
  
  /**
   * Returns the single solution of this atomic equation, or throws a {@link NoSolutionException}
   * if there is none.
   * @return the associated solution to this equation.
   * @throws NoSolutionException if no solution exist
   */
  public Solution<T> getSolution() throws NoSolutionException {
    Iterator<Solution<T>> it = this.iterator();
    if (it.hasNext())
      return it.next();
    else
      throw new NoSolutionException();
  }
  
  @Override
  public Iterator<Solution<T>> iterator(){
    ArrayList<Solution<T>> solution = new ArrayList<Solution<T>>();
    if (this.a == this.b)
      solution.add(new AtomicSolution<T>(this.c, 1, this));
    else if (this.a == this.c)
      solution.add(new AtomicSolution<T>(this.b, 1, this));
    else if (this.a != null) {
      if (this.a.equals(this.b))
        solution.add(new AtomicSolution<T>(this.c, 1, this));
      else if (this.a.equals(this.c))
        solution.add(new AtomicSolution<T>(this.b, 1, this));
    }
    assert(solution.size() <= 1);
    return solution.iterator();
  }

  @Override
  public AtomicEquation<T> dual() {
    return new AtomicEquation<T>(this.a, this.c, this.b);
  }
}
