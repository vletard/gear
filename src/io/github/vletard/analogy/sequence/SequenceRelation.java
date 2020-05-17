package io.github.vletard.analogy.sequence;

import io.github.vletard.analogy.Element;
import io.github.vletard.analogy.Relation;

public class SequenceRelation<E, S extends Sequence<E>> implements Relation {
  private final Factorization<E, S> factorization;

  public SequenceRelation(Factorization<E, S> factorization) {
    this.factorization = factorization;
  }

  @Override
  public Relation dual() {
    return new SequenceRelation<E, S>(this.factorization.dual());
  }

  @Override
  public String displayStraight() {
    return this.factorization.displayElement(Element.A) + " -> " + this.factorization.displayElement(Element.B);
  }

  @Override
  public String displayCrossed() {
    return this.factorization.displayElement(Element.A) + " -> " + this.factorization.displayElement(Element.C);
  }
  
  @Override
  public String toString() {
    return this.displayStraight() + "\n" + this.displayCrossed();
  }
}
