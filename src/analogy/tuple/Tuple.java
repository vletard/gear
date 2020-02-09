package analogy.tuple;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import util.RecursivelyPrintable;

public class Tuple<V> {
  private final Map<Object, V> items;
  
  public Tuple(Map<Object, V> m) {
    this.items = Collections.unmodifiableMap(new HashMap<Object, V>(m));
  };

  public Collection<? extends Object> keySet() {
    return this.items.keySet();
  }

  public V get(Object key) {
    return this.items.get(key);
  };
  
  private static String writeOffset(int offset) {
    String s = "";
    for (int i = 0; i < offset; i++)
      s += "  ";
    return s;
  }
  
  public String prettyPrint(int offset) {
    HashSet<Object> simpleKeys = new HashSet<Object>();
    HashSet<Object> recursiveValues = new HashSet<Object>();
    HashSet<Object> complexKeys = new HashSet<Object>();
    
    for (Object k: this.keySet()) {
      V v = this.get(k);
      if (v instanceof RecursivelyPrintable)
        recursiveValues.add(k);
      else if (!(k instanceof String || k instanceof Integer || k instanceof Boolean
          || k instanceof Double || k instanceof Float || k instanceof Character))
        complexKeys.add(k);
      else
        simpleKeys.add(k);
    }
    
    String output = "";
    
    output += "{ \n";
    for (Object k: simpleKeys)
      output += writeOffset(offset+1) + k + "=" + this.get(k) + ",\n";

    for (Object k: recursiveValues)
      output += writeOffset(offset+1) + k + "=" + ((RecursivelyPrintable)this.get(k)).prettyPrint(offset+1) + ",\n";
    
    for (Object k: complexKeys)
      output += writeOffset(offset+1) + k + "=" + this.get(k) + ",\n";
    
    output = output.substring(0, output.length()-2) + "\n" + writeOffset(offset) + "}";
    
    return output;
  }
  
  @Override
  public String toString() {
    return this.items.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((items == null) ? 0 : items.hashCode());
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
    Tuple other = (Tuple) obj;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }
  
}
