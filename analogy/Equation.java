package analogy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import analogy.matrix.EquationReadingHead;
import analogy.matrix.Factor;
import analogy.matrix.Factorizations;
import analogy.matrix.ImpossibleStepException;
import analogy.matrix.Step;
import util.UnmodifiableArrayList;

/**
 * This class represents an analogical equation.
 * It offers primitives to compute its solutions and stores them.
 * @author Vincent Letard
 *
 * @param <E> The items composing the sequences of the analogical Equation.
 */
public class Equation<E>{
  /**
   * This map associates each sequence representing a solution to the set of factorizations
   * that have been found giving that solution.
   * @author Vincent Letard
   *
   * @param <T> Type of objects in the sequences and factors.
   */
  public class SolutionMap<T> extends HashMap<List<T>, Set<List<Factor<T>>>>{
    private static final long serialVersionUID = -2236281191394013633L;
  }

  /**
   * This is an exception to be thrown when trying to perform an operation on a solution
   * but no solution could be found.
   * @author Vincent Letard
   *
   */
  public static class NoSolutionException extends Exception{
    private static final long serialVersionUID = 5899880279685014860L;
    public NoSolutionException(String string) {
      super(string);
    }
  }

  final public UnmodifiableArrayList<E> A, B, C;
  private SortedMap<Integer, SolutionMap<E>> solutions;
  private boolean bestSolutionsProcessed;

  public Equation(UnmodifiableArrayList<E> A, UnmodifiableArrayList<E> B, UnmodifiableArrayList<E> C){
    this.A = A;
    this.B = B;
    this.C = C;
    this.solutions = null;
    this.bestSolutionsProcessed = false;
  }

  /**
   * Retrieves the map of all the solutions of lowest degree of this equation.
   * Note that if solveBest was not called prior to getBestSolutions, it will be called automatically.
   * @return a {@link SolutionMap} of the solutions of best degree. 
   */
  public SolutionMap<E> getBestSolutions(){
    if (this.solutions == null)
      this.solveBest();
    if (this.solutions.isEmpty())
      return new SolutionMap<E>();
    else
      return this.solutions.get(this.solutions.firstKey());
  }

  /**
   * Returns the degree of the best solution found.
   * Note that if solveBest was not called prior to getBestSolutions, it will be called automatically.
   * @return the best degree.
   * @throws NoSolutionException if a degree cannot be obtained because the equation has no solution.
   */
  public int getBestDegree() throws NoSolutionException {
    if (this.solutions == null)
      this.solveBest();
    if (this.solutions.isEmpty())
      throw new NoSolutionException("Cannot determine the degree of a non existent solution.");
    else
      return this.solutions.get(this.solutions.firstKey()).values().iterator().next().iterator().next().size();
  }

  /**
   * Checks whether the counts of items in the 3 available sequences make a solution
   * to this analogical equation impossible or not.
   * @return false if the proportion between the sequences is impossible
   */
  private boolean checkCounts() {
    if (this.A.size() - this.B.size() - this.C.size() > 0)
      return false;

    Map<E, Integer> counts = new HashMap<E, Integer>();

    for (int i=0; i < this.A.size(); i++)
      counts.put(this.A.get(i), counts.getOrDefault(this.A.get(i), 0) +1);
    for (int i=0; i < this.B.size(); i++)
      counts.put(this.B.get(i), counts.getOrDefault(this.B.get(i), 0) -1);
    for (int i=0; i < this.C.size(); i++)
      counts.put(this.C.get(i), counts.getOrDefault(this.C.get(i), 0) -1);

    Iterator<Integer> it = counts.values().iterator();
    while (it.hasNext())
      if (it.next() > 0)
        return false;

    return true;
  }

  /**
   * Attempts to solve this Equation until all the best solutions have been found.
   * More precisely, the greedy algorithm goes on until one of those two conditions occurs:
   * - every path has been explored and failed
   * - a path gave a solution of degree d, and every other path has been explored until no more solution of degree d can be found
   */
  public void solveBest(){
    if (this.bestSolutionsProcessed)
      return;

    this.solutions = new TreeMap<Integer, SolutionMap<E>>();

    if (! this.checkCounts())
      return;

    SortedMap<Integer, Set<EquationReadingHead<E>>> readingRegister = new TreeMap<Integer, Set<EquationReadingHead<E>>>();
    {
      EquationReadingHead<E> head = new EquationReadingHead<E>(this);
      Set<EquationReadingHead<E>> s = new HashSet<EquationReadingHead<E>>();
      s.add(head);
      readingRegister.put(head.getCurrentDegree(), s);
    }

    /*
     * Note that the matrix searching loop below continues on until there is no partial solution
     * which current degree is lower or equal than the best found solution so far.
     * This implies that the function won't stop until *all* the best solutions have been found.
     * Usually though, the best degree solution is unique.
     */
    while (!readingRegister.isEmpty() && 
        (this.solutions.isEmpty() || readingRegister.firstKey() <= this.solutions.firstKey())){

      int currentDegree = readingRegister.firstKey();
      Set<EquationReadingHead<E>> s = readingRegister.get(currentDegree);
      EquationReadingHead<E> currentHead = s.iterator().next();
      s.remove(currentHead);
      if (s.isEmpty())
        readingRegister.remove(currentDegree);

      if (currentHead.isFinished()) {
        List<Factor<E>> factorList = currentHead.getFactors();
        List<E> sequence = Factorizations.extractElement(factorList, Element.D);
        this.solutions.putIfAbsent(currentDegree, new SolutionMap<E>());
        this.solutions.get(currentDegree).putIfAbsent(sequence, new HashSet<List<Factor<E>>>());
        this.solutions.get(currentDegree).get(sequence).add(factorList);
      }
      else{
        try{
          for (Step step : new Step[]{Step.AB, Step.AC, Step.CD, Step.BD}) {
            if (currentHead.canStep(step)){
              EquationReadingHead<E> newHead = currentHead.makeStep(step, true);
              int newDegree = newHead.getCurrentDegree();
              readingRegister.putIfAbsent(newDegree, new HashSet<EquationReadingHead<E>>());
              readingRegister.get(newDegree).add(newHead);
            }
          }
        } catch(ImpossibleStepException e) {
          throw new RuntimeException(e);
        }
      }
    }
    this.bestSolutionsProcessed = true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((A == null) ? 0 : A.hashCode());
    result = prime * result + ((B == null) ? 0 : B.hashCode());
    result = prime * result + ((C == null) ? 0 : C.hashCode());
    return result;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Equation other = (Equation) obj;
    if (A == null) {
      if (other.A != null)
        return false;
    } else if (!A.equals(other.A))
      return false;
    if (B == null) {
      if (other.B != null)
        return false;
    } else if (!B.equals(other.B))
      return false;
    if (C == null) {
      if (other.C != null)
        return false;
    } else if (!C.equals(other.C))
      return false;
    return true;
  }
}
