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

import java.util.Scanner;

/**
 * Extends ExpressionEvaluator. The expressionEvaluator2 supports variables
 * of names instead of characters only. Evaluate the prefix expression as per
 * the lecture instruction of post-fix
 * 
 * @author Ahmed (AhmedAredah)
 * @version Sep 16, 2022
 */
public class ExpressionEvaluator2 extends ExpressionEvaluator {

    /**
     * reach each word as a variable in a string
     * 
     * @param expr string contains words variables
     * @return string with corresponding variables values
     */
    @SuppressWarnings("resource")
    private static String readWord(String expr) {
        char c;
        String s = expr;
        for (int i = 0; i < expr.length(); i++) {
            c = expr.charAt(i);
            if (c >= 'a' && c <= 'z') {
                String var = "";
                int startPos = i;
                while (Character.isLetter(c)) {
                    if (++i < expr.length())
                        c = expr.charAt(i);
                    else
                        break;
                }
                var = expr.substring(startPos, i);

                // read value from user
                String input = "";

                System.out.println(
                        String.format("What is the value of '%s'", var));
                Scanner scan = new Scanner(System.in);
                if (scan.hasNextLine())
                    input = scan.nextLine();

                if (!isNumeric(input))
                    throw new RuntimeException("input value is not digit");

                // replace the character with the input digit
                s = s.replaceFirst(var, " " + input + " ");
            }
        }
        return s;
    }

    /**
     * following the CS5040 lecture postfix evaluation for prefix It accepts
     * names as variables
     * 
     * @param expr is the prefix expression
     * @return result
     */
    public static Double evaluate(String expr) {
        // check if valid expression. if not throw error
        isValidExpr(expr, true);
        // evaluate
        return evaluateOneExpr(reduce(readWord(expr)));
    }
}
