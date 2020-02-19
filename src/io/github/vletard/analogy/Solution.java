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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((content == null) ? 0 : content.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Solution other = (Solution) obj;
    if (content == null) {
      if (other.content != null)
        return false;
    } else if (!content.equals(other.content))
      return false;
    return true;
  }
}
