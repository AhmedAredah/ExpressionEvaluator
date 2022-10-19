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

/**
 * A place holder for operators and operands
 * this version accepts only basic operators (+,-,/,*)
 * further development is needed for more operands support
 * 
 * @author Ahmed (AhmedAredah)
 * @version Sep 16, 2022
 */
public class Token {
    /** Define operator holder */
    private Character operator = null;
    /** Define operand holder */
    private Double operand = null;
    /** Check if the Token isOperand */
    private boolean isOperand = false;

    /**
     * Define the token as an Operator
     * 
     * @param c is the operator
     */
    public Token(Character c) {
        this.operator = c;
        this.operand = 0.0;
    }

    /**
     * Define the token as operand
     * 
     * @param operand is the double value
     */
    public Token(Double operand) {
        this.operand = operand;
        this.operator = ' ';
        this.isOperand = true;
    }

    /**
     * evaluate the operator on values
     * 
     * @param value1 is double value
     * @param value2 is double value
     * @return result of type double
     */
    Double applyOperator(Double value1, Double value2) {
        if (!this.isOperator())
            throw new RuntimeException("Token is not a defined operator!");
        // if it is an addition operator
        if (this.operator.equals('+')) {
            if (value2 == null)
                return 0.0;
            return value1 + value2;
        }
        // if it is a negative operator
        else if (this.operator.equals('-')) {
            return value1 - value2;
        }
        // if it is a division operator
        else if (this.operator.equals('/')) {
            return value1 / value2;
        }
        // if it is a multiplication operator
        else  {
            if (value2 == null)
                return 1.0;
            return value1 * value2;
        }
    }

    /**
     * Gets the identity of the operator
     * 
     * @return the identity of the operator. 
     * throws an error if operator is not (+,-,*,/)
     */
    public Double getIdentity() {
        // check if it is an operator first
        if (!this.isOperator())
            throw new RuntimeException("Token is not a defined operator!");
        // zero if addition or subtraction
        if (this.operator.equals('+') || this.operator.equals('-'))
            return 0.0;
        // one if division or multiplication
        return 1.0;
    }

    /**
     * check if the operator can take no operands
     * 
     * @return true if it can and false otherwise
     */
    public boolean takesNoOperands() {
        if (!this.isOperator())
            throw new RuntimeException("Token is not a defined operator!");

        // if multiplication or addition, it does not need an operands
        return (this.operator.equals('+') || this.operator.equals('*'));
    }

    /**
     * checks if the Token is operator or operand
     * 
     * @return true if operator, false if operand
     */
    public boolean isOperator() {
        // other operators could have been passed
        // check we only have those basic operators
        return (operator == '+' || operator == '-' || operator == '/'
                || operator == '*');
    }

    /**
     * 
     * @return true if the token is operand, false otherwise
     */
    protected boolean isOperand() {
        return isOperand;
    }

    /**
     * get the value of operand
     * 
     * @return operand value (double)
     */
    public Double getValue() {
        return this.operand;
    }

    /**
     * get the value of operator
     * 
     * @return operator value (char)
     */
    public Character getOperator() {
        return this.operator;
    }
}
