package analogy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import analogy.sequence.Factor;
import analogy.sequence.SequenceEquation;
import analogy.sequence.SequenceProportion;
import analogy.sequence.SequenceEquation.NoSolutionException;
import util.CharacterSequence;
import util.Sequence;

public class Main{
  public static List<Character> stringToCharList(String s){
    List<Character> l = new ArrayList<Character>();
    for (int i = 0; i < s.length(); i++){
      l.add(s.charAt(i));
    }
    return l;
  }
  
  public static void main(String[] args) throws NoSolutionException{
    Sequence<Character> A = new CharacterSequence("baa");
    Sequence<Character> B = new CharacterSequence("aba");
    Sequence<Character> C = new CharacterSequence("aab");
    Sequence<Character> D = new CharacterSequence("aab");
    Proportion<Sequence<Character>> p = new SequenceProportion<Character>(A, B, C, D);
    SequenceEquation<Character> e = new SequenceEquation<Character>(A, B, C);
    
    System.out.println(p);
    System.out.println(p.isValid());
    System.out.println(p);

    e.solveBest();
    Iterator<Entry<Sequence<Character>, Set<List<Factor<Character>>>>> it2 = e.getBestSolutions().entrySet().iterator();
    while (it2.hasNext()) {
      Entry<Sequence<Character>, Set<List<Factor<Character>>>> solution = it2.next();
      System.out.println(solution.getKey() + " (degree " + e.getBestDegree() + ") has " + solution.getValue().size() + " distinct alignments.");
    }
  }
}
