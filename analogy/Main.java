package analogy;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import analogy.Equation.NoSolutionException;
import analogy.Equation.SolutionMap;
import analogy.matrix.Factor;

public class Main{
  public static void main(String[] args) throws NoSolutionException{
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
    Iterator<Entry<List<Character>, Set<List<Factor<Character>>>>> it2 = e.getBestSolutions().entrySet().iterator();
    while (it2.hasNext()) {
      Entry<List<Character>, Set<List<Factor<Character>>>> solution = it2.next();
      System.out.println(solution.getKey() + " (degree " + e.getBestDegree() + ") has " + solution.getValue().size() + " distinct alignments.");
    }
  }
}
