import java.util.*;

public class Term extends ASTNode  {

  /////////////////////fields//////////////////////////

  private char op;  // reference to the operation performed

  ///////////////////////////constructors//////////////////

 /**
  * A private constructor that is calling the ASTNode super class
  * constructor.
  */
  private Term(Factor onlyChild)  {
    super(onlyChild); } 

 /**
  * Private constructor calling the ASTNode super class constructor,
  * initializing operation to the param.
  */
  private Term(Term n1, Factor n2, char oper)  {
    super(n1, n2);
    this.op = oper;
  }
  
 //////////////////////methods//////////////////////////

 /**
  * A factory method that parses a String accoring to the BNF definition for
  *  <term>. The BNF definition is:
  * <term> := <factor> | <term> "*" <factor> | <term> "/" <factor>. Return a 
  * Term object that is the root of an abstract syntax (sub)tree resulting 
  * from the parse, or null if the String cannot be parsed as a <term>.
  *
  * @param String to be parsed
  * @return Term object or null if not parsed
  */
  public static Term parse(String s)  { 
   
    if(s == null) 
      return null;

    s = s.trim(); // remove white spaces
 
    if(s.length() == 0)
      return null;

    Factor result = Factor.parse(s);
   
    if(result != null)  {
      return new Term(result);
    }
    else  {
      // find '*' 
      Term leftChild;
      Factor rightChild;

      for(int i = 1 ; i < s.length() - 1; i++)  {
        if(s.charAt(i) == '*')  {
           // parse everything before '*' as term
          // parse everything after '*' as factor 
          leftChild = Term.parse(s.substring(0, i));
          rightChild = Factor.parse(s.substring(i+1));

          if(leftChild != null & rightChild != null)  {
            return new Term(leftChild, rightChild, '*');
          }
        }
       // find '/'
        else if(s.charAt(i) == '/')  {
          // parse everything before '/' as term
          // parse everything after '/' as factor  
          leftChild = Term.parse(s.substring(0, i));
          rightChild = Factor.parse(s.substring(i+1));

          if(leftChild != null & rightChild != null)  {
            return new Term(leftChild, rightChild, '/');
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
      return getChild(0).eval(symtab);
      
    if(this.arity() == 2)  {

      if(this.op == '*')  
       return (getChild(0).eval(symtab)*getChild(1).eval(symtab));
      if(this.op == '/')  
       return (getChild(0).eval(symtab)/getChild(1).eval(symtab));

    }
    throw new IllegalStateException("Node with invalid number of children");
  }
}
