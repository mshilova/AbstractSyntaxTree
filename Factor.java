import java.util.*;

public class Factor extends ASTNode  {

/////////////////////////constructors/////////////////////

 /**
  * A private constructor that is calling the ASTNode super class
  * constructor.
  */
  private Factor(ASTNode n)  {
    super(n);
  }


//////////////////////////methods/////////////////////////////

 /**
  * A factory method that parses a String accoring to the BNF definition for 
  * <factor>. The BNF definition is:
  * <factor> := <const> | <ident> | "(" <expr> ")". Returns a Factor object
  * that is the root of an abstract syntax (sub)tree resulting from the 
  * parse, or null if the String cannot be parsed as a <factor>.
  *
  * @param the String to be parsed
  * @return Factor object or null if not parsed
  */
  public static Factor parse(String s)  {

    
    if(s == null)
      return null;

    s = s.trim(); // remove white spaces
   
    if(s.length() == 0)
      return null;

    // first parse as Const
    ASTNode result = Const.parse(s);
    if(result != null)  {
      return new Factor(result);
    }
    else {
      // if not const parse as Ident
      result = Ident.parse(s);
      if(result != null)  {
        return new Factor(result);
      }
      else  {
        // if not Ident parse as "(" <expr> ")"
        if(s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')')  {
          // parse everything inside () as expr
          result = Expr.parse(s.substring(1, s.length() - 1));
          if(result != null)  {
            return new Factor(result);
          }
        }
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
      return this.getChild(0).eval(symtab);
    else
      throw new IllegalStateException("Node with invalid number of children");
  }
}
