package util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.SortedMap;
import java.util.TreeMap;

public class QueueMultiSortedMap<K extends Comparable<K>, V> implements UnmodifiableQueueMultiSortedMap<K, V>, MultiSortedMap<K, V> {
  private SortedMap<K, Queue<V>> map;
  private int size;
  
  public QueueMultiSortedMap(){
    this.map = new TreeMap<K, Queue<V>>();
    this.size = 0;
  }

  @Override
  public Queue<V> get(Object key) {
    return this.map.get(key);
  }

  public void add(K key, V value) {
    this.map.putIfAbsent(key, new LinkedList<V>());
    this.map.get(key).add(value);
    this.size++;
  }

  @Override
  public boolean isEmpty() {
    return this.size == 0;
  }

  /**
   * Returns the first (lowest) key currently in this map.
   * @return the first key.
   */
  public K firstKey() {
    return this.map.firstKey();
  }
  
  /**
   * Retrieves and removes the head (first element) of the first Queue in the SortedMap,
   * or null if this SortedMap is empty.
   * If the polled Queue becomes empty, it is then removed from the SortedMap.
   * @param key the key of the queue which head is to be polled
   * @return the polled item, or null if the queue indexed by key is empty or uninitialized.
   */
  public V poll() {
    if (this.isEmpty())
      return null;
    else {
      K firstKey = this.firstKey();
      V item = this.map.get(firstKey).poll();
      this.size--;
      if (this.map.get(firstKey).isEmpty())
        this.map.remove(firstKey);
      return item;
    }
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public boolean containsKey(Object key) {
    return this.map.containsKey(key);
  }

  @Override
  public int put(K key, V value) {
    this.map.putIfAbsent(key, new LinkedList<V>());
    Queue<V> q = this.map.get(key);
    q.add(value);
    return q.size();
  }
  
  @Override
  public String toString() {
	  return this.map.toString();
  }
}


