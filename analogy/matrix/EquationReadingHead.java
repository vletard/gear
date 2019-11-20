package analogy.matrix;

import java.util.List;

import analogy.Equation;

public class EquationReadingHead<E>{
  private final Equation<E> equation;
  private final int a, b, c;
  private final List<Factor<E>> factors;

  @Override
  public String toString(){
    String repr = "[ ";
    repr = repr + equation.A.subList(0, a) + "|" + equation.A.subList(a, equation.A.size());
    repr = repr + " : ";
    repr = repr + equation.B.subList(0, b) + "|" + equation.B.subList(b, equation.B.size());
    repr = repr + " :?: ";
    repr = repr + equation.C.subList(0, c) + "|" + equation.C.subList(c, equation.C.size());
    repr = repr + " ]";
    return repr;
  }

  public EquationReadingHead(Equation<E> p){
    this.equation = p;
    this.a = 0;
    this.b = 0;
    this.c = 0;
    this.factors = Factors.newList();
  }

  private E getB(){
    return this.equation.B.get(this.b);
  }

  private E getC(){
    return this.equation.C.get(this.c);
  }
  
  public EquationReadingHead(EquationReadingHead<E> eqn, Step step) throws ImpossibleStepException{
    if (!eqn.canStep(step))
      throw new ImpossibleStepException();
    this.equation = eqn.equation;
    int a = eqn.a;
    int b = eqn.b;
    int c = eqn.c;
    switch (step){
      case AB : this.factors = Factors.extendListB(eqn.factors, false, eqn.getB());
                a += 1;
                b += 1;
                break;
      case AC : this.factors = Factors.extendListC(eqn.factors, true, eqn.getC());
                a += 1;
                c += 1;
                break;
      case CD : this.factors = Factors.extendListC(eqn.factors, false, eqn.getC());
                c += 1;
                break;
      case BD : this.factors = Factors.extendListB(eqn.factors, true, eqn.getB());
                b += 1;
                break;
      default: throw new IllegalArgumentException("A new reading head can only be created with a defined step.");
    }
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public boolean canStep(Step step){
    switch (step){
    case AB : return (a < equation.A.size() && b < equation.B.size() && equation.A.get(a) == equation.B.get(b));
    case AC : return (a < equation.A.size() && c < equation.C.size() && equation.A.get(a) == equation.C.get(c));
    case CD : return (c < equation.C.size());
    case BD : return (b < equation.B.size());
    default : return false;
    }
  }

  public boolean isFinished(){
    return (a == equation.A.size() && b == equation.B.size() && c == equation.C.size());
  }

  public int getDegree() {
    return this.factors.size();
  }

  public List<Factor<E>> getFactorList() {
    return factors;
  }
}
