package analogy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import analogy.sequence.SequenceEquation;
import util.Sequence;
import util.Tuple;

public class DefaultEquation extends AbstractEquation<Object, SolutionBag<Object>> {

  private TreeMap<Integer, SolutionBag<Object>> solutions;

  public DefaultEquation(Object a, Object b, Object c) {
    super(a, b, c);
  }

  /**
   * Attempts to solve this analogical equation atomically.
   * If success, the solution added is considered of degree 1.
   */
  private void solveAtomic(){
    if (this.A == this.B)
      solutions.put(1, new SolutionSingleton(this.C));
    else if (this.A == this.C)
      solutions.put(1, new SolutionSingleton(this.B));
    else if (this.A != null) {
      if (this.A.equals(this.B))
        solutions.put(1, new SolutionSingleton(this.C));
      else if (this.A.equals(this.C))
        solutions.put(1, new SolutionSingleton(this.B));
    }
  }

  private void solveBestTuple() {
    if (!(this.A instanceof Tuple && this.B instanceof Tuple && this.C instanceof Tuple))
      return;

    Tuple<?> A = (Tuple<?>) this.A;
    Tuple<?> B = (Tuple<?>) this.B;
    Tuple<?> C = (Tuple<?>) this.C;

    Set<Object> keys = new HashSet<Object>();
    keys.addAll(A.keySet());
    keys.addAll(B.keySet());
    keys.addAll(C.keySet());

    HashMap<Object, SolutionBag<Object>> localSolution = new HashMap<Object, SolutionBag<Object>>();
    int compoundDegree = 0;
    try {
      for (Object key: keys) {
        DefaultEquation sub = new DefaultEquation(A.get(key), B.get(key), C.get(key));
        compoundDegree = Math.max(compoundDegree, sub.getBestDegree());
        localSolution.put(key, sub.getBestSolutions());
      }
      assert(compoundDegree > 0);
      this.solutions.put(compoundDegree, new SolutionTuple(localSolution).genericCast());
    }
    catch (NoSolutionException e) {}
  }

  private void solveBestSequence() {
    if (!(this.A instanceof Sequence && this.B instanceof Sequence && this.C instanceof Sequence))
      return;

    Sequence<Object> A = (Sequence<Object>) this.A;
    Sequence<Object> B = (Sequence<Object>) this.B;
    Sequence<Object> C = (Sequence<Object>) this.C;

    SequenceEquation<Object> eqn = new SequenceEquation<Object>(A, B, C);
    try {
      this.solutions.put(eqn.getBestDegree(), eqn.getBestSolutions().genericCast());
    } catch (NoSolutionException e) {}
  }

  @Override
  public void solveBest() {
    assert(!(this.A instanceof Sequence && this.B instanceof Sequence && this.C instanceof Sequence));
    assert(!(this.A instanceof Tuple && this.B instanceof Tuple && this.C instanceof Tuple));

    this.solutions = new TreeMap<Integer, SolutionBag<Object>>();

    this.solveAtomic();
    if (this.solutions.isEmpty())
      this.solveBestTuple();
    if (this.solutions.isEmpty())
      this.solveBestSequence();
  }

  @Override
  protected TreeMap<Integer, SolutionBag<Object>> getSolutions() {
    return this.solutions;
  }
}
