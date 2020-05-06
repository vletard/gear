package io.github.vletard.analogy.sequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import io.github.vletard.analogy.Element;
import io.github.vletard.analogy.SubtypeRebuilder;

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
   * @param <S> Type of sequence in the factorization.
   * @param factors The factorization to be extended, will not be edited.
   * @param crossed Whether the new item should be added as crossed factor or not (see {@link Factor}).
   * @param item Item to be added for extension.
   * @return An immutable extension of the provided list with the provided item.
   */
  public static <E, S extends Sequence<E>> List<Factor<E, S>> extendListB(List<Factor<E, S>> factors, boolean crossed, E item, SubtypeRebuilder<Sequence<E>, S> rebuilder) {
    LinkedList<Factor<E, S>> newList = new LinkedList<Factor<E, S>>(factors);
    if (newList.isEmpty() || newList.getLast().isCrossed() != crossed)
      newList.add(new Factor<E, S>(crossed, Collections.singletonList(item), Collections.emptyList(), rebuilder));
    else
      newList.add(new Factor<E, S>(newList.removeLast(), Collections.singletonList(item), Collections.emptyList()));
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
  public static <E, S extends Sequence<E>> List<Factor<E, S>> extendListC(List<Factor<E, S>> factorization, boolean crossed, E item, SubtypeRebuilder<Sequence<E>, S> rebuilder) {
    LinkedList<Factor<E, S>> newList = new LinkedList<Factor<E, S>>(factorization);
    if (newList.isEmpty() || newList.getLast().isCrossed() != crossed)
      newList.add(new Factor<E, S>(crossed, Collections.emptyList(), Collections.singletonList(item), rebuilder));
    else
      newList.add(new Factor<E, S>(newList.removeLast(), Collections.emptyList(), Collections.singletonList(item)));
    return Collections.unmodifiableList(newList);
  }
  
  /**
   * Returns a new empty (immutable) factorization.
   * @param <E> Type of objects in the factorization (actually in the extensions of the factorization).
   * @return An immutable factorization of type <E>.
   */
  public static <E, S extends Sequence<E>> List<Factor<E, S>> newFactorization(){
    return Collections.emptyList();
  }
  
  /**
   * Concatenates string expressions of items in the provided list and return the resulting string.
   * @param l List of which elements are to be concatenated.
   * @return The concatenation of the string representation of all the elements in the provided list.
   */
  private static String concatenateList(Sequence<?> l) {
    String str = "";
    Iterator<?> it = l.iterator();
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
  public static <E, S extends Sequence<E>> Sequence<E> extractElement(List<Factor<E, S>> factorization, Element e){
    Sequence<E> s = new Sequence<E>(Collections.emptyList());
    for (Factor<E, S> f: factorization) {
      if ((f.isCrossed() && (e == Element.B || e == Element.D))
          || (!f.isCrossed() && (e == Element.A || e == Element.B)))
        s = Sequence.concat(s, f.getB());
      else
        s = Sequence.concat(s, f.getC());
    }
    return new Sequence<E>(s);
  }
  
  /**
   * Returns the size (length of alignment in number of objects) of the provided factorization.
   * @param factorization The factorization from which to compute the size.
   * @return The size of the factorization.
   */
  public static <E, S extends Sequence<E>> int getSize(List<Factor<E, S>> factorization) {
    int size = 0;
    for (int i=0; i<factorization.size(); i++) {
      Factor<E, S> f = factorization.get(i);
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
  public static <E, S extends Sequence<E>> String toString(List<Factor<E, S>> factorization) {
    ArrayList<Integer> maxSizes = new ArrayList<Integer>();
    for (int i=0; i<factorization.size(); i++) {
      Factor<E, S> f = factorization.get(i);
      maxSizes.add(Math.max(concatenateList(f.getB()).length(), concatenateList(f.getC()).length()));
    }
    
    String str = "";
    for (int i=0; i<4; i++) {
      str += i + " ";
      for (int j=0; j<factorization.size(); j++) {
        Factor<E, S> f = factorization.get(j);
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
