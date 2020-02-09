package analogy.tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import analogy.Solution;

public class TupleSolution<T> extends Solution<Tuple<T>> {
  
  private static <T> Tuple<T> initContent(Map<Object, Solution<T>> m){
    HashMap<Object, T> content = new HashMap<Object, T>();
    for (Entry<Object, Solution<T>> e: m.entrySet())
      content.put(e.getKey(), e.getValue().getContent());
    return new Tuple<T>(content);
  }

  public TupleSolution(Map<Object, Solution<T>> m, int degree) {
    super(initContent(m), degree);
    // TODO add recursive information
  }

}
