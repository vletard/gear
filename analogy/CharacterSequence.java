package analogy;

import util.UnmodifiableArrayList;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Character;

public class CharacterSequence extends UnmodifiableArrayList<Character>{
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

  /*
   * @Override
   */
  public CharacterSequence subList(int i, int j){
    return new CharacterSequence(super.subList(i, j));
  }

  /*
   * @Override
   */
  public String toString(){
    String representation = "";
    Iterator<Character> it = this.iterator();
    while (it.hasNext())
      representation = representation + it.next();
    return representation;
  }
}
