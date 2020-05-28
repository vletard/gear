package io.github.vletard.analogy;

public class AtomicRelation implements Relation {
  private final Object a, b;

  private AtomicRelation(Object a, Object b) {
    this.a = a;
    this.b = b;
  }

  public static AtomicRelation newStraightRelation(AtomicEquation<?> equation) {
    return new AtomicRelation(equation.a, equation.b);
  }

  public static AtomicRelation newCrossedRelation(AtomicEquation<?> equation) {
    return new AtomicRelation(equation.a, equation.c);
  }

  @Override
  public String toString() {
    if (this.a.equals(this.b))
      return "identity";
    else
      return this.a + " -> " + this.b;
  }

  @Override
  public boolean isIdentity() {
    return this.a.equals(this.b);
  }
}
