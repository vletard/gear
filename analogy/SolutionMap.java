package analogy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import analogy.sequence.Factor;
import util.Sequence;

/**
 * This map associates each sequence representing a solution to the set of factorizations
 * that have been found giving that solution.
 * @author Vincent Letard
 *
 * @param <T> Type of objects in the sequences and factors.
 */
public class SolutionMap<T> extends HashMap<Sequence<T>, Set<List<Factor<T>>>> implements SolutionBag<Sequence<T>>{
  private static final long serialVersionUID = -8301614387608491626L;

  @Override
  public Iterator<Sequence<T>> iterator() {
    return this.keySet().iterator();
  }

  public SolutionBag<Object> genericCast() {
    return new SolutionBag<Object>() {
      
      @Override
      public Iterator<Object> iterator() {
        return new Iterator<Object>() {
          private Iterator<Sequence<T>> it = SolutionMap.this.iterator();

          @Override
          public boolean hasNext() {
            return this.it.hasNext();
          }

          @Override
          public Object next() {
            return this.it.next();
          }
          
        };
      }
    };
  }
}
