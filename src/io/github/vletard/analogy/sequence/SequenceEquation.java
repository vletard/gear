package io.github.vletard.analogy.sequence;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import io.github.vletard.analogy.DefaultEquation;
import io.github.vletard.analogy.Element;
import io.github.vletard.analogy.SubtypeRebuilder;

/**
 * This class represents an analogical equation on sequences.
 * Its solution are provided using the specialized class {@link SequenceSolution}.
 * @author Vincent Letard
 *
 * @param <E> The items composing the sequences of the analogical Equation.
 */
public class SequenceEquation<E, Subtype extends Sequence<E>> extends DefaultEquation<Subtype, SequenceSolution<E, Subtype>>{

  private final SubtypeRebuilder<Sequence<E>, Subtype> rebuilder;
  
  public SequenceEquation(Subtype a, Subtype b, Subtype c, SubtypeRebuilder<Sequence<E>, Subtype> rebuilder){
    super(a, b, c);
    this.rebuilder = rebuilder;
  }

  /**
   * Checks whether the counts of items in the 3 available sequences make a solution
   * to this analogical equation impossible or not.
   * @return false if the proportion between the sequences is impossible
   */
  private boolean checkCounts() {
    if (this.a.size() - this.b.size() - this.c.size() > 0)
      return false;

    Map<E, Integer> counts = new HashMap<E, Integer>();

    for (int i=0; i < this.a.size(); i++)
      counts.put(this.a.get(i), counts.getOrDefault(this.a.get(i), 0) +1);
    for (int i=0; i < this.b.size(); i++)
      counts.put(this.b.get(i), counts.getOrDefault(this.b.get(i), 0) -1);
    for (int i=0; i < this.c.size(); i++)
      counts.put(this.c.get(i), counts.getOrDefault(this.c.get(i), 0) -1);

    Iterator<Integer> it = counts.values().iterator();
    while (it.hasNext())
      if (it.next() > 0)
        return false;

    return true;
  }

  @Override
  public Iterator<SequenceSolution<E, Subtype>> iterator() {
    if (! SequenceEquation.this.checkCounts())
      return Collections.emptyIterator();

    else
      return new Iterator<SequenceSolution<E, Subtype>>() {
        private final SortedMap<Integer, Set<EquationReadingHead<E, Subtype>>> readingRegister = new TreeMap<Integer, Set<EquationReadingHead<E, Subtype>>>();
        private SequenceSolution<E, Subtype> nextElement = null;
  
        {
          EquationReadingHead<E, Subtype> head = new EquationReadingHead<E, Subtype>(SequenceEquation.this);
          Set<EquationReadingHead<E, Subtype>> s = new HashSet<EquationReadingHead<E, Subtype>>();
          s.add(head);
          this.readingRegister.put(head.getCurrentDegree(), s);
        }
  
        @Override
        public boolean hasNext() {
          while (this.nextElement == null && !this.readingRegister.isEmpty()) {
            int currentDegree = readingRegister.firstKey();
            Set<EquationReadingHead<E, Subtype>> s = readingRegister.get(currentDegree);
            EquationReadingHead<E, Subtype> currentHead = s.iterator().next();
            s.remove(currentHead);
            if (s.isEmpty())
              readingRegister.remove(currentDegree);
  
            if (currentHead.isFinished()) {
              List<Factor<E>> factorList = currentHead.getFactors();
              Sequence<E> sequence = Factorizations.extractElement(factorList, Element.D);
              this.nextElement = new SequenceSolution<E, Subtype>(SequenceEquation.this.rebuilder.rebuild(sequence), currentDegree, factorList);
            }
            else{
              try{
                for (Step step : new Step[]{Step.AB, Step.AC, Step.CD, Step.BD}) {
                  if (currentHead.canStep(step)){
                    EquationReadingHead<E, Subtype> newHead = currentHead.makeStep(step, true);
                    int newDegree = newHead.getCurrentDegree();
                    readingRegister.putIfAbsent(newDegree, new HashSet<EquationReadingHead<E, Subtype>>());
                    readingRegister.get(newDegree).add(newHead);
                  }
                }
              } catch(ImpossibleStepException e) {
                throw new RuntimeException(e);
              }
            }
          }
  
          if (this.nextElement == null)
            return false;
          else
            return true;
        }
  
        @Override
        public SequenceSolution<E, Subtype> next() {
          if (this.hasNext()) {
            SequenceSolution<E, Subtype> next = this.nextElement;
            this.nextElement = null;
            return next;
          }
          else
            throw new NoSuchElementException();
        }
    };
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((a == null) ? 0 : a.hashCode());
    result = prime * result + ((b == null) ? 0 : b.hashCode());
    result = prime * result + ((c == null) ? 0 : c.hashCode());
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
    SequenceEquation other = (SequenceEquation) obj;
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
    if (c == null) {
      if (other.c != null)
        return false;
    } else if (!c.equals(other.c))
      return false;
    return true;
  }
}
