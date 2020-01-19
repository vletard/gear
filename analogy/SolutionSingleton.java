package analogy;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SolutionSingleton implements SolutionBag<Object> {

  private final Object content;
  
  public SolutionSingleton(Object content) {
    this.content = content;
  }
  
  public Object getContent() {
    return this.content;
  }

  @Override
  public Iterator<Object> iterator() {
    return new Iterator<Object>() {

      private boolean read = false;

      @Override
      public boolean hasNext() {
        return !this.read;
      }

      @Override
      public Object next() {
        if (!this.read) {
          this.read = true;
          return SolutionSingleton.this.content;
        }
        else
          throw new NoSuchElementException();
      }
    };
  }

}
