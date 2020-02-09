package analogy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import analogy.sequence.Sequence;
import analogy.sequence.SequenceEquation;
import analogy.sequence.SequenceProportion;
import analogy.sequence.SequenceSolution;
import analogy.set.ImmutableSet;
import analogy.set.SetEquation;
import analogy.tuple.Tuple;
import analogy.tuple.TupleEquation;
import analogy.tuple.TupleSolution;
import util.CharacterSequence;

public class Main{
  private static <V> Tuple<V> singletonTuple(V item){
    HashMap<Object, V> m = new HashMap<Object, V>();
    m.put(0, item);
    return new Tuple<V>(m);
  }
  
  public static void main(String[] args) throws NoSolutionException {
    String equation;
    
    Sequence<Character> A = new CharacterSequence("baa");
    Sequence<Character> B = new CharacterSequence("aba");
    Sequence<Character> C = new CharacterSequence("aab");
    Sequence<Character> D = new CharacterSequence("aab");
    SequenceEquation<Character> e = new SequenceEquation<Character>(A, B, C);
    
    assert(new SequenceProportion<Character>(A, B, C, D).isValid());

    equation = A + " : " + B + " :: " + C + " : ";
    boolean degreePrintedOut = false;
    for (SequenceSolution<Character> s:e.nBestDegreeSolution(1)) {
      if (!degreePrintedOut) {
        System.out.println("Degree " + s.getDegree() + ":");
        degreePrintedOut = true;
      }
      System.out.println(equation + s.getContent());
//      System.out.println(Factorizations.toString(s.getFactorization()));
    }
    System.out.println();
    
    Tuple<CharacterSequence> tA = singletonTuple(new CharacterSequence("AKCKE"));
    Tuple<CharacterSequence> tB = singletonTuple(new CharacterSequence("BKCKF"));
    Tuple<CharacterSequence> tC = singletonTuple(new CharacterSequence("AKDKE"));
    Tuple<CharacterSequence> tD = singletonTuple(new CharacterSequence("BKDKF"));
    assert(new DefaultProportion<Object>(tA, tB, tC, tD).isValid());
    
    equation = tA + " : " + tB + " :: " + tC + " : ";
    for (TupleSolution<CharacterSequence> s: new TupleEquation<CharacterSequence>(tA, tB, tC)) {
      System.out.println(equation + s.getContent());
    }
    System.out.println();
    
    ImmutableSet<Integer> sA = new ImmutableSet<Integer>(new HashSet<Integer>(Arrays.asList(1, 2)));
    ImmutableSet<Integer> sB = new ImmutableSet<Integer>(new HashSet<Integer>(Arrays.asList(3, 1, 5)));
    ImmutableSet<Integer> sC = new ImmutableSet<Integer>(new HashSet<Integer>(Arrays.asList(2, 0, 4)));
    ImmutableSet<Integer> sD = new ImmutableSet<Integer>(new HashSet<Integer>(Arrays.asList(4, 0, 3, 5)));
    
    assert(new DefaultProportion<Object>(sA, sB, sC, sD).isValid());
    
    equation = sA + " : " + sB + " :: " + sC + " : ";
    for (Solution<ImmutableSet<Integer>> s: new SetEquation<Integer>(sA, sB, sC))
      System.out.println(equation + s.getContent());
  }
}
