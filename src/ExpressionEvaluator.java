//On my honor:
//
//- I have not used source code obtained from another student,
//or any other unauthorized source, either modified or
//unmodified.
//
//- All source code and documentation used in my program is
//either my original work, or was derived by me from the
//source code published in the textbook for this course.
//
//- I have not discussed coding details about this project with
//anyone other than my partner (in the case of a joint
//submission), instructor, ACM/UPE tutors or the TAs assigned
//to this course. I understand that I may discuss the concepts
//of this program with other students, and that another student
//may help me debug my program so long as neither of us writes
//anything during the discussion or modifies any computer file
//during the discussion. I have violated neither the spirit nor
//letter of this restriction.

import java.util.Stack;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
/**
 * The expressionEvaluator2 supports variables of characters only. Evaluate the
 * prefix expression as per the lecture instruction of post-fix. It also 
 * supports double values and integer values
 * 
 * @author Ahmed (AhmedAredah)
 * @version Sep 16, 2022
 */
public class ExpressionEvaluator {
    // I prefer to use functional-like programming. I dedicate each method to
    // a specific task. Even if it is not the most efficient way but it is
    // easier for me and the reviewer to understand my code and trace the error.

    /**
     * check if an expression has same number of opening and closing brackets
     * 
     * @param expr is the provided expression
     * @return true if balances, false otherwise
     */
    public static boolean isBalanced(String expr) {
        // check if empty string
        if (expr == null || expr.trim().isEmpty())
            throw new RuntimeException("Empty Expression");

        // initialize an empty stack
        Stack<Character> stack = new Stack<Character>();

        // loop on each character from beginning of string
        for (int i = 0; i < expr.length(); i++) {
            // get the character
            char currentChar = expr.charAt(i);
            // if an opening bracket, add to stack
            if (isOpeningBracket(currentChar)) {
                stack.add(currentChar);
            }
            // if a closing bracket, remove last from stack
            else if (isClosingBracket(currentChar)) {
                if (stack.isEmpty())
                    return false;
                char openningBracket;
                openningBracket = stack.pop();
                switch (currentChar) {
                case ')': {
                    if (openningBracket == '{' || openningBracket == '[')
                        return false;
                }
                    break;
                case ']': {
                    if (openningBracket == '{' || openningBracket == '(')
                        return false;
                }
                    break;
                case '}': {
                    if (openningBracket == '[' || openningBracket == '(')
                        return false;
                }
                    break;
                }
            }
        }
        // if the stack is empty it means all opening brackets have closing
        // ones, if not return false
        return stack.isEmpty();
    }
    
    /**
     * check if the provided expression is in prefix format
     * 
     * @param expr is the expression
     * @param readWords is a boolean to consider following letters as a word
     * @return a SimpleEntry(boolean, integer) if the expr in prefix format, 
     * the boolean is true and false otherwise, if the integer value = 0 it 
     * indicate a successful expr parsing
     */
    protected static SimpleEntry<Boolean,Integer> isPrefix(String expr, 
                                                        boolean readWords) {
        // we could solve it by a stack, but i used stack before so i am showing
        // here another method of implementation
        // declare and initialization
        char c;                 // holds the current character
        int i = 0;              // holds the current character index
        boolean result = true;  // holds the result of the expr check
        int dc = 0;             // count the number of digits/variables
        int oc = 0;             // count the number of operators
        for (i = expr.length()-1; i >=0 ; i--) {
            c = expr.charAt(i);
            // if character is space, continue
            if (c == ' ')
                continue;
            // if closing bracket, have a new instance
            if (isClosingBracket(c)){
                dc++;
                SimpleEntry<Boolean, Integer> res 
                            = isPrefix(expr.substring(0, i), readWords);
                if ((boolean) res.getKey() == false)
                    return new SimpleEntry<>(result, i);
                i = (int)res.getValue();
            }
            // if opening bracket (end of instance expression), return result 
            else if (isOpeningBracket(c)){
                // if operators more than 1, consecutive operators
                if ((dc > 1 && oc == 0))
                    result = false;
                return new SimpleEntry<>(result, i);
            }
            // count number of digits in this instance
            else if (Character.isDigit(c)) {
                while (Character.isDigit(c) || c == '.') {
                    if (--i >= 0)
                        c = expr.charAt(i);
                    else
                        break;
                }
                i++;    // balance the extra i--
                dc++;   // increase digits/variable count
            }
            // count number of variables in this instance
            else if (Character.isLetter(c)) {
                // check if we are parsing variables by characters or words
                if (readWords) {
                    while (Character.isLetter(c)) {
                        if (--i >= 0)
                            c = expr.charAt(i);
                        else
                            break;
                    }
                    i++;    // balance the extra i--
                }
                dc++;       // increase digits/variable count
            }
            // check if operator
            else if (new Token(c).isOperator()) {
                oc ++;       // increase operators count
                
                // if no digits found from left to right and operator must take
                // operands, result = false, true otherwise
                if (dc == 0 && (! (new Token(c).takesNoOperands())))
                    result = false;
                else if (dc > 0)
                    result = true;
                
            }
        }
        if ((dc > 1 && oc == 0))
            result = false;
        // return result value
        return new SimpleEntry<>(result,i);
    }

    /**
     * check if the provided character is an opening bracket
     * 
     * @param ch is the character
     * @return true if opening bracket, false otherwise
     */
    private static boolean isOpeningBracket(Character ch) {
        return (ch == '(' || ch == '[' || ch == '{');
    }

    /**
     * check if the provided character is a closing bracket
     * 
     * @param ch is the character
     * @return true if closing bracket, false otherwise
     */
    private static boolean isClosingBracket(Character ch) {
        return (ch == ')' || ch == ']' || ch == '}');
    }


