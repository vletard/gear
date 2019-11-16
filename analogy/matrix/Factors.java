package analogy.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Factors {

  public static <E> List<Factor<E>> extendListB(List<Factor<E>> factors, boolean crossed, E item) {
    LinkedList<Factor<E>> newList = new LinkedList<Factor<E>>(factors);
    if (newList.isEmpty() || newList.getLast().isCrossed() != crossed)
      newList.add(new Factor<E>(crossed, Collections.singletonList(item), Collections.emptyList()));
    else
      newList.add(new Factor<E>(newList.removeLast(), Collections.singletonList(item), Collections.emptyList()));
    return Collections.unmodifiableList(newList);
  }

  public static <E> List<Factor<E>> extendListC(List<Factor<E>> factors, boolean crossed, E item) {
    LinkedList<Factor<E>> newList = new LinkedList<Factor<E>>(factors);
    if (newList.isEmpty() || newList.getLast().isCrossed() != crossed)
      newList.add(new Factor<E>(crossed, Collections.emptyList(), Collections.singletonList(item)));
    else
      newList.add(new Factor<E>(newList.removeLast(), Collections.emptyList(), Collections.singletonList(item)));
    return Collections.unmodifiableList(newList);
  }
  
  public static <E> List<Factor<E>> newList(){
    return Collections.emptyList();
  }
  
  private static <E> String concatenateList(List<E> l) {
    String str = "";
    Iterator<E> it = l.iterator();
    while (it.hasNext())
      str += it.next().toString();
    return str;
  }
  
  public static <E> List<E> extractSolution(List<Factor<E>> factorList){
    LinkedList<E> solution = new LinkedList<E>();
    Iterator<Factor<E>> it = factorList.iterator();
    while (it.hasNext()) {
      Factor<E> f = it.next();
      if (f.isCrossed())
        solution.addAll(f.getB());
      else
        solution.addAll(f.getC());
    }
    return solution;
  }

  public static <E> String toString(List<Factor<E>> factorList) {
    ArrayList<Integer> maxSizes = new ArrayList<Integer>();
    for (int i=0; i<factorList.size(); i++) {
      Factor<E> f = factorList.get(i);
      maxSizes.add(Math.max(concatenateList(f.getB()).length(), concatenateList(f.getC()).length()));
    }
    
    String str = "";
    for (int i=0; i<4; i++) {
      str += i + " ";
      for (int j=0; j<factorList.size(); j++) {
        Factor<E> f = factorList.get(j);
        int size = maxSizes.get(j);
        String content;
        if (i==1 || (i==0 && !f.isCrossed()) || (i==3 && f.isCrossed()))
          content = concatenateList(f.getB());
        else
          content = concatenateList(f.getC());
        str += String.format("%" + size + "s", content);
        if (j<factorList.size()-1)
          str += "|";
      }
      str += "\n";
    }
    return str + "\n";
  }
}
