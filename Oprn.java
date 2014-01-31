import java.util.*;

public class Oprn extends ASTNode  {


  ////////////////////////fields//////////////////////

  private char op; // reference to the type of operation performed


 ///////////////////constructors//////////////////////
  
 /**
  * Private constructor calling the ASTNode super class constructor,
  * initializing operation to the param.
  */
  private Oprn(Oprn n1, Term n2, char oper)  {
    super(n1, n2);
    this.op = oper;
  } 
  private Oprn(Term onlyChild)  {
    super(onlyChild);
  }

 /**
  * A factory method that parses a String accoring to the BNF definition for
  * <oprn>. The BNF definition is:
  * <oprn> := <term> | <oprn> "+" <term> | <oprn> "-" <term>. Returns an Oprn
  * object that is the root of an abstract syntax (sub)tree resulting from the
  * parse, or null if the String cannot be parsed as an <oprn>.
  *
  * @param the String to be parsed
  * @return Oprn object or null if not parsed
  */
  public static Oprn parse(String s)  {

    if(s == null)
       return null;

    s = s.trim(); // remove white spaces

    if(s.length() == 0)
      return null;

    // first parse as term
    Term result = Term.parse(s);

    if(result != null)  {
      return new Oprn(result);
    }
    else  {
      Oprn leftChild;
      Term rightChild;
      //  loop to find '+'  
      for(int i = 1; i < s.length() - 1; i++)  {
        if(s.charAt(i) == '+')  {
          // parse everything before '+' as oprn
          // parse everything after '+' as term        
          leftChild = Oprn.parse(s.substring(0, i));
          rightChild = Term.parse(s.substring(i+1));

          if(leftChild != null && rightChild != null)  {
            return new Oprn(leftChild, rightChild, '+');
          }
        }
        // loop to find '-'
        else if(s.charAt(i) == '-')  {
          // parse everything before '-' as oprn
          // parse everything after '-' as term 
          leftChild = Oprn.parse(s.substring(0, i));
          rightChild = Term.parse(s.substring(i+1));

            if(leftChild != null & rightChild != null)  {
              return new Oprn(leftChild, rightChild, '-');
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

    if(this.arity() == 2)  {
      if(this.op == '+')  
        return (getChild(0).eval(symtab) + getChild(1).eval(symtab));
      if(this.op == '-')
        return (getChild(0).eval(symtab) - getChild(1).eval(symtab));
    }
    throw new IllegalStateException("Node with invalid number of children");
  }
}
