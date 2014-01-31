import java.util.*;

public class Expr extends ASTNode  {



//////////////////////constructors////////////////////////

 /**
  * Private constructor, calling ASTNode superclass.
  */
  private Expr(ASTNode n)  {
    super(n);
  }


/////////////////////////methods/////////////////////////////

  /**
   * A factory method that parses a String accoring to the BNF definition 
   * for <expr>. The BNF definition is: <expr> := <assmt> | <oprn>. Returns
   * an Expr object that is the root of an abstract syntax (sub)tree
   * resulting from the parse, or null if the String cannot be parsed as 
   * an <expr>.
   *
   * @param the String to be parsed
   * @return Expr object or null if not parsed
   */
  public static Expr parse(String s)  {

    if(s == null)
      return null;

    s = s.trim(); // get rid of white spaces

    if(s.length() == 0)
      return null;
    // parse as oprn
    ASTNode result = Oprn.parse(s);
    if(result != null) {
      return new Expr(result);
    }
    else  {
      // parse as assmt
      result = Assmt.parse(s);
      if (result != null)  {
        return new Expr(result);
      }
    }
    return null;
  }

 /**
  * Evaluates the abstract syntax (sub)tree that is rooted at this ASTNode in 
  * the context of the given symbol table, and return the result. Throws 
  * IllegalStateException if the abstract syntax (sub)tree
  * cannot be evaluated.
  *
  * @return the double value that is the result of evaluating the abstract 
  * syntax (sub)tree rooted at this ASTNode.
  * @param symtab - A map from variable identifiers to values, to use as a 
  * symbol table in the evaluation.
  * @see java.lang.Exception
  */ 
  public double eval(Map<String, Double> symtab)  {
    if(this.arity() == 1) 
      return getChild(0).eval(symtab);
    else
      throw new IllegalStateException("Node with invalid number of children");
  }
}
