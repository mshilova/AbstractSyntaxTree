import java.util.*;
/**
 * Filename: Const.java
 *
 * This class represents the <const> nonterminal symbol in an abstract 
 * syntax tree. This class does not have a public constror; the public 
 * interface for creating an instance of the class is the static 
 * parse(String s) factory method.
 *
 * @date 06/05/2013
 */
public class Const extends ASTNode  {

  ///////////////////////////fields/////////////

  private Double myVal; // reference to the value of the ident
  
  ///////////////////constructor//////////////////////////

  /**
   * Private constructor, takes a double, value is initialized to the param
   * @param
   */ 
  private Const(Double r)  {
    this.myVal = r;
  }

 /**
  * A factory method that parses a String according to the def for <const>
  *  The definition is: Any String that can be parsed by 
  * java.lang.Double.parseDouble(s)without throwing a NumberFormatException. 
  * Returns a Const object that is the root of an abstract syntax subtree 
  * resulting from the parse, or null if the String cannot be parsed as a 
  * <const>.
  *
  * @return Const object or null is not parsed
  * @param a String to be parsed
  */ 
  public static Const parse(String s)  {
    if(s == null)
      return null;

    s = s.trim();  // remove white spaces on both sides

    if(s.length() == 0)
      return null;
   
    // if String can be parsed by Double.parseDouble(s) without throwing 
    // NumberFormatException then it is a constant
 
    try  {
      Double result = Double.parseDouble(s);
      return new Const(result);
    }
    catch(NumberFormatException e)  {
      return null;
    }
  }
 
 /**
  * Ecaluates the abstract syntax (sub)tree that is rooted at this ASTNode in 
  * the context of the given symbol table, and return the result. Throws 
  * IllegalStateException if the abstract syntax (sub)tree can't be evaluated
  *
  * @return the double value that is the result of evaluating the abstract 
  * syntax (sub)tree rooted at this ASTNode.
  * @param symtab - A map from variable identifiers to values, to use as a 
  * symbol table in the evaluation.
  * @see java.lang.Exception
  */ 
  public double eval(Map<String, Double> symtab)  {
    if(this.arity() == 0)  
      return myVal;
    else
      throw new IllegalStateException("Cannot be evaluated");
  }

}
