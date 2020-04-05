package io.github.vletard.analogy.util;

import java.util.ArrayList;
import java.util.List;

import io.github.vletard.analogy.sequence.Sequence;

public class CharacterSequence extends Sequence<Character>{
  private static final long serialVersionUID = 5379880476535946361L;

  public CharacterSequence(String s){
    super(CharacterSequence.listOfString(s));
  }

  public CharacterSequence(Iterable<Character> l){
    super(l);
  }

  public static List<Character> listOfString(String s){
    List<Character> l = new ArrayList<Character>();
    for (int i = 0; i < s.length(); i++){
      l.add(s.charAt(i));
    }
    return l;
  }

  @Override
  public String toString(){
    String representation = "";
    for (Character c:this.toList())
      representation = representation + c;
    return representation;
  }
}
