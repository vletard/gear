package analogy.matrix;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Factor<E> {

  private final List<E> B;
  private final List<E> C;
  private final boolean crossed;

  public Factor(boolean crossed, List<E> B, List<E> C) {
    this.crossed = crossed;
    this.B = Collections.unmodifiableList(new LinkedList<E>(B));
    this.C = Collections.unmodifiableList(new LinkedList<E>(C));
  }

  public Factor(Factor<E> factor, List<E> addB, List<E> addC) {
    this.crossed = factor.isCrossed();
    List<E> buildB = new LinkedList<E>(factor.B);
    buildB.addAll(addB);
    this.B = Collections.unmodifiableList(buildB);
    
    List<E> buildC = new LinkedList<E>(factor.C);
    buildC.addAll(addC);
    this.C = Collections.unmodifiableList(buildC);
  }

  public List<E> getB() {
    return B;
  }

  public List<E> getC() {
    return C;
  }
  
  public boolean isCrossed() {
    return this.crossed;
  }
}
