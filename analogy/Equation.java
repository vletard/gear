package analogy;

import java.util.Collection;
import java.util.List;

import analogy.matrix.EquationReadingHead;
import analogy.matrix.Factor;
import analogy.matrix.ImpossibleStepException;
import analogy.matrix.Step;
import util.MultiMap;
import util.MultiSortedMap;
import util.QueueMultiSortedMap;
import util.UnmodifiableArrayList;

/**
 * This class represents an analogical equation.
 * It offers primitives to compute its solutions and stores them.
 * @author Vincent Letard
 *
 * @param <E> The items composing the sequences of the analogical Equation.
 */
public class Equation<E>{
  final public UnmodifiableArrayList<E> A, B, C;
  private MultiSortedMap<Integer, List<Factor<E>>> solutions;
//  private TreeMap<Integer, Set<List<Factor<E>>>> solutions;
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

  public MultiMap<Integer, List<Factor<E>>> getSolutions(){
    return this.solutions;
  }

  public Collection<List<Factor<E>>> getBestSolutions(){
    if (this.solutions == null)
      this.solveBest();
    return this.solutions.get(this.solutions.firstKey());
  }

  public void solveBest(){
    if (this.foundBestSolution)
      return;
    this.solutions = new QueueMultiSortedMap<Integer, List<Factor<E>>>();

    QueueMultiSortedMap<Integer, EquationReadingHead<E>> readingRegister = new QueueMultiSortedMap<Integer, EquationReadingHead<E>>();
    {
      EquationReadingHead<E> head = new EquationReadingHead<E>(this);
      readingRegister.add(head.getDegree(), head);
//      Queue<EquationReadingHead<E>> initialList = new LinkedList<EquationReadingHead<E>>();
//      initialList.add(head);
//      readingRegister.put(head.getCurrentDegree(), initialList);
    }

    /*
     * Note that the matrix searching loop below continues on until there is no partial solution
     * which current degree is lower or equal than the best found solution so far.
     * This implies that the function won't stop until *all* the best solutions have been found.
     * Usually though, the best degree solution is unique.
     */
    while (!readingRegister.isEmpty() && this.solutions.isEmpty()){  // TODO continue until all best solutions are exhausted
      EquationReadingHead<E> current = readingRegister.poll();
      if (current.isFinished())
        this.solutions.put(current.getDegree(), current.getFactorList());
      else{
        try{
          for (Step step : new Step[]{Step.AB, Step.AC, Step.CD, Step.BD}) {
            if (current.canStep(step)){
              EquationReadingHead<E> result = new EquationReadingHead<E>(current, step);
              readingRegister.add(result.getDegree(), result);
            }
          }
        } catch(ImpossibleStepException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
