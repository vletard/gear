package analogy;

import java.util.TreeMap;

public abstract class AbstractEquation<E, Bag extends SolutionBag<E>> {

  public final E A, B, C;

  public AbstractEquation(E a, E b, E c){
    this.A = a;
    this.B = b;
    this.C = c;
  }

  protected abstract TreeMap<Integer, Bag> getSolutions();
  
  /**
   * Returns the degree of the best solution found.
   * Note that if solveBest was not called prior to getBestSolutions, it will be called automatically.
   * @return the best degree.
   * @throws NoSolutionException if a degree cannot be obtained because the equation has no solution.
   */
  public int getBestDegree() throws NoSolutionException {
    if (this.getSolutions() == null)
      this.solveBest();
    if (this.getSolutions().isEmpty())
      throw new NoSolutionException("Cannot determine the degree of a non existent solution.");
    else
      return this.getSolutions().firstKey();
  }

  /**
   * Retrieves the map of all the solutions of lowest degree of this equation.
   * Note that if solveBest was not called prior to getBestSolutions, it will be called automatically.
   * @return a {@link SolutionMap} of the solutions of best degree. 
   * @throws NoSolutionException 
   */
  public Bag getBestSolutions() throws NoSolutionException {
    if (this.getSolutions() == null)
      this.solveBest();
    if (this.getSolutions().isEmpty())
      throw new NoSolutionException();
    else
      return this.getSolutions().get(this.getSolutions().firstKey());
  }

  /**
   * Attempts to solve this Equation until all the best solutions have been found.
   * More precisely, the greedy algorithm goes on until one of those two conditions occurs:
   * - every path has been explored and failed
   * - a path gave a solution of degree d, and every other path has been explored until no more solution of degree d can be found
   */
  public abstract void solveBest();
}
