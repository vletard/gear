package analogy;

import java.util.ArrayList;
import java.util.ListIterator;

public class Main{
  public static void main(String[] args){
    CharacterSequence A = new CharacterSequence("AX");
    CharacterSequence B = new CharacterSequence("AY");
    CharacterSequence C = new CharacterSequence("BX");
    CharacterSequence D = new CharacterSequence("BY");
    Proportion<Character> p = new Proportion<Character>(A, B, C, D);

    System.out.println(p);
    System.out.println(p.isValid());
    System.out.println(p);
  }
}
