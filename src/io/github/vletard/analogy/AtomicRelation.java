package io.github.vletard.analogy;

public class AtomicRelation implements Relation {
  private final boolean crossed;
  private final AtomicEquation<?> equation;

  public AtomicRelation(AtomicEquation<?> equation, boolean crossed) {
    this.crossed = crossed;
    this.equation = equation;
  }
  
  @Override
  public AtomicRelation dual() {
    return new AtomicRelation(this.equation, !this.crossed);
  }
  
  @Override
  public String toString() {
    String output = this.equation.a.toString() + " -> ";
    if (this.crossed)
      return output + this.equation.b.toString();
    else
      return output + this.equation.c.toString();
  }

  @Override
  public String displayStraight() {
    if (this.crossed)
      return "id";
    else
      return this.equation.a + " -> " + this.equation.b;
  }

  @Override
  public String displayCrossed() {
    if (this.crossed)
      return this.equation.a + " -> " + this.equation.c;
    else
      return "id";
  }
}
