package util;

import java.util.List;
import java.lang.Cloneable;
import java.util.Collection;
import java.util.RandomAccess;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Collections;

/*
 * An ArrayList that is unmodifiable, immutable.
 */
public class UnmodifiableArrayList<E> implements List<E>, RandomAccess, Cloneable{
  private List<E> list;

  public UnmodifiableArrayList(List<E> src){
    this.list = Collections.unmodifiableList(new ArrayList<E>(src));
  }

  /*
   * @Override
   */
  public boolean add(E e){
    return this.list.add(e);
  }

  /*
   * @Override
   */
  public void add(int i, E e){
    this.list.add(i, e);
  }

  /*
   * @Override
   */
  public boolean addAll(Collection<? extends E> c){
    return this.list.addAll(c);
  }

  /*
   * @Override
   */
  public boolean addAll(int i, Collection<? extends E> c){
    return this.list.addAll(i, c);
  }

  /*
   * @Override
   */
  public boolean contains(Object o){
    return this.list.contains(o);
  }

  /*
   * @Override
   */
  public boolean containsAll(Collection<?> c){
    return this.list.containsAll(c);
  }

  /*
   * @Override
   */
  public boolean isEmpty(){
    return this.list.isEmpty();
  }

  /*
   * @Override
   */
  public <T> T[] toArray(T[] a){
    return this.list.toArray(a);
  }

  /*
   * @Override
   */
  public Object[] toArray(){
    return this.list.toArray();
  }

  /*
   * @Override
   */
  public E set(int i, E e){
    return this.list.set(i, e);
  }

  /*
   * @Override
   */
  public E get(int i){
    return this.list.get(i);
  }

  /*
   * @Override
   */
  public int size(){
    return this.list.size();
  }

  /*
   * @Override
   */
  public UnmodifiableArrayList<E> subList(int i, int j){
    return new UnmodifiableArrayList<E>(this.list.subList(i, j)); // suboptimal?
  }

  /*
   * @Override
   */
  public E remove(int i){
    return this.list.remove(i);
  }

  /*
   * @Override
   */
  public boolean retainAll(Collection<?> c){
    return this.list.retainAll(c);
  }

  /*
   * @Override
   */
  public boolean remove(Object o){
    return this.list.remove(o);
  }

  /*
   * @Override
   */
  public boolean removeAll(Collection<?> c){
    return this.list.removeAll(c);
  }

  /*
   * @Override
   */
  public void clear(){
    this.list.clear();
  }

  /*
   * @Override
   */
  public int indexOf(Object o){
    return this.list.indexOf(o);
  }

  /*
   * @Override
   */
  public int lastIndexOf(Object o){
    return this.list.lastIndexOf(o);
  }

  /*
   * @Override
   */
  public Iterator<E> iterator(){
    return this.list.iterator();
  }

  /*
   * @Override
   */
  public ListIterator<E> listIterator(){
    return this.list.listIterator();
  }

  /*
   * @Override
   */
  public ListIterator<E> listIterator(int i){
    return this.list.listIterator(i);
  }

  /*
   * @Override
   */
  public boolean equals(Object o){
    return this.list.equals(o);
  }

  /*
   * @Override
   */
  public String toString(){
    return this.list.toString();
  }
}
