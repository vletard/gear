package io.github.vletard.analogy;

import java.util.HashSet;
import java.util.Set;

import io.github.vletard.analogy.sequence.Sequence;
import io.github.vletard.analogy.sequence.SequenceProportion;
import io.github.vletard.analogy.set.ImmutableSet;
import io.github.vletard.analogy.tuple.Tuple;

public class DefaultProportion<T> {
  final public T A, B, C, D;
  private Boolean valid;
  
  public DefaultProportion(T a, T b, T c, T d) {
    this.A = a;
    this.B = b;
    this.C = c;
    this.D = d;
  }

  public boolean isValid() {
    if (this.valid == null)
      this.valid = this.check();
    return this.valid;
  }

  private boolean check() {
    try {
      return this.checkAtomic();
    } catch (NonAtomicException e) {
      return this.checkSet() || this.checkTuple() || this.checkSequence();
    }
  }

  private boolean checkAtomic() throws NonAtomicException {
    if (this.A == null && this.B == null) {
      if (this.C == null && this.D == null)
        return true;
      else if (this.C != null && this.C.equals(this.D))
        return true;
      else
        return false;
    }
    else if (this.A == null && this.C == null) {
      if (this.B.equals(this.D))
        return true;
      else
        return false;
    }
    else if (this.B == null && this.D == null) {
      if (this.A.equals(this.C))
        return true;
      else
        return false;
    }
    else if (this.C == null && this.D == null) {
      if (this.A.equals(this.B))
        return true;
      else
        return false;
    }
    else if (this.A != null && this.B != null && this.C != null && this.D != null)
      if ((this.A.equals(this.B) && this.C.equals(this.D)) || (this.A.equals(this.C) && this.B.equals(this.D)))
        return true;
      else
        throw new NonAtomicException();
    else
      return false;
  }
  
  private boolean checkSet() {
    if (!(this.A instanceof ImmutableSet && this.B instanceof ImmutableSet && this.C instanceof ImmutableSet && this.D instanceof ImmutableSet))
      return false;
    
    ImmutableSet<Object> a = (ImmutableSet<Object>) this.A;
    ImmutableSet<Object> b = (ImmutableSet<Object>) this.B;
    ImmutableSet<Object> c = (ImmutableSet<Object>) this.C;
    ImmutableSet<Object> d = (ImmutableSet<Object>) this.D;
    
    HashSet<Object> union = new HashSet<Object>();
    union.addAll(a.asSet());
    union.addAll(b.asSet());
    union.addAll(c.asSet());
    union.addAll(d.asSet());
    
    for (Object key: union) {
      if (!new DefaultProportion<Boolean>(a.contains(key), b.contains(key), c.contains(key), d.contains(key)).isValid())
        return false; // if any of the items does not respect analogical constraints over the 4 sets, the proportion is invalid
    }
    return true;
  }
  
  private boolean checkTuple() {
    if (!(this.A instanceof Tuple && this.B instanceof Tuple && this.C instanceof Tuple && this.D instanceof Tuple))
      return false;
    
    Tuple<Object> A = (Tuple<Object>) this.A;
    Tuple<Object> B = (Tuple<Object>) this.B;
    Tuple<Object> C = (Tuple<Object>) this.C;
    Tuple<Object> D = (Tuple<Object>) this.D;
    
    Set<Object> keys = new HashSet<Object>();
    keys.addAll(A.keySet());
    keys.addAll(B.keySet());
    keys.addAll(C.keySet());
    keys.addAll(D.keySet());
    
    for (Object key: keys) {
      if (!new DefaultProportion<Object>(A.get(key), B.get(key), C.get(key), D.get(key)).isValid())
        return false; // if any of the entries does not respect analogical constraints over the 4 tuples, the proportion is invalid
    }
    return true;
  }

  private boolean checkSequence() {
    if (!(this.A instanceof Sequence && this.B instanceof Sequence && this.C instanceof Sequence && this.D instanceof Sequence))
      return false;
    
    Sequence<Object> A = (Sequence<Object>)this.A;
    Sequence<Object> B = (Sequence<Object>)this.B;
    Sequence<Object> C = (Sequence<Object>)this.C;
    Sequence<Object> D = (Sequence<Object>)this.D;
    
    return new SequenceProportion<Object>(A, B, C, D).isValid();
  }
}
