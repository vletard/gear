package analogy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import util.UnmodifiableArrayList;
import analogy.matrix.*;

/**
 * This class represents an analogical proportion. It can be initialized with 4 sequence elements
 * and offers a method to check whether the sequences are in proportion or not.
 * @author Vincent Letard
 *
 * @param <E> Parametric type of the elements inside sequences.
 */
public class Proportion<E>{
  final public UnmodifiableArrayList<E> A, B, C, D;
  private Boolean valid;

  public Proportion(UnmodifiableArrayList<E> A, UnmodifiableArrayList<E> B, UnmodifiableArrayList<E> C, UnmodifiableArrayList<E> D){
    this.A = A;
    this.B = B;
    this.C = C;
    this.D = D;
  }

  /**
   * Checks in full depth whether the 4 sequences of this Proportion are a valid
   * analogical proportion or not. 
   * @return true if the proportion is valid.
   */
  private boolean check(){
    if (! this.checkCounts())
      return false;
    
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
  
  /**
   * Checks whether the counts of items in the 4 sequences make an analogical proportion
   * impossible or not.
   * @return false if the proportion between the sequences is impossible
   */
  private boolean checkCounts() {
    if (this.A.size() + this.D.size() != this.B.size() + this.C.size())
      return false;
    
    Map<E, Integer> counts = new HashMap<E, Integer>();
    
    for (int i=0; i < this.A.size(); i++)
      counts.put(this.A.get(i), counts.getOrDefault(this.A.get(i), 0) +1);
    for (int i=0; i < this.D.size(); i++)
      counts.put(this.D.get(i), counts.getOrDefault(this.D.get(i), 0) +1);
    for (int i=0; i < this.B.size(); i++)
      counts.put(this.B.get(i), counts.getOrDefault(this.B.get(i), 0) -1);
    for (int i=0; i < this.C.size(); i++)
      counts.put(this.C.get(i), counts.getOrDefault(this.C.get(i), 0) -1);
    
    Iterator<Integer> it = counts.values().iterator();
    while (it.hasNext())
      if (it.next() != 0)
        return false;
    
    return true;
  }

  /**
   * Checks in full depth whether the 4 sequences of this Proportion are a valid
   * analogical proportion or not (uses caching of the result if available). 
   * @return true if the proportion is valid.
   */
  public boolean isValid(){
    if (this.valid == null){
      this.valid = check();
    }
    return this.valid;
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
