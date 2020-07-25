package io.github.vletard.analogy;

/**
 * This exception type is to be thrown in case of a failed but non fatal rebuild operation in a {@link SubtypeRebuilder}.
 * It will be systematically caught, displayed and ignored by the solution iterators.
 * @author Vincent Letard
 */
public class RebuildException extends Exception {
  private static final long serialVersionUID = 9147518011599067857L;
  
  public RebuildException(Exception cause) {
    super(cause);
  }
}
