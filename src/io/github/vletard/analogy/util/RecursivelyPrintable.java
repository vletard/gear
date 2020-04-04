package io.github.vletard.analogy.util;

public interface RecursivelyPrintable {
  /**
   * Returns a recursive representation of this object, using the provided offset.
   * @param offset number of spaces left for each level of recursion
   * @return a recursive string representation of this object
   */
  public String prettyPrint(int offset);
}
