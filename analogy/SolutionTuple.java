package analogy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import util.Tuple;

public class SolutionTuple extends Tuple<SolutionBag<Object>> implements SolutionBag<Tuple<?>> {

  public SolutionTuple(Map<?, SolutionBag<Object>> m) {
    super(m);
  }

  @Override
  public Iterator<Tuple<?>> iterator() {
    return new Iterator<Tuple<?>>() {
      private final ArrayList<Object> keys;
      private final ArrayList<Iterator<Object>> iterators;
      private final ArrayList<Object> values;
      private boolean hasNextElement;

      {
        this.keys = new ArrayList<Object>();
        this.iterators = new ArrayList<Iterator<Object>>();
        this.values = new ArrayList<Object>();
        this.hasNextElement = true;
        for (Object key: SolutionTuple.this.keySet()) {
          Iterator<Object> it = SolutionTuple.this.get(key).iterator();
          if (!it.hasNext()) {
            this.hasNextElement = false;
            break;
          }
          this.keys.add(key);
          this.iterators.add(it);
          this.values.add(it.next());
        }
      }

      @Override
      public boolean hasNext() {
        return this.hasNextElement;
      }

      @Override
      public Tuple<Object> next() {
        HashMap<Object, Object> m = new HashMap<Object, Object>();
        boolean increment = false;
        for (int i = 0; i < this.keys.size(); i++) {
          // setting Map value
          m.put(this.keys.get(i), this.values.get(i));

          // incrementing iterator list
          Iterator<Object> it = this.iterators.get(i);
          if (it.hasNext()) {
            this.values.set(i, it.next());
            increment = true;
          }
          else
            this.iterators.set(i, SolutionTuple.this.get(this.keys.get(i)).iterator());
        }
        this.hasNextElement = increment;
        return new Tuple<Object>(m);
      }
    };
  }
  
  public SolutionBag<Object> genericCast(){
    return new SolutionBag<Object>() {
      
      @Override
      public Iterator<Object> iterator() {
        return new Iterator<Object>() {
          private final Iterator<Tuple<?>> it = SolutionTuple.this.iterator();
          
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
