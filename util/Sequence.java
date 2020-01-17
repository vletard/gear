package util;

import java.util.ArrayList;
import java.util.Iterator;

public class Sequence<T> implements Iterable<T> {
  private final ArrayList<T> sequence;

  public Sequence(Iterable<T> s) {
    this.sequence = new ArrayList<T>();
    for (T item: s)
      this.sequence.add(item);
  }

  public int size() {
    return this.sequence.size();
  }

  public T get(int i) {
    return this.sequence.get(i);
  }
  
  public ArrayList<T> toList(){
    return new ArrayList<T>(this.sequence);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
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
    Sequence other = (Sequence) obj;
    if (sequence == null) {
      if (other.sequence != null)
        return false;
    } else if (!sequence.equals(other.sequence))
      return false;
    return true;
  }

  @Override
  public Iterator<T> iterator() {
    return this.sequence.iterator();
  }
  
  @Override
  public String toString() {
    return this.sequence.toString();
  }
}
