package util;

public interface UnmodifiableMultiSortedMap<K, V> extends UnmodifiableMultiMap<K, V>{
  
  /**
   * Returns the first (lowest) key currently in this map.
   * @return the first key.
   */
  public K firstKey();
}
