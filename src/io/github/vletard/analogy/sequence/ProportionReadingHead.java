package io.github.vletard.analogy.sequence;

import io.github.vletard.analogy.RebuildException;
import io.github.vletard.analogy.SubtypeRebuilder;
import io.github.vletard.analogy.sequence.Step;

public class ProportionReadingHead<E>{
  private final SequenceProportion<E> proportion;
  private final int a, b, c, d;
  private final Factorization<E, Sequence<E>> factorization;

  public ProportionReadingHead(SequenceProportion<E> p){
    this.proportion = p;
    this.a = 0;
    this.b = 0;
    this.c = 0;
    this.d = 0;
    this.factorization = new Factorization<E, Sequence<E>>(SubtypeRebuilder.identity());
  }

  /**
   * Returns the degree of this ProportionReadingHead for its current state.
   * If isFinished() is false, the returned degree is a lower bound for the degree of
   * a potential factorization (if the Proportion is valid).
   * @return The current degree of this ProportionReadingHead.
   */
  public int getCurrentDegree(){
    return this.factorization.size();
  }

  private ProportionReadingHead(ProportionReadingHead<E> prev, Step step) throws ImpossibleStepException, RebuildException{
    if (!prev.canStep(step))
      throw new ImpossibleStepException();
    this.proportion = prev.proportion;
    int a = prev.a;
    int b = prev.b;
    int c = prev.c;
    int d = prev.d;
    switch (step){
      case AB : this.factorization = prev.factorization.extendListB(false, prev.getB());
                a += 1;
                b += 1;
                break;
      case AC : this.factorization = prev.factorization.extendListC(true, prev.getC());
                a += 1;
                c += 1;
                break;
      case CD : this.factorization = prev.factorization.extendListC(false, prev.getC());
                c += 1;
                d += 1;
                break;
      case BD : this.factorization = prev.factorization.extendListB(true, prev.getB());
                b += 1;
                d += 1;
                break;
      default: throw new IllegalArgumentException("A new reading head can only be created with a defined step.");
    }
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }

  /**
   * Creates and return a new {@link ProportionReadingHead} obtained by performing
   * the specified {@link Step}.
   * @param step Step to apply on the current reading head.
   * @param fastForward Whether to automatically repeat the same step as far as possible (keeping the same degree).  
   * @return A new reading head after performing the given step.
   * @throws ImpossibleStepException If the step cannot be applied.
   * @throws RebuildException 
   */
  public ProportionReadingHead<E> makeStep(Step step, boolean fastForward) throws ImpossibleStepException, RebuildException{
    ProportionReadingHead<E> newHead = new ProportionReadingHead<E>(this, step);
    if (fastForward) {
      // An alternative to fast forwarding steps can be to adjust the tracking of explored 
      // reading heads (in Proportion.check()) by ignoring factors when comparing two
      // ProportionReadingHead for equality (equality would only depend on indices).
      while (newHead.canStep(step)) {
        ProportionReadingHead<E> fastForwardHead = newHead.makeStep(step, fastForward);
        if (fastForwardHead.getCurrentDegree() > newHead.getCurrentDegree())
          break;
        else
          newHead = fastForwardHead;
      }
    }
    return newHead;
  }

  /**
   * Checks whether a given {@link Step} is possible for the current state of this reading head.
   * @param step the Step to be tested
   * @return true if the current state allows the step, false otherwise
   */
  public boolean canStep(Step step){
    switch (step){
      case AB : return (a < proportion.A.size() && b < proportion.B.size() && proportion.A.get(a).equals(proportion.B.get(b)));
      case AC : return (a < proportion.A.size() && c < proportion.C.size() && proportion.A.get(a).equals(proportion.C.get(c)));
      case CD : return (c < proportion.C.size() && d < proportion.D.size() && proportion.C.get(c).equals(proportion.D.get(d)));
      case BD : return (b < proportion.B.size() && d < proportion.D.size() && proportion.B.get(b).equals(proportion.D.get(d)));
      default : return false;
    }
  }

  /**
   * Checks whether this ProportionReadingHead has reached the sink of the matrix. If true,
   * the linked Proportion must be valid.
   * @return true if the progress in the matrix has finished.
   */
  public boolean isFinished(){
    return (a == proportion.A.size() && b == proportion.B.size() && c == proportion.C.size() && d == proportion.D.size());
  }

  private E getB(){
    return this.proportion.B.get(this.b);
  }

  private E getC(){
    return this.proportion.C.get(this.c);
  }


  @Override
  public String toString(){
    String repr = "";
    repr = repr + proportion.A.toList().subList(0, a) + "|" + proportion.A.toList().subList(a, proportion.A.size());
    repr = repr + " : \n";
    repr = repr + proportion.B.toList().subList(0, b) + "|" + proportion.B.toList().subList(b, proportion.B.size());
    repr = repr + " :?: \n";
    repr = repr + proportion.C.toList().subList(0, c) + "|" + proportion.C.toList().subList(c, proportion.C.size());
    repr = repr + " : \n";
    repr = repr + proportion.D.toList().subList(0, d) + "|" + proportion.D.toList().subList(d, proportion.D.size());
    return repr;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + a;
    result = prime * result + b;
    result = prime * result + c;
    result = prime * result + d;
    result = prime * result + ((factorization == null) ? 0 : factorization.hashCode());
    result = prime * result + ((proportion == null) ? 0 : proportion.hashCode());
    return result;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProportionReadingHead other = (ProportionReadingHead) obj;
    if (a != other.a)
      return false;
    if (b != other.b)
      return false;
    if (c != other.c)
      return false;
    if (d != other.d)
      return false;
    if (factorization == null) {
      if (other.factorization != null)
        return false;
    } else if (!factorization.equals(other.factorization))
      return false;
    if (proportion == null) {
      if (other.proportion != null)
        return false;
    } else if (!proportion.equals(other.proportion))
      return false;
    return true;
  }
  
  /**
   * Returns the factorization corresponding to this ProportionReadingHead, for its current state.
   * @return the factorization of this ProportionReadingHead.
   */
  public Factorization<E, Sequence<E>> getFactors() {
    return this.factorization;
  }
}
