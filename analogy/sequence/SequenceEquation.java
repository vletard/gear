package analogy.sequence;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import analogy.Element;
import analogy.SolutionMap;
import analogy.AbstractEquation;
import util.Sequence;

/**
 * This class represents an analogical equation.
 * It offers primitives to compute its solutions and stores them.
 * @author Vincent Letard
 *
 * @param <E> The items composing the sequences of the analogical Equation.
 */
public class SequenceEquation<E> extends AbstractEquation<Sequence<E>, SolutionMap<E>>{

  private TreeMap<Integer, SolutionMap<E>> solutions;
  private boolean bestSolutionsProcessed;

  public SequenceEquation(Sequence<E> a, Sequence<E> b, Sequence<E> c){
    super(a, b, c);
    this.bestSolutionsProcessed = false;
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
  @Override
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
        Sequence<E> sequence = Factorizations.extractElement(factorList, Element.D);
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
  protected TreeMap<Integer, SolutionMap<E>> getSolutions() {
    return this.solutions;
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
    SequenceEquation other = (SequenceEquation) obj;
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
