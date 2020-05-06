package io.github.vletard.analogy.sequence;

import java.util.List;

import io.github.vletard.analogy.SubtypeRebuilder;

public class Factor<E, T extends Sequence<E>> {
  private final T b;
  private final T c;
  private final boolean crossed;
  private final SubtypeRebuilder<Sequence<E>, T> rebuilder;
  
  public Factor(boolean crossed, List<E> b, List<E> c, SubtypeRebuilder<Sequence<E>, T> rebuilder) {
    this.crossed = crossed;
    this.b = rebuilder.rebuild(new Sequence<E>(b));
    this.c = rebuilder.rebuild(new Sequence<E>(c));
    this.rebuilder = rebuilder;
  }

  public Factor(Factor<E, T> factor, List<E> addB, List<E> addC) {
    this.crossed = factor.isCrossed();
    this.rebuilder = factor.rebuilder;
    this.b = this.rebuilder.rebuild(Sequence.concat(factor.b, new Sequence<E>(addB)));
    this.c = this.rebuilder.rebuild(Sequence.concat(factor.c, new Sequence<E>(addC)));
  }

  public T getB() {
    return b;
  }

  public T getC() {
    return c;
  }
  
  public boolean isCrossed() {
    return this.crossed;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((b == null) ? 0 : b.hashCode());
    result = prime * result + ((c == null) ? 0 : c.hashCode());
    result = prime * result + (crossed ? 1231 : 1237);
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
    Factor other = (Factor) obj;
    if (b == null) {
      if (other.b != null)
        return false;
    } else if (!b.equals(other.b))
      return false;
    if (c == null) {
      if (other.c != null)
        return false;
    } else if (!c.equals(other.c))
      return false;
    if (crossed != other.crossed)
      return false;
    return true;
  }
}
