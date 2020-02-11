package io.github.vletard.analogy.sequence;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import io.github.vletard.analogy.DefaultProportion;

/**
 * This class represents an analogical proportion. It can be initialized with 4 sequence elements
 * and offers a method to check whether the sequences are in proportion or not.
 * @author Vincent Letard
 *
 * @param <E> Parametric type of the elements inside sequences.
 */
public class SequenceProportion<E> extends DefaultProportion<Sequence<E>>{
  private Boolean valid;
  private List<Factor<E>> factorization;
  
  public static class InvalidProportionException extends Exception{
    private static final long serialVersionUID = -5958668638828140006L;
    public InvalidProportionException(String string) {
      super(string);
    }
  }

  public SequenceProportion(Sequence<E> a, Sequence<E> b, Sequence<E> c, Sequence<E> d){
    super(a, b, c, d);
  }
  
  /**
   * Gets the (minimal) factorization degree of this analogical Proportion.
   * @return The computed degree.
   * @throws InvalidProportionException if the Proportion is invalid.
   */
  public int getDegree() throws InvalidProportionException {
    return this.getFactorization().size();
  }

  /**
   * Gets the (minimal) factorization of this analogical Proportion.
   * @return The computed factorization.
   * @throws InvalidProportionException if the Proportion is invalid.
   */
  public List<Factor<E>> getFactorization() throws InvalidProportionException {
    if (this.isValid())
      return this.factorization;
    else
      throw new InvalidProportionException("An invalid proportion does not have a factorization.");
  }

  /**
   * Checks in full depth whether the 4 sequences of this Proportion are a valid
   * analogical proportion or not. 
   * @return true if the proportion is valid.
   */
  private boolean check(){
    if (! this.checkCounts())
      return false;

    SortedMap<Integer, Set<ProportionReadingHead<E>>> readingRegister = new TreeMap<Integer, Set<ProportionReadingHead<E>>>();
    {
      ProportionReadingHead<E> head = new ProportionReadingHead<E>(this);
      Set<ProportionReadingHead<E>> s = new HashSet<ProportionReadingHead<E>>();
      s.add(head);
      readingRegister.put(head.getCurrentDegree(), s);
    }
    while(!readingRegister.isEmpty()){
      int currentDegree = readingRegister.firstKey();
      Set<ProportionReadingHead<E>> s = readingRegister.get(currentDegree);
      ProportionReadingHead<E> currentHead = s.iterator().next();
      s.remove(currentHead);
      if (s.isEmpty())
        readingRegister.remove(currentDegree);
      
      if (currentHead.isFinished()) {
        this.factorization = currentHead.getFactors();
        return true;
      }
      else{
        try{
          for (Step step : new Step[]{Step.AB, Step.AC, Step.CD, Step.BD}) {
            if (currentHead.canStep(step)){
              ProportionReadingHead<E> result = currentHead.makeStep(step, true);
              readingRegister.putIfAbsent(result.getCurrentDegree(), new HashSet<ProportionReadingHead<E>>());
              readingRegister.get(result.getCurrentDegree()).add(result);
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
    if (this.A.size() + this.D.size() - this.B.size() - this.C.size() != 0)
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

  
  @Override
  public String toString(){
    String marker = " :: ";
    if (valid == null || valid == false)
      marker = " ?? ";
    return "[ " + A + " : " + B + marker + C + " : " + D + " ]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((A == null) ? 0 : A.hashCode());
    result = prime * result + ((B == null) ? 0 : B.hashCode());
    result = prime * result + ((C == null) ? 0 : C.hashCode());
    result = prime * result + ((D == null) ? 0 : D.hashCode());
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
    SequenceProportion other = (SequenceProportion) obj;
    if (A == null) {
      if (other.A != null)
        return false;
    } else if (!A.equals(other.A))
      return false;
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
    if (D == null) {
      if (other.D != null)
        return false;
    } else if (!D.equals(other.D))
      return false;
    return true;
  }
}
