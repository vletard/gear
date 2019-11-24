package analogy;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
  public class SolutionMap<T> extends HashMap<List<T>, Set<List<Factor<T>>>>{
    private static final long serialVersionUID = -2236281191394013633L;
  }
  
  final public UnmodifiableArrayList<E> A, B, C;
  private SortedMap<Integer, SolutionMap<E>> solutions;
//  private boolean solutionFull;
  private boolean foundBestSolution;

  public Equation(UnmodifiableArrayList<E> A, UnmodifiableArrayList<E> B, UnmodifiableArrayList<E> C){
    this.A = A;
    this.B = B;
    this.C = C;
    this.solutions = null;
//    this.solutionFull = false;
    this.foundBestSolution = false;
  }

  public Map<Integer, SolutionMap<E>> getSolutions(){
    return Collections.unmodifiableMap(this.solutions);
  }

  public SolutionMap<E> getBestSolutions(){
    if (this.solutions == null)
      this.solveBest();
    if (this.solutions.isEmpty())
      return new SolutionMap<E>();
    else
      return this.solutions.get(this.solutions.firstKey());
  }

  public void solveBest(){
    if (this.foundBestSolution)
      return;
    this.solutions = new TreeMap<Integer, SolutionMap<E>>();

    SortedMap<Integer, Queue<EquationReadingHead<E>>> readingRegister = new TreeMap<Integer, Queue<EquationReadingHead<E>>>();
    {
      EquationReadingHead<E> head = new EquationReadingHead<E>(this);
      Queue<EquationReadingHead<E>> q = new LinkedList<EquationReadingHead<E>>();
      q.add(head);
      readingRegister.put(head.getCurrentDegree(), q);
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
      Queue<EquationReadingHead<E>> q = readingRegister.get(currentDegree);
      EquationReadingHead<E> currentHead = q.poll();
      if (q.isEmpty())
        readingRegister.remove(currentDegree);
      
      if (currentHead.isFinished()) {
        List<Factor<E>> factorList = currentHead.getFactorList();
        List<E> sequence = Factorizations.extractSolution(factorList);
        this.solutions.putIfAbsent(currentDegree, new SolutionMap<E>());
        this.solutions.get(currentDegree).putIfAbsent(sequence, new HashSet<List<Factor<E>>>());
        this.solutions.get(currentDegree).get(sequence).add(factorList);
      }
      else{
        try{
          for (Step step : new Step[]{Step.AB, Step.AC, Step.CD, Step.BD}) {
            if (currentHead.canStep(step)){
              EquationReadingHead<E> newHead = currentHead.makeStep(step);
              int newDegree = newHead.getCurrentDegree();
              readingRegister.putIfAbsent(newDegree, new LinkedList<EquationReadingHead<E>>());
              readingRegister.get(newDegree).add(newHead);
            }
          }
        } catch(ImpossibleStepException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
