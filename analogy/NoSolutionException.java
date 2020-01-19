package analogy;

/**
 * This is an exception to be thrown when trying to perform an operation on a solution
 * but no solution could be found.
 * @author Vincent Letard
 */
public class NoSolutionException extends Exception {
  private static final long serialVersionUID = 5899880279685014860L;
  public NoSolutionException(String string) {
    super(string);
  }
  public NoSolutionException() {
  }
}
