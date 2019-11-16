package util;

import java.util.Queue;

/**
 * A version of the MultiMap that stores values of each key in a Queue.
 * @author Vincent Letard
 *
 * @param <K> Type of the keys of this QueueMultiMap
 * @param <V> Type of the values of the QueueMultiMap
 */
public interface UnmodifiableQueueMultiSortedMap<K, V> extends UnmodifiableMultiMap<K, V>{
  public class Entry<K, V> extends UnmodifiableMultiMap.Entry<K, V>{

    public Entry(K key, Queue<V> value){
      super(key, value);
    }

    public Queue<V> getValue() {
      return (Queue<V>) super.getValue();
    }
  }

  public Queue<V> get(Object key);
}