    /**
     * verify the expression to have only one operator at a time
     * 
     * @param expr is a string expression
     * @return true if good expression, false otherwise
     */
    private static boolean isGoodExpression(String expr) {
        char c;
        int counter = 0;
        for (int i = 0; i < expr.length(); i++) {
            c = expr.charAt(i);
            if (c == ' ')
                continue;
            if (new Token(c).isOperator()) {
                if (++counter > 1)
                    return false;
            } else
                counter = 0;
        }
        return (counter == 0);
    }

    /**
     * evaluate a stack of tokens
     * 
     * @param tkn is the stack of tokens
     * @return result of calculations
     */
    private static Double performOperation(Stack<Token> tkn) {
        // define and initialize the variables
        Token operator = new Token(' ');
        ArrayList<Double> nums = new ArrayList<Double>();

        while (!tkn.isEmpty()) {
            if (tkn.peek().isOperator()) {
                operator = tkn.pop();
            } else if (tkn.peek().isOperand()) {
                nums.add(tkn.pop().getValue());
            } else {
                tkn.pop();
                break;
            }
        }

        // calculate
        double result = 0.0;

        if (!operator.isOperator() && nums.size() == 1)
            return nums.get(0);
        if (nums.size() >= 2) {
            result = nums.get(0);
            for (int i = 1; i < nums.size(); i++)
                result = operator.applyOperator(result, nums.get(i));
        } else if (nums.size() == 1) {
            result = operator.getIdentity();
            result = operator.applyOperator(result, nums.get(0));
        } else {
            if (operator.takesNoOperands()) {
                result = operator.getIdentity();
                result = operator.applyOperator(null, null);
            } else {
                throw new RuntimeException("Operand is missing!");
            }
        }
        return result;

    }

    /**
     * solves an expression without any bracket
     * 
     * @param expr is the expression without brackets
     * @return the result of the expression evaluation
     */
    protected static Double evaluateOneExpr(String expr) {

        // create a stack for easier accessing
        Stack<Token> tkn = new Stack<Token>();

        // parse the expression backwards so i get the operator at the end
        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);

            if (c == ' ')
                continue;
            // check if inserted number
            if (c == '\'') {
                // this is a number
                double num = 0.0;
                int startPos = i;

                i--;
                c = expr.charAt(i);

                while (c != '\'') {
                    if (--i >= 0)
                        c = expr.charAt(i);
                    else
                        break;
                }
                num = Double.parseDouble(expr.substring(i + 1, startPos));
                tkn.push(new Token(num));

            }
            // check if it is number, it could have a decimal part
            if (Character.isDigit(c) || c == '.') {
                // this is a number
                double num = 0.0;
                int startPos = i;
                while (Character.isDigit(c) || c == '.') {
                    if (--i >= 0)
                        c = expr.charAt(i);
                    else
                        break;
                }
                i++;
                num = Double.parseDouble(expr.substring(i, startPos + 1));
                tkn.push(new Token(num));
            }
            // current character is operator
            else if (new Token(c).isOperator()) {
                tkn.push(new Token(c)); // push the current operator to stack
                double output = performOperation(tkn);
                tkn.push(new Token(output)); // push to stack for later use

            }
        }

        double output = performOperation(tkn);

        return output;
    }

    /**
     * solves all expressions inside brackets
     * 
     * @param expr is the prefix expression. It could have brackets
     * @return expression without brackets
     */
    protected static String reduce(String expr) {
        String s = expr;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == ' ')
                continue;
            else if (isOpeningBracket(c)) {
                s = s.substring(0, i) + " '"
                        + reduce(s.substring(i + 1, s.length())) + " ";
            } else if (isClosingBracket(c)) {
                double result;
                result = evaluateOneExpr(s.substring(0, i));
                return result + "' " + s.substring(i + 1, s.length());
            }
        }
        return s;
    }

    /**
     * reach each character as a variable in a string
     * 
     * @param expr string contains character variables
     * @return string with corresponding variables values
     */
    @SuppressWarnings("resource")
    private static String readCharacter(String expr) {
        char c;
        String s = expr;
        for (int i = 0; i < expr.length(); i++) {
            c = expr.charAt(i);

            if (c >= 'a' && c <= 'z') {
                String input = "";
                // read value from user

                System.out
                        .println(String.format("What is the value of '%s'", c));
                Scanner scan = new Scanner(System.in);
                if (scan.hasNextLine()) {
                    input = scan.nextLine();
                }
                if (!isNumeric(input))
                    throw new RuntimeException("input value is not digit");

                // replace the character with the input digit
                s = s.replaceFirst("" + c, " " + input + " ");
            }
        }
        return s;
    }

    /**
     * check if a string contains digits only
     * 
     * @param num string of double values
     * @return true, if it has digits only, false otherwise
     */
    protected static boolean isNumeric(String num) {
        if (num == null) {
            return false;
        }
        try {
            Double.parseDouble(num);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * check if an expression is a valid pre-fix expression
     * @param expr is the given expression
     * @param readWord true if consecutive letters are considered one word
     */
    protected static void isValidExpr(String expr, boolean readWord)
    {
        if (!isBalanced(expr))
            throw new RuntimeException("Unbalanced Expression");
        if (! isPrefix(expr, readWord).getKey())
            throw new RuntimeException("Not a valid Expression");
        else if (!isGoodExpression(expr))
            throw new RuntimeException("Found two consecutive operators");
        
    }
    /**
     * following the CS5040 lecture post-fix evaluation for prefix
     * 
     * @param expr is the prefix expression
     * @return result
     */
    public static Double evaluate(String expr) {
        // check if valid expression. if not throw error
        isValidExpr(expr, false);
        // evaluate
        return evaluateOneExpr(reduce(readCharacter(expr)));
    }
}
