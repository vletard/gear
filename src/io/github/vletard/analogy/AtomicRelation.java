package io.github.vletard.analogy;

public class AtomicRelation implements Relation {
  private final Object a, b;

  private AtomicRelation(Object a, Object b) {
    if (a != null && a.equals(b)) {
      this.a = null;
      this.b = null;
    }
    else {
      this.a = a;
      this.b = b;
    }
  }

  public static AtomicRelation newStraightRelation(AtomicEquation<?> equation) {
    return new AtomicRelation(equation.a, equation.b);
  }

  public static AtomicRelation newCrossedRelation(AtomicEquation<?> equation) {
    return new AtomicRelation(equation.a, equation.c);
  }

  @Override
  public String toString() {
    if (this.a == null && this.b == null)
      return "identity";
    else
      return this.a + " -> " + this.b;
  }

  @Override
  public boolean isIdentity() {
    return this.a == null && this.b == null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((a == null) ? 0 : a.hashCode());
    result = prime * result + ((b == null) ? 0 : b.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AtomicRelation other = (AtomicRelation) obj;
    if (a == null) {
      if (other.a != null)
        return false;
    } else if (!a.equals(other.a))
      return false;
    if (b == null) {
      if (other.b != null)
        return false;
    } else if (!b.equals(other.b))
      return false;
    return true;
  }
}
