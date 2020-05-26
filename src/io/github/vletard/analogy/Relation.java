package io.github.vletard.analogy;

public interface Relation {
  public Relation dual();
  public String displayStraight();
  public String displayCrossed();
  public boolean isIdentityStraight();
  public boolean isIdentityCrossed();
}
