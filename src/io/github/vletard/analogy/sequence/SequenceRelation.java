package io.github.vletard.analogy.sequence;

import io.github.vletard.analogy.Element;
import io.github.vletard.analogy.Relation;

public class SequenceRelation<E, S extends Sequence<E>> implements Relation {
  private final Factorization<E, S> factorization;
  private final boolean crossed;

  private SequenceRelation(Factorization<E, S> factorization, boolean crossed) {
    this.factorization = factorization;
    this.crossed = crossed;
  }
  
  public static <E, S extends Sequence<E>> SequenceRelation<E, S> newStraightRelation(Factorization<E, S> factorization) {
    return new SequenceRelation<E, S>(factorization, false);
  }
  
  public static <E, S extends Sequence<E>> SequenceRelation<E, S> newCrossedRelation(Factorization<E, S> factorization) {
    return new SequenceRelation<E, S>(factorization, true);
  }

  @Override
  public String toString() {
    if (this.isIdentity())
      return "identity";
    
    String output = this.factorization.displayElement(Element.A) + " -> ";
    if (this.crossed)
      return output + this.factorization.displayElement(Element.C);
    else
      return output + this.factorization.displayElement(Element.B);
  }
  
  @Override
  public boolean isIdentity() {
    if (this.crossed)
      return this.factorization.extractElement(Element.A).equals(this.factorization.extractElement(Element.C));
    else
      return this.factorization.extractElement(Element.A).equals(this.factorization.extractElement(Element.B));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.toString().hashCode();
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
    SequenceRelation other = (SequenceRelation) obj;
    return this.toString().contentEquals(other.toString());
  }
}
