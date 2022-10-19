# Expression Evaluator

This project is part of Virginia Tech CS5040 course with Prof. Tessema Mengistu.

LISP is a family of programming languages with a long history and a distinctive, fully parenthesized prefix notation. Originally specified in 1958, LISP is the second-oldest high-level programming language in widespread use today. Lisp (LISt Processor) was originally created as a practical mathematical notation for computer programs, influenced by the notation of lambda calculus. It quickly became the favored programming language for artificial intelligence (AI) research. As one of the earliest programming languages, Lisp pioneered many ideas in computer science, including tree data structures, automatic storage management, dynamic typing, conditionals, higher-order functions, recursion, etc. Lisp is an expression-oriented language. A Lisp expression is written with its elements separated by whitespace, and surrounded by parentheses. For arithmetic expressions, each of the four arithmetic operators (+,-, *, /) appears before an arbitrary number of operands, which are separated by spaces and enclosed in parentheses. For example, (+ 1 2) is an expression whose elements are the three atoms +, 1, and 2.
The use of parentheses is Lisp's most immediately obvious difference from other programming language families. As a result, students have long given Lisp nicknames such as Lost In Stupid Parentheses.

The following are the behaviours of the four arithmetic operators that we are going to consider for this project:

Addition operator (+)
(+ a b c d ...) - returns the sum of all operands (a, b, c, d,...).
(+) - returns 0.
Subtraction operator (-)
(- a b c d ...) returns a - b - c - d - ....
(- a) - returns -a. the minus operator must have at least one operand.
Multiplication operator (*)
(* a b c d ...) returns the product of all operands (a, b, c, d,...).
(*) - returns 1.
Division operator (/)
(/ a b c d ...) returns a / b / c / d / ....
(/ a) - returns 1/a. The divide operator must have at least one operand.
You can form more complex expressions by combining these basic arithmetic expressions. For example, the following is a valid Lisp expression:

(+ (- 12) (* 4 2 3) (/ (+4) (*) (- 2 3 1)))

The expression is evaluated as:
(+ (- 12) (* 4 2 3) (/ 4 1 -2))
= (+ -12 24 -2)
= 10
