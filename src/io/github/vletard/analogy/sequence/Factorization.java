package io.github.vletard.analogy.sequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.github.vletard.analogy.Element;
import io.github.vletard.analogy.SubtypeRebuilder;

/**
 * This class represents a list of {@link Factor}. The factor configurations (straight or crossed) are alternating in a factorization,
 * indeed factor boundaries are defined by a configuration change. As a consequence, if the first factor of the factorization is straight,
 * every even factor will be straight and every odd factor will be crossed (assuming the first one has index 0).
 * In general factors, and factor lists are not meant to be edited, {@link Factorization} is immutable.
 * @author Vincent Letard
 */
public class Factorization<E, S extends Sequence<E>> {
  
  private final List<Factor<E, S>> factorList;
  private final SubtypeRebuilder<Sequence<E>, S> rebuilder;
  
  public Factorization(SubtypeRebuilder<Sequence<E>, S> rebuilder) {
    this.factorList = Collections.unmodifiableList(new ArrayList<Factor<E, S>>());
    this.rebuilder = rebuilder;
  }
  
  private Factorization(List<Factor<E, S>> factorList, SubtypeRebuilder<Sequence<E>, S> rebuilder) {
    this.factorList = Collections.unmodifiableList(new ArrayList<Factor<E, S>>(factorList));
    this.rebuilder = rebuilder;
  }
  
  /**
   * Extends this factorization with the provided item on B and D if crossed is true,
   * on A and B otherwise.
   * The extended Factor list is returned, the provided list is not edited.
   * @param crossed Whether the new item should be added as crossed factor or not (see {@link Factor}).
   * @param item Item to be added for extension.
   * @return An immutable extension of this factorization with the provided item.
   */
  public Factorization<E, S> extendListB(boolean crossed, E item) {
    LinkedList<Factor<E, S>> newList = new LinkedList<Factor<E, S>>(this.factorList);
    if (newList.isEmpty() || newList.getLast().isCrossed() != crossed)
      newList.add(new Factor<E, S>(crossed, Collections.singletonList(item), Collections.emptyList(), this.rebuilder));
    else
      newList.add(new Factor<E, S>(newList.removeLast(), Collections.singletonList(item), Collections.emptyList()));
    return new Factorization<E, S>(newList, this.rebuilder);
  }
  
  /**
   * Extends this factorization with the provided item on A and C if crossed is true,
   * on C and D otherwise.
   * The extended Factor list is returned, the provided list is not edited.
   * @param crossed Whether the new item should be added as crossed factor or not (see {@link Factor}).
   * @param item Item to be added for extension.
   * @return An immutable extension of this factorization with the provided item.
   */
  public Factorization<E, S> extendListC(boolean crossed, E item) {
    LinkedList<Factor<E, S>> newList = new LinkedList<Factor<E, S>>(this.factorList);
    if (newList.isEmpty() || newList.getLast().isCrossed() != crossed)
      newList.add(new Factor<E, S>(crossed, Collections.emptyList(), Collections.singletonList(item), rebuilder));
    else
      newList.add(new Factor<E, S>(newList.removeLast(), Collections.emptyList(), Collections.singletonList(item)));
    return new Factorization<E, S>(newList, this.rebuilder);
  }
  
  /**
   * Extracts, concatenates and returns the specified element of this factorization.
   * @param e The element to be extracted.
   * @return A list representing the concatenation of the sequence of the element.
   */
  public Sequence<E> extractElement(Element e){
    Sequence<E> s = new Sequence<E>(Collections.emptyList());
    for (Factor<E, S> f: this.factorList) {
      if ((f.isCrossed() && (e == Element.B || e == Element.D))
          || (!f.isCrossed() && (e == Element.A || e == Element.B)))
        s = Sequence.concat(s, f.getB());
      else
        s = Sequence.concat(s, f.getC());
    }
    return new Sequence<E>(s);
  }
  
  /**
   * Returns the degree (number of factors) of this factorization.
   * @return The degree of the factorization.
   */
  public int degree() {
    return this.factorList.size();
  }
  
  /**
   * Returns the size (length of alignment in number of objects) of this factorization.
   * @return The size of the factorization.
   */
  public int size() {
    int size = 0;
    for (Factor<E, S> f: this.factorList)
      size += Math.max(f.getB().concatenate().length(), f.getC().concatenate().length());
    return size;
  }

  /**
   * Computes a 4 line string representation of the aligned factors of this factorization.
   * @return A 4 line string representation of the factorization.
   */
  @Override
  public String toString() {
    ArrayList<Integer> maxSizes = new ArrayList<Integer>();
    for (Factor<E, S> f: this.factorList)
      maxSizes.add(Math.max(f.getB().concatenate().length(), f.getC().concatenate().length()));
    
    String str = "";
    for (int i = 0; i < 4; i++) {
      str += i + " ";
      for (int j = 0; j < this.factorList.size(); j++) {
        Factor<E, S> f = this.factorList.get(j);
        int size = maxSizes.get(j);
        String content;
        if (i==1 || (i==0 && !f.isCrossed()) || (i==3 && f.isCrossed()))
          content = f.getB().concatenate();
        else
          content = f.getC().concatenate();
        str += String.format("%" + size + "s", content);
        if (j < this.factorList.size() - 1)
          str += "|";
      }
      str += "\n";
    }
    return str;
  }
}
