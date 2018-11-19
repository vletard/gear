package analogy;

import java.util.LinkedList;
import java.util.ListIterator;

import util.UnmodifiableArrayList;
import analogy.matrix.*;

public class Proportion<E>{
  final public UnmodifiableArrayList<E> A, B, C, D;
  private Boolean valid;

  public Proportion(UnmodifiableArrayList<E> A, UnmodifiableArrayList<E> B, UnmodifiableArrayList<E> C, UnmodifiableArrayList<E> D){
    this.A = A;
    this.B = B;
    this.C = C;
    this.D = D;
  }

  private boolean check(){
    LinkedList<ProportionReadingHead<E>> readingRegister = new LinkedList<ProportionReadingHead<E>>();
    readingRegister.add(new ProportionReadingHead<E>(this));
    while(!readingRegister.isEmpty()){
      ProportionReadingHead<E> current = readingRegister.removeFirst();
      if (current.isFinished())
        return true;
      else{
        try{
          for (Step step : new Step[]{Step.AB, Step.AC, Step.CD, Step.BD}) {
            if (current.canStep(step)){
              ProportionReadingHead<E> result = current.makeStep(step);
              if (readingRegister.isEmpty())
                readingRegister.add(result);
              else {
                ListIterator<ProportionReadingHead<E>> it = readingRegister.listIterator();
                while (it.hasNext() && it.next().getCurrentDegree() > result.getCurrentDegree()){
                }
                it.previous();
                it.add(result); // TODO check for duplicates
              }
            }
          }
        } catch(ImpossibleStepException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return false;
  }
  
  public boolean isValid(){
    if (valid == null){
      valid = check();
    }
    return valid;
  }

  /*
   * @Override
   */
  public String toString(){
    String marker = " :: ";
    if (valid == null || valid == false)
      marker = " ?? ";
    return "[ " + A + " : " + B + marker + C + " : " + D + " ]";
  }
}
