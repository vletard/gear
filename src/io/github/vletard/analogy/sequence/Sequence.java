package io.github.vletard.analogy.sequence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Sequence<E> implements Iterable<E>, Serializable {
  private static final long serialVersionUID = 7810330061732174873L;
  private final ArrayList<E> sequence;

  public Sequence(Iterable<E> s) {
    this.sequence = new ArrayList<E>();
    for (E item: s)
      this.sequence.add(item);
  }

  public int size() {
    return this.sequence.size();
  }

  public E get(int i) {
    return this.sequence.get(i);
  }
  
  public ArrayList<E> toList(){
    return new ArrayList<E>(this.sequence);
  }

  public static <E> Sequence<E> concat(Sequence<E> a, Sequence<E> b) {
    ArrayList<E> sequence = new ArrayList<E>();
    sequence.addAll(a.sequence);
    sequence.addAll(b.sequence);
    return new Sequence<E>(sequence);
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
    if (!(obj instanceof Sequence))
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
  public Iterator<E> iterator() {
    return this.sequence.iterator();
  }
  
  @Override
  public String toString() {
    return this.sequence.toString();
  }
}
