package io.github.vletard.analogy;

public class Solution<T> {
  private final T content;
  private final int degree;
  
  public Solution(T content, int degree) {
    this.content = content;
    this.degree = degree;
  }

  public T getContent() {
    return this.content;
  }
  
  public int getDegree() {
    return this.degree;
  }
  
  @Override
  public String toString() {
    return this.content.toString();
  }
}
