package analogy.matrix;

import analogy.Proportion;

public class ProportionReadingHead<E>{
  private final Proportion<E> p;
  private final int a, b, c, d;
  private final int currentDegree;
  private final Step lastStep;

  /*
   * @Override
   */
  public String toString(){
    String repr = "[ ";
    repr = repr + p.A.subList(0, a) + "|" + p.A.subList(a, p.A.size());
    repr = repr + " : ";
    repr = repr + p.B.subList(0, b) + "|" + p.B.subList(b, p.B.size());
    repr = repr + " :?: ";
    repr = repr + p.C.subList(0, c) + "|" + p.C.subList(c, p.C.size());
    repr = repr + " : ";
    repr = repr + p.D.subList(0, d) + "|" + p.D.subList(d, p.D.size());
    return repr;
  }

  public ProportionReadingHead(Proportion<E> p){
    this.p = p;
    this.a = 0;
    this.b = 0;
    this.c = 0;
    this.d = 0;
    this.currentDegree = 1;
    this.lastStep = Step.UNDEFINED;
  }

  public int getCurrentDegree(){
    return currentDegree;
  }

  private ProportionReadingHead(ProportionReadingHead<E> previous, Step step) throws ImpossibleStepException{
    this.p = previous.p;
    int a = previous.a;
    int b = previous.b;
    int c = previous.c;
    int d = previous.d;
    switch (step){
      case AB : a += 1;
                b += 1;
                break;
      case AC : a += 1;
                c += 1;
                break;
      case CD : c += 1;
                d += 1;
                break;
      case BD : b += 1;
                d += 1;
                break;
      case UNDEFINED: throw new ImpossibleStepException();
    }
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
    this.lastStep = step;
    if (   previous.lastStep == Step.UNDEFINED ||
         ((previous.lastStep == Step.AB || previous.lastStep == Step.CD) && (step == Step.AB || step == Step.CD)) ||
	 ((previous.lastStep == Step.AC || previous.lastStep == Step.BD) && (step == Step.AC || step == Step.BD))    )
      this.currentDegree = previous.currentDegree;
    else 
      this.currentDegree = previous.currentDegree + 1;
  }

  public ProportionReadingHead<E> makeStep(Step step) throws ImpossibleStepException{
    return new ProportionReadingHead<E>(this, step);
  }

  public boolean canStep(Step step){
    switch (step){
      case AB : return (a < p.A.size() && b < p.B.size() && p.A.get(a) == p.B.get(b));
      case AC : return (a < p.A.size() && c < p.C.size() && p.A.get(a) == p.C.get(c));
      case CD : return (c < p.C.size() && d < p.D.size() && p.C.get(c) == p.D.get(d));
      case BD : return (b < p.B.size() && d < p.D.size() && p.B.get(b) == p.D.get(d));
      default : return false;
    }
  }

  public boolean isFinished(){
    return (a == p.A.size() && b == p.B.size() && c == p.C.size() && d == p.D.size());
  }
}
