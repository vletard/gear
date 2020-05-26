package io.github.vletard.analogy;

public class AtomicRelation implements Relation {
  private final AtomicEquation<?> equation;

  public AtomicRelation(AtomicEquation<?> equation) {
    this.equation = equation;
  }

  @Override
  public AtomicRelation dual() {
    return new AtomicRelation(this.equation.dual());
  }

  @Override
  public String toString() {
    String output = "";
    if (! this.equation.b.equals(this.equation.a))
      output = this.equation.b.toString();
    if (! this.equation.c.equals(this.equation.a))
      output = this.equation.c.toString();

    if (!output.contentEquals(""))
      return this.equation.a.toString() + " -> " + output;
    else
      return "identity";
  }

  @Override
  public String displayStraight() {
    if (this.equation.b.equals(this.equation.a))
      return "identity";
    else
      return this.equation.a + " -> " + this.equation.b;
  }

  @Override
  public String displayCrossed() {
    if (this.equation.c.equals(this.equation.a))
      return "identity";
    else
      return this.equation.a + " -> " + this.equation.c;
  }
}
