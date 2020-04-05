package io.github.vletard.analogy.tuple;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.github.vletard.analogy.util.InvalidParameterException;
import io.github.vletard.analogy.util.RecursivelyPrintable;

/**
 * Represents a analogy-ready tuple of a fixed number of objects of type V.
 * Each object is added as a key-value mapping.
 * The key can be of any type, and the value is the actual object.
 * One key can only be associated with one object, but an object may be associated with multiple keys.
 * @author Vincent Letard
 *
 * @param <V> type of the objects of this tuple.
 */
public class Tuple<V> implements RecursivelyPrintable, Serializable {
  private static final long serialVersionUID = 4818248083124898868L;
  private final Map<Object, V> items;
  private final Map<Object, V> freeItems;

  /**
   * Constructs a new Tuple using the provided map.
   * @param m mapping of objects for the Tuple.
   */
  public Tuple(Map<?, ? extends V> m) {
    this.items = Collections.unmodifiableMap(new HashMap<Object, V>(m));
    this.freeItems = Collections.emptyMap();
  };

  /**
   * Constructs a new Tuple with a subset of objects that will always have a degree of 0 while solving
   * analogical equations.
   * Note that key set of the free mapping cannot intersect that of the regular one.
   * @param regular mapping of objects for the Tuple.
   * @param free additional mapping of objects for the Tuple.
   * @throws InvalidParameterException if both key sets are not disjoint.
   */
  public Tuple(Map<?, ? extends V> regular, Map<?, ? extends V> free) throws InvalidParameterException {
    HashSet<Object> test = new HashSet<Object>(regular.keySet());
    test.retainAll(free.keySet());
    if (!test.isEmpty())
      throw new InvalidParameterException("The key set of the free mapping cannot intersect that of the regular one.");
    this.items = Collections.unmodifiableMap(new HashMap<Object, V>(regular));
    this.freeItems = Collections.unmodifiableMap(new HashMap<Object, V>(free));
  }

  /**
   * Gets the set of keys of this Tuple.
   * @return the key set of this Tuple.
   */
  public Set<Object> keySet() {
    HashSet<Object> keys = new HashSet<Object>(this.items.keySet());
    keys.addAll(this.freeItems.keySet());
    return keys;
  }

  /**
   * Returns the object associated to the provided key in this Tuple, or null if no such mapping exists.
   * @param key the key to get the mapping from.
   * @return the object associated with the provided key in this Tuple.
   */
  public V get(Object key) {
    if (this.items.containsKey(key))
      return this.items.get(key);
    else
      return this.freeItems.get(key);
  };

  /**
   * Returns whether this Tuple has a mapping to the provided key.
   * @param key the key to check for a mapping.
   * @return true if the key is mapped to something in this Tuple.
   */
  public boolean containsKey(Object key) {
    return this.items.containsKey(key) || this.freeItems.containsKey(key);
  }

  /**
   * Gets the set of free keys of this Tuple, that is keys of objects that
   * won't be considered while solving analogical equations.
   * @return the free key set of this Tuple.
   */
  public Set<Object> freeKeys() {
    return this.freeItems.keySet();
  }

  /**
   * Helper method generating a string of n spaces.
   * @param n number of spaces.
   * @return a string composed of n spaces.
   */
  private static String writeOffset(int n) {
    String s = "";
    for (int i = 0; i < n; i++)
      s += "  ";
    return s;
  }

  private String prettyPrint(int offset, Set<Object> keySet) {
    HashSet<Object> simpleKeys = new HashSet<Object>();
    HashSet<Object> recursiveValues = new HashSet<Object>();
    HashSet<Object> complexKeys = new HashSet<Object>();

    for (Object k: keySet) {
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

    for (Object k: simpleKeys)
      output += writeOffset(offset+1) + k + "=" + this.get(k) + ",\n";

    for (Object k: recursiveValues)
      output += writeOffset(offset+1) + k + "=" + ((RecursivelyPrintable)this.get(k)).prettyPrint(offset+1) + ",\n";

    for (Object k: complexKeys)
      output += writeOffset(offset+1) + k + "=" + this.get(k) + ",\n";

    output = output.substring(0, output.length()-2) + "\n";

    return output;
  }

  @Override
  public String prettyPrint(int offset) {
    String output = "";
    output += "{ \n";
    output += this.prettyPrint(offset, this.items.keySet());
    if (this.freeItems.size() > 0) {
      output += "free";
      output += this.prettyPrint(offset, this.freeItems.keySet());
    }
    output += writeOffset(offset) + "}";

    return output;
  }

  @Override
  public String toString() {
    String str = "<" + this.items.toString();
    if (this.freeItems.size() > 0)
      str += " / " + this.freeItems.toString();
    return str + ">";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((freeItems == null) ? 0 : freeItems.hashCode());
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
    if (freeItems == null) {
      if (other.freeItems != null)
        return false;
    } else if (!freeItems.equals(other.freeItems))
      return false;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }

}
