package io.github.vletard.analogy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import io.github.vletard.analogy.sequence.Sequence;
import io.github.vletard.analogy.sequence.SequenceEquation;
import io.github.vletard.analogy.sequence.SequenceProportion;
import io.github.vletard.analogy.set.ImmutableSet;
import io.github.vletard.analogy.set.SimpleSetEquation;
import io.github.vletard.analogy.tuple.SimpleTupleEquation;
import io.github.vletard.analogy.tuple.SubTupleRebuilder;
import io.github.vletard.analogy.tuple.Tuple;
import io.github.vletard.analogy.tuple.TupleEquation;
import io.github.vletard.analogy.util.CharacterSequence;
import io.github.vletard.analogy.util.InvalidParameterException;

public class Test {
  
  public static void main(String[] args) throws NoSolutionException, InvalidParameterException {
    String equation;
    
    final SubtypeRebuilder<Sequence<Character>, CharacterSequence> charSeqRebuilder = new SubtypeRebuilder<Sequence<Character>, CharacterSequence>() {
      @Override
      public CharacterSequence rebuild(Sequence<Character> object) {
        return new CharacterSequence(object);
      }
    };
    
    CharacterSequence A = new CharacterSequence("baa");
    CharacterSequence B = new CharacterSequence("aba");
    CharacterSequence C = new CharacterSequence("aab");
    CharacterSequence D = new CharacterSequence("aab");
    SequenceEquation<Character, CharacterSequence> e = new SequenceEquation<Character, CharacterSequence>(A, B, C, charSeqRebuilder);
    
    assert(new SequenceProportion<Character>(A, B, C, D).isValid());

    equation = A + " : " + B + " :: " + C + " : ";
    boolean degreePrintedOut = false;
    for (Solution<CharacterSequence> s:e.nBestDegreeSolutions(1)) {
      if (!degreePrintedOut) {
        System.out.println("Degree " + s.getDegree() + ":");
        degreePrintedOut = true;
      }
      System.out.println(equation + s.getContent());
      System.out.println(s.getStraightRelation());
      System.out.println(s.getCrossedRelation());
    }
    System.out.println();
    
    HashMap<String, Sequence<Character>> regularMap, freeMap;
    regularMap = new HashMap<String, Sequence<Character>>();
    freeMap = new HashMap<String, Sequence<Character>>();
    regularMap.put("regular", new CharacterSequence("AKCKE"));
    freeMap.put("free", new CharacterSequence("AKCKE"));
    Tuple<Sequence<Character>> tA = new Tuple<Sequence<Character>>(regularMap, freeMap);
    regularMap = new HashMap<String, Sequence<Character>>();
    freeMap = new HashMap<String, Sequence<Character>>();
    regularMap.put("regular", new CharacterSequence("BKCKF"));
    freeMap.put("free", new CharacterSequence("BKCKF"));
    Tuple<Sequence<Character>> tB = new Tuple<Sequence<Character>>(regularMap, freeMap);
    regularMap = new HashMap<String, Sequence<Character>>();
    freeMap = new HashMap<String, Sequence<Character>>();
    regularMap.put("regular", new CharacterSequence("AKDKE"));
    freeMap.put("free", new CharacterSequence("AKDKE"));
    Tuple<Sequence<Character>> tC = new Tuple<Sequence<Character>>(regularMap, freeMap);
    regularMap = new HashMap<String, Sequence<Character>>();
    freeMap = new HashMap<String, Sequence<Character>>();
    regularMap.put("regular", new CharacterSequence("BKDKF"));
    freeMap.put("free", new CharacterSequence("BKDKF"));
    Tuple<Sequence<Character>> tD = new Tuple<Sequence<Character>>(regularMap, freeMap);
    assert(new DefaultProportion<Object>(tA, tB, tC, tD).isValid());
    
    equation = tA + " : " + tB + " :: " + tC + " : ";
    SubTupleRebuilder<Sequence<Character>, Tuple<Sequence<Character>>> tupleRebuilder = new SubTupleRebuilder<Sequence<Character>, Tuple<Sequence<Character>>>(charSeqRebuilder) {
      
      @Override
      public Tuple<Sequence<Character>> rebuild(Tuple<Sequence<Character>> object) {
        return object;
      }
    };
    for (Solution<Tuple<Sequence<Character>>> s: new TupleEquation<Sequence<Character>, Tuple<Sequence<Character>>>(tA, tB, tC, tupleRebuilder).uniqueSolutions()) {
//      System.out.println(s.getDegree());
      System.out.println(equation + s.getContent());
      System.out.println(s.getStraightRelation());
      System.out.println(s.getCrossedRelation());
    }
    System.out.println();
    
    ImmutableSet<Integer> sA = new ImmutableSet<Integer>(new HashSet<Integer>(Arrays.asList(1, 2)));
    ImmutableSet<Integer> sB = new ImmutableSet<Integer>(new HashSet<Integer>(Arrays.asList(3, 1, 5)));
    ImmutableSet<Integer> sC = new ImmutableSet<Integer>(new HashSet<Integer>(Arrays.asList(2, 0, 4)));
    ImmutableSet<Integer> sD = new ImmutableSet<Integer>(new HashSet<Integer>(Arrays.asList(4, 0, 3, 5)));
    
    assert(new DefaultProportion<Object>(sA, sB, sC, sD).isValid());
    
    equation = sA + " : " + sB + " :: " + sC + " : ";
    for (Solution<ImmutableSet<Integer>> s: new SimpleSetEquation<Integer>(sA, sB, sC))
      System.out.println(equation + s.getContent());
  }
}
