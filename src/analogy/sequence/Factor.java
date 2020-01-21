package analogy.sequence;

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((B == null) ? 0 : B.hashCode());
    result = prime * result + ((C == null) ? 0 : C.hashCode());
    result = prime * result + (crossed ? 1231 : 1237);
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Factor<E> other = (Factor<E>) obj;
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
    if (crossed != other.crossed)
      return false;
    return true;
  }
}
