package util;

import java.util.ArrayList;
import java.util.List;

import analogy.sequence.Sequence;

public class CharacterSequence extends Sequence<Character>{
  public CharacterSequence(String s){
    super(CharacterSequence.listOfString(s));
  }

  public CharacterSequence(List<Character> l){
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
