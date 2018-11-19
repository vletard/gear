package analogy;

import analogy.matrix.*;

import java.util.LinkedList;
import java.util.ArrayList;

public class Equation<E>{
  class ResultListByDegree{ // TODO determine how to restrict modification in this class
    int degree;
    LinkedList<FactorizedList<E>> solutionList;
  }

  final private ArrayList<E> A, B, C;
  private LinkedList<ResultListByDegree> solutions;
  private boolean fullSolution = false;

  public Equation(final ArrayList<E> A, final ArrayList<E> B, final ArrayList<E> C){
    this.A = A;
    this.B = B;
    this.C = C;
    this.solutions = null;
  }

  public LinkedList<ResultListByDegree> getSolutions(){
    if (this.solutions == null || this.fullSolution == false)
      this.solveFull();
    return this.solutions;
  }

  public ResultListByDegree getBestSolutions(){
    if (this.solutions == null)
      this.solveBest();
    return this.solutions.getFirst();
  }

  public void solveBest(){
    throw new UnsupportedOperationException();
  }

  public void solveFull(){
    throw new UnsupportedOperationException();
  }
}
