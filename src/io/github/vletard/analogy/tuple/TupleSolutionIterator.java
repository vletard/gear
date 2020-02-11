package io.github.vletard.analogy.tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import io.github.vletard.analogy.DefaultEquation;
import io.github.vletard.analogy.Solution;

public class TupleSolutionIterator<T> implements Iterator<TupleSolution<T>> {

  /**
   * List defining an order on the tuple equation keys.
   */
  private final ArrayList<Object> keys;

  /**
   * The list of the solution iterators of each sub equation of this tuple equation.
   */
  private final ArrayList<Iterator<Solution<T>>> iterators;

  /**
   * List of partial solution lists growing on demand.
   * The ordering is indexed on the keys ArrayList.
   */
  private final ArrayList<ArrayList<Solution<T>>> partialLists;

  /**
   * Index of the next element among the partial lists.
   */
  private final ArrayList<Integer> currentIndex;

  /**
   * The degree of solutions that are currently returned by this iterator.
   * Solutions of that degree must be exhausted before incrementing.
   */
  private int currentDegree;

  /**
   * Flag for whether the hasNext() method is expected to increment the currentIndex
   * before checking for a next element.
   */
  private boolean dueForIncrementation;

  public TupleSolutionIterator(Tuple<T> A, Tuple<T> B, Tuple<T> C){
    {
      HashSet<Object> keySet = new HashSet<Object>();
      keySet.addAll(A.keySet());
      keySet.addAll(B.keySet());
      keySet.addAll(C.keySet());
      this.keys = new ArrayList<Object>(keySet); // assigning an arbitrary order to the key set for indexing structures
    }

    this.iterators = new ArrayList<Iterator<Solution<T>>>();
    this.partialLists = new ArrayList<ArrayList<Solution<T>>>();
    this.currentIndex = new ArrayList<Integer>();
    for (Object k: this.keys) {
      Iterator<Solution<T>> it = new DefaultEquation<T, Solution<T>>(A.get(k), B.get(k), C.get(k)).iterator();
      this.currentIndex.add(0);
      this.iterators.add(it);
      this.partialLists.add(new ArrayList<Solution<T>>());
    }
    this.currentDegree = 0;
    this.dueForIncrementation = false;
  }

  private Boolean increment(List<Integer> degrees, int index) {
    if (index >= this.currentIndex.size())
      return false;

    LinkedList<Integer> degreeList = new LinkedList<Integer>(degrees);
    int partialListIndex = this.currentIndex.get(index);
    if (partialListIndex + 1 >= this.partialLists.get(index).size()) {
      // attempt to compute one more element to store in the partial list
      Iterator<Solution<T>> it = this.iterators.get(index);
      if (it.hasNext())
        this.partialLists.get(index).add(it.next());
    }
    boolean greaterDegreeAvailable = false;
    if (partialListIndex + 1 < this.partialLists.get(index).size()) {
      // attempt to increment the partial list index while remaining in the currentDegree limit
      degreeList.set(index, this.partialLists.get(index).get(partialListIndex).getDegree());
      int aggregate = this.aggregateDegree(degreeList);
      if (aggregate <= this.currentDegree) {
        this.currentIndex.set(index, partialListIndex + 1);
        return true;
      }
      else
        greaterDegreeAvailable = true;
    }

    // if the current index was not incremented yet, reset it and increment a subsequent index
    degreeList.set(index, this.partialLists.get(index).get(0).getDegree());
    if (this.increment(degrees, index+1)){
      this.currentIndex.set(index, 0); // only if the call succeeded
      return true;
    }
    else if (greaterDegreeAvailable)
      return null;
    else
      return false;
  }

  private boolean increment() {
    if (this.currentIndex.size() > 0) {
      Boolean result = false;
      do {  // global increment
        LinkedList<Integer> degreeList = new LinkedList<Integer>();
        for (int i = 0; i < this.keys.size(); i++) {
          Solution<T> s = this.partialLists.get(i).get(this.currentIndex.get(i));
          degreeList.add(s.getDegree());
        }

        do { // increment until the resulting degree is greater or equal to the current degree (or until no more elements can be found)
          result = this.increment(Collections.unmodifiableList(degreeList), 0);
          degreeList = new LinkedList<Integer>();
          for (int i = 0; i < this.keys.size(); i++) {
            Solution<T> s = this.partialLists.get(i).get(this.currentIndex.get(i));
            degreeList.add(s.getDegree());
          }
        } while (result == Boolean.TRUE && this.aggregateDegree(degreeList) < this.currentDegree);
        if (result == Boolean.TRUE)
          return true;
        else if (result == Boolean.FALSE)
          return false;
        else { // result is null, meaning that a greater degree is available
          for (int i = 0; i < this.keys.size(); i++)
            this.currentIndex.set(i, 0); // reinitializing indices
          this.currentDegree ++; // increasing searched degree before looping
        }
      } while (result == null);
      return true;
    }
    else
      return false;
  }

  @Override
  public boolean hasNext() {
    if (this.dueForIncrementation)
      return this.increment();
    else
      for (int i = 0; i < this.keys.size(); i++) {
        ArrayList<Solution<T>> partialList = this.partialLists.get(i);
        if (partialList.size() <= this.currentIndex.get(i)) {
          // this state is supposed to only occur during the first call (delayed initialization)
          assert(partialList.size() == i && i == 0);
          if (this.iterators.get(i).hasNext())
            partialList.add(this.iterators.get(i).next());
          else
            return false;
        }
      }
    return true;
  }

  /**
   * Returns an aggregation of the list of degrees of the subordinate solutions.
   * For this tuple structure, degrees are aggregated by maximum.
   * @return the maximum of the degree list
   */
  private int aggregateDegree(List<Integer> degreeList) {
    int aggregate = 0;
    for (int d: degreeList)
      aggregate = Math.max(aggregate, d);
    return aggregate;
  }

  @Override
  public TupleSolution<T> next() {
    if (this.hasNext()) {
      HashMap<Object, Solution<T>> m = new HashMap<Object, Solution<T>>();
      LinkedList<Integer> degreeList = new LinkedList<Integer>();
      for (int i = 0; i < this.keys.size(); i++) {
        Object k = this.keys.get(i);
        Solution<T> s = this.partialLists.get(i).get(this.currentIndex.get(i));
        m.put(k, s);
        degreeList.add(s.getDegree());
      }
      this.dueForIncrementation = true;
      return new TupleSolution<T>(m, aggregateDegree(degreeList));
    }
    else
      throw new NoSuchElementException();
  }
}