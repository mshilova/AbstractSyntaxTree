import java.util.*;
/**
 * Filename: Ident.java
 *
 * This class represents the <ident> nonterminal symbol in an abstract 
 * syntax tree. This class does not have a public constror; the public 
 * interface for creating an instance of the class is the static 
 * parse(String s) factory method.
 *
 * @date 06/05/2013
 */
public class Ident extends ASTNode  {

  /////////////////////fields////////////////////
  private String name;

  /////////////////////constructor/////////////////////

 /**
  * Private constructor, initializing name,
  * calling ASTNode super class. This node has no children.
  */
  private Ident(String m)  {
    super();
    this.name = m;
  }

  ////////////////////////////methods//////////////////////

 /**
  * A factory method that parses a String accoring to the definition for 
  * <ident>. The definition is: Any String that starts with a 
  * JavaIdentifierStart character, followed by 0 or more JavaIdentifierPart 
  * characters. Returns a Ident object that is the root of an abstract syntax
  * subtree resulting from the parse, or null if the String cannot be parsed 
  * as a <ident>.
  *
  * @param a String to be parsed
  * @return Ident object or null if not parsed
  */
  public static Ident parse(String s)  {
    
    boolean legalIdent = false;

    if(s == null) 
      return null;

    // remove white spaces from both sides
    s = s.trim();

    if(s.length() != 0)  {
      // convert the string to array of chars
      // check if starts with legal charecter
      if(Character.isJavaIdentifierStart(s.charAt(0)))  {
        legalIdent = true;
      }
      // check the rest of the charecters
      if(legalIdent == true && s.length() > 1)  {
        for(int i = 1; i < s.length(); i++)  {
          if(!Character.isJavaIdentifierPart(s.charAt(i)))  {
            legalIdent = false;
            break;
          }
        }
      }  
    }
    if(!legalIdent)  {
      return null;
    }
    else  {
      return new Ident(s); 
    }
  }

 /**
  * Evaluate the abstract syntax (sub)tree that is rooted at this ASTNode
  * in the context of the given symbol table, and return the result. Throws 
  * RuntimeException if variable has not been assigned value,
  * throws IllegalStateException if cannot be evaluated.
  * 
  * @param symboltable - A map from variable identifiers to values, to use as a
  * symbol table in the evaluation. 
  * @return the double value that is the result of evaluating the abstract 
  * syntax (sub)tree rooted at this ASTNode.
  */
  public double eval(Map<String, Double> symtab)  {

    if(this.arity() != 0)
      throw new IllegalStateException("Cannot be evaluated!");
    if(symtab.containsKey(this.name))  {
      return symtab.get(name);
    } else {
      throw new RuntimeException( "UNINITIALIZED VARIABLE " + this.name);
    }
  }

 /**
  * Overriden toString() method, returns the name of the string
  * @return name of the string parsed
  */
  public String toString()  {
    return this.name;    
  }
}
