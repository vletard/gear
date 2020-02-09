package analogy.sequence;

import java.util.List;

import analogy.Solution;

public class SequenceSolution<T> extends Solution<Sequence<T>> {

  private final List<Factor<T>> factorization;
  // TODO add here recursive information about sub-solutions while implementing sequence analogical recursion

  public SequenceSolution(Sequence<T> content, int degree, List<Factor<T>> factorization) {
    super(content, degree);
    this.factorization = factorization;
  }

  public List<Factor<T>> getFactorization() {
    return factorization;
  }

}
