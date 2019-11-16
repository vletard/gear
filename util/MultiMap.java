package util;

public interface MultiMap<K, V> extends UnmodifiableMultiMap<K, V> {
  
  /**
   * Links the specified value to the specified key in this map (optional operation).
   * How the value will be added to the already existing ones depend on the implementation of the MultiMap.
   * @param key key with which the specified value is to be associated
   * @param value value to be associated with the specified key
   * @return the number of values that are now associated with the key
   */
  public int put(K key, V value);
}
