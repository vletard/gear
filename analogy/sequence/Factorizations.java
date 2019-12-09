package analogy.sequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import analogy.Element;
import util.Sequence;

/**
 * This tool class provides primitives for managing Factor lists.
 * In general factors, and factor lists are not meant to be edited. No Factor list returned
 * by methods of this class is mutable.
 * @author Vincent Letard
 *
 */
public class Factorizations {

  /**
   * Extends the provided Factor list with the provided item on B and D if crossed is true,
   * on A and B otherwise.
   * The extended Factor list is returned, the provided list is not edited.
   * @param <E> Type of objects in the factorization.
   * @param factors The factorization to be extended, will not be edited.
   * @param crossed Whether the new item should be added as crossed factor or not (see {@link Factor}).
   * @param item Item to be added for extension.
   * @return An immutable extension of the provided list with the provided item.
   */
  public static <E> List<Factor<E>> extendListB(List<Factor<E>> factors, boolean crossed, E item) {
    LinkedList<Factor<E>> newList = new LinkedList<Factor<E>>(factors);
    if (newList.isEmpty() || newList.getLast().isCrossed() != crossed)
      newList.add(new Factor<E>(crossed, Collections.singletonList(item), Collections.emptyList()));
    else
      newList.add(new Factor<E>(newList.removeLast(), Collections.singletonList(item), Collections.emptyList()));
    return Collections.unmodifiableList(newList);
  }
  
  /**
   * Extends the provided Factor list with the provided item on A and C if crossed is true,
   * on C and D otherwise.
   * The extended Factor list is returned, the provided list is not edited.
   * @param <E> Type of objects in the factorization.
   * @param factorization The factorization to be extended, will not be edited.
   * @param crossed Whether the new item should be added as crossed factor or not (see {@link Factor}).
   * @param item Item to be added for extension.
   * @return An immutable extension of the provided list with the provided item.
   */
  public static <E> List<Factor<E>> extendListC(List<Factor<E>> factorization, boolean crossed, E item) {
    LinkedList<Factor<E>> newList = new LinkedList<Factor<E>>(factorization);
    if (newList.isEmpty() || newList.getLast().isCrossed() != crossed)
      newList.add(new Factor<E>(crossed, Collections.emptyList(), Collections.singletonList(item)));
    else
      newList.add(new Factor<E>(newList.removeLast(), Collections.emptyList(), Collections.singletonList(item)));
    return Collections.unmodifiableList(newList);
  }
  
  /**
   * Returns a new empty (immutable) factorization.
   * @param <E> Type of objects in the factorization (actually in the extensions of the factorization).
   * @return An immutable factorization of type <E>.
   */
  public static <E> List<Factor<E>> newFactorization(){
    return Collections.emptyList();
  }
  
  /**
   * Concatenates string expressions of items in the provided list and return the resulting string.
   * @param <E> Type of objects in the factorization.
   * @param l List of which elements are to be concatenated.
   * @return The concatenation of the string representation of all the elements in the provided list.
   */
  private static <E> String concatenateList(List<E> l) {
    String str = "";
    Iterator<E> it = l.iterator();
    while (it.hasNext())
      str += it.next().toString();
    return str;
  }
  
  /**
   * Extracts, concatenates and returns the specified element of the given factorization.
   * @param <E> Type of objects in the factorization.
   * @param factorization The Factorization from which to extract an element.
   * @param e The element to be extracted.
   * @return A list representing the concatenation of the sequence of the element.
   */
  public static <E> Sequence<E> extractElement(List<Factor<E>> factorization, Element e){
    LinkedList<E> element = new LinkedList<E>();
    for (Factor<E> f: factorization) {
      if ((f.isCrossed() && (e == Element.B || e == Element.D))
          || (!f.isCrossed() && (e == Element.A || e == Element.B)))
        element.addAll(f.getB());
      else
        element.addAll(f.getC());
    }
    return new Sequence<E>(element);
  }
  
  /**
   * Returns the size (length of alignment in number of objects) of the provided factorization.
   * @param factorization The factorization from which to compute the size.
   * @return The size of the factorization.
   */
  public static <E> int getSize(List<Factor<E>> factorization) {
    int size = 0;
    for (int i=0; i<factorization.size(); i++) {
      Factor<E> f = factorization.get(i);
      size += Math.max(concatenateList(f.getB()).length(), concatenateList(f.getC()).length());
    }
    return size;
  }

  /**
   * Computes a 4 line string representation of the aligned factors of the given factorization.
   * @param <E> Type of objects in the factorization.
   * @param factorization The factorization to be aligned in a string.
   * @return A 4 line string representation of the factorization.
   */
  public static <E> String toString(List<Factor<E>> factorization) {
    ArrayList<Integer> maxSizes = new ArrayList<Integer>();
    for (int i=0; i<factorization.size(); i++) {
      Factor<E> f = factorization.get(i);
      maxSizes.add(Math.max(concatenateList(f.getB()).length(), concatenateList(f.getC()).length()));
    }
    
    String str = "";
    for (int i=0; i<4; i++) {
      str += i + " ";
      for (int j=0; j<factorization.size(); j++) {
        Factor<E> f = factorization.get(j);
        int size = maxSizes.get(j);
        String content;
        if (i==1 || (i==0 && !f.isCrossed()) || (i==3 && f.isCrossed()))
          content = concatenateList(f.getB());
        else
          content = concatenateList(f.getC());
        str += String.format("%" + size + "s", content);
        if (j<factorization.size()-1)
          str += "|";
      }
      str += "\n";
    }
    return str;
  }
}
