package analogy;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import analogy.matrix.Factor;

public class Main{
  public static void main(String[] args){
    CharacterSequence A = new CharacterSequence("baa");
    CharacterSequence B = new CharacterSequence("aba");
    CharacterSequence C = new CharacterSequence("aab");
    CharacterSequence D = new CharacterSequence("dabc");
    Proportion<Character> p = new Proportion<Character>(A, B, C, D);
    Equation<Character> e = new Equation<Character>(A, B, C);

    System.out.println(p);
    System.out.println(p.isValid());
    System.out.println(p);

    e.solveBest();
    Iterator<Entry<Integer, Equation<Character>.SolutionMap<Character>>> it = e.getSolutions().entrySet().iterator();
    while (it.hasNext()) {
      Entry<Integer, Equation<Character>.SolutionMap<Character>> degree = it.next();
      Iterator<Entry<List<Character>, Set<List<Factor<Character>>>>> it2 = degree.getValue().entrySet().iterator();
      while (it2.hasNext()) {
        Entry<List<Character>, Set<List<Factor<Character>>>> solution = it2.next();
        System.out.println(solution.getKey() + " (degree " + degree.getKey() + ") has " + solution.getValue().size() + " distinct alignments.");
      }
    }
  }
}
