package util;

import java.util.Collection;

/**
 * A MultiMap is a Map that can pair multiple elements to each key.
 * @author Vincent Letard
 *
 * @param <K> Type of the keys of this MultiMap
 * @param <V> Type of the values of the MultiMap
 */
public interface UnmodifiableMultiMap<K, V> {
  public class Entry<K, V> {

    private K key;
    private Collection<V> value;
    
    public Entry(K key, Collection<V> value){
      this.key = key;
      this.value = value;
    }
    
    public K getKey() {
      return key;
    }

    public Collection<V> getValue() {
      return value;
    }
  }

  /**
   * Returns a Collection view of the mappings contained in this map.
   * The collection is backed by the map, so changes to the map are reflected in the collection, and vice-versa.
   * @return a collection view of the mappings contained in this map
   */
//  public Collection<Map.Entry<K, V>> entryCollection();
  
  /**
   * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
   * @param key the key whose associated value is to be returned
   * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
   */
  public Collection<V> get(Object key);
  
  /**
   * Returns the number of key-value mappings in this map. If the map contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
   * @return the number of key-value mappings in this map
   */
  public int size();
  
  /**
   * Returns true if this map contains no key-value mappings.
   * @return true if this map contains no key-value mappings
   */
  public default boolean isEmpty() {
    return this.size() == 0;
  }
  
  /**
   * Returns true if this map contains a mapping for the specified key.
   * More formally, returns true if and only if this map contains a mapping for a key k such that (key==null ? k==null : key.equals(k)).
   * (There can be at most one such mapping.)
   * @param key key whose presence in this map is to be tested
   * @return true if this map contains a mapping for the specified key
   */
  public boolean containsKey(Object key);
}
