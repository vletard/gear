package util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Tuple<V> {
  private final HashMap<?, V> items;
  
  public Tuple(Map<?, V> m) {
    this.items = new HashMap<Object, V>(m);
  };

  public Tuple(Collection<V> c) {
    HashMap<Integer, V> items = new HashMap<Integer, V>();
    int i = 0;
    for (V item: c) {
      items.put(i, item);
      i++;
    }
    this.items = items;
  }

  public Collection<? extends Object> keySet() {
    return this.items.keySet();
  }

  public V get(Object key) {
    return this.items.get(key);
  };
  
  @Override
  public String toString() {
    return this.items.toString();
  }
}
