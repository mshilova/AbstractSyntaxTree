import java.util.*;

/**
 * Filename: Assmt.java
 *
 * This class represents the <assmt> nonterminal symbol in an abstract 
 * syntax tree. This class does not have a public constror; the public 
 * interface for creating an instance of the class is the static 
 * parse(String s) factory method.
 *
 * @date 06/05/2013
 */ 
public class Assmt extends ASTNode   {

  /////////////////////////////fields///////////////////

  private String name;  // reference to the identifier name

 ////////////////////////constructor/////////////////////
 
 /**
  * Private constructor that takes two nodes: Ident and Expr,
  * calling the ASTNode super class to initialize.
  * 
  * @param identifier and expression nodes.
  */
  private Assmt(Ident n1, Expr n2)  {
    super(n1, n2);
  }
 

  //////////////////////////////methods/////////////////////////

 /**
  * A factory method that parses a String according to the BNF
  * definition for <assmt> := <ident> "=" <expr>, returns an Assmt
  * object that is the root of an abstract syntax (sub)tree resulting 
  * from the parse, or null if the String cannot be parsed as <assmt>
  *
  * @param a String to be parsed
  * @return a node of type Assmt or null if cannot be parsed as Assmt
  */ 
  public static Assmt parse(String s)  {
    
    if(s == null)  // String cannot be null
      return null;
   
    s = s.trim(); // remove white spaces
    
    if(s.length() == 0)  // empty string cannot be parsed
      return null;


    Ident leftChild;  // reference to the left child (Ident)
    Expr rightChild;  // reference to the right child (Expr)

    // loop through until encounter "="
    for(int i = 1; i < s.length() - 1; i++)  {

      if(s.charAt(i) == '=')  {
         // parse everything to the left of '=' as Ident
         // parse everything to the right of '=' as Expr        
         leftChild = Ident.parse(s.substring(0, i));
         rightChild = Expr.parse(s.substring(i+1));
         // if both parsed successfully return Assmt node
         if(leftChild != null && rightChild != null)  {
           return new Assmt(leftChild, rightChild);
         }
      }
    }
    return null;
  }

 /**
  * Evaluating abstract syntax (sub)tree that is rooted at this ASTNode in
  * the context of the given symbol table (a map from variable identifiers 
  * to values, to use as a symbol table in the evaluation) and return the 
  * result. Throws IllegalStateException if the abstract syntax (sub)tree
  * cannot be evaluated.
  *
  * @param a symbol table
  * @return the double value that is the result of evaluation the abstract
  * syntax (sub)tree rooted at this ASTNode.
  * @see java.lang.Exception
  */ 
  public double eval(Map<String, Double> symtab)  {

    if(this.arity() == 2)  {
      // the value of the Assmt is the value of the right child
      // i.e. everything after '='
      double result = getChild(1).eval(symtab);
      // get the name of the identifier and put it in symbol table
      name = getChild(0).toString();
      symtab.put(name, result); 
      return result;
    }
    else
      throw new IllegalStateException("Cannot be evaluated!");
  }
}
