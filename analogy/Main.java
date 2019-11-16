package analogy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import analogy.matrix.Factor;
import analogy.matrix.Factors;

public class Main{
  public static void main(String[] args){
    CharacterSequence A = new CharacterSequence("AXa");
    CharacterSequence B = new CharacterSequence("AYa");
    CharacterSequence C = new CharacterSequence("bXB");
    CharacterSequence D = new CharacterSequence("BYb");
    Proportion<Character> p = new Proportion<Character>(A, B, C, D);
    Equation<Character> e = new Equation<Character>(A, B, C);

    System.out.println(p);
    System.out.println(p.isValid());
    System.out.println(p);

    e.solveBest();
    Iterator<List<Factor<Character>>> it = e.getBestSolutions().iterator();
    HashSet<List<Character>> solutions = new HashSet<List<Character>>();
    while (it.hasNext())
      solutions.add(Factors.extractSolution(it.next()));
    System.out.println(solutions);
  }
}
