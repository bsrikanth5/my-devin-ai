/*
 * INTERPRETER  (Behavioral)
 *
 * Problem:  You need to evaluate sentences in a small language: math
 *           expressions, search filters ("price < 50 AND color = red"),
 *           simple rules or commands.
 *
 * Idea:     Represent each grammar rule as a class. A sentence becomes a tree
 *           of these objects, and each node knows how to `interpret()`
 *           itself - usually by interpreting its children first.
 *
 * Analogy:  A musician reading sheet music: each symbol (note, rest, repeat)
 *           has a rule for how to play it, and the piece is just symbols
 *           combined.
 *
 * Use when: The language is small and simple. For anything big, use a
 *           real parser library instead. (Regular expressions and SQL
 *           engines are interpreters at heart.)
 *
 * Run:      java behavioral/Interpreter.java
 */
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Interpreter {

    interface Expression {
        int interpret(Map<String, Integer> variables);
    }

    // Terminal expressions: the leaves of the tree.
    static class Number implements Expression {
        private final int value;
        Number(int value) { this.value = value; }
        public int interpret(Map<String, Integer> vars) { return value; }
        public String toString() { return String.valueOf(value); }
    }

    static class Variable implements Expression {
        private final String name;
        Variable(String name) { this.name = name; }
        public int interpret(Map<String, Integer> vars) { return vars.get(name); }
        public String toString() { return name; }
    }

    // Non-terminal expressions: combine other expressions.
    static class Add implements Expression {
        private final Expression left, right;
        Add(Expression left, Expression right) { this.left = left; this.right = right; }
        public int interpret(Map<String, Integer> vars) { return left.interpret(vars) + right.interpret(vars); }
        public String toString() { return "(" + left + " + " + right + ")"; }
    }

    static class Subtract implements Expression {
        private final Expression left, right;
        Subtract(Expression left, Expression right) { this.left = left; this.right = right; }
        public int interpret(Map<String, Integer> vars) { return left.interpret(vars) - right.interpret(vars); }
        public String toString() { return "(" + left + " - " + right + ")"; }
    }

    static class Multiply implements Expression {
        private final Expression left, right;
        Multiply(Expression left, Expression right) { this.left = left; this.right = right; }
        public int interpret(Map<String, Integer> vars) { return left.interpret(vars) * right.interpret(vars); }
        public String toString() { return "(" + left + " * " + right + ")"; }
    }

    // A tiny parser for postfix notation ("3 4 +" means 3 + 4), which is
    // easy to parse with a stack: numbers/variables are pushed, an operator
    // pops two expressions and pushes the combined one.
    static Expression parsePostfix(String input) {
        Deque<Expression> stack = new ArrayDeque<>();
        for (String token : input.split(" ")) {
            switch (token) {
                case "+": { Expression r = stack.pop(); stack.push(new Add(stack.pop(), r)); break; }
                case "-": { Expression r = stack.pop(); stack.push(new Subtract(stack.pop(), r)); break; }
                case "*": { Expression r = stack.pop(); stack.push(new Multiply(stack.pop(), r)); break; }
                default:
                    if (token.matches("-?\\d+")) {
                        stack.push(new Number(Integer.parseInt(token)));
                    } else {
                        stack.push(new Variable(token));
                    }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        // Build a tree by hand: (2 + 3) * x
        Expression byHand = new Multiply(new Add(new Number(2), new Number(3)), new Variable("x"));
        System.out.println(byHand + " with x=4  ->  " + byHand.interpret(Map.of("x", 4)));

        // Or parse it from text.
        Expression parsed = parsePostfix("price qty * discount -");
        Map<String, Integer> order = Map.of("price", 25, "qty", 3, "discount", 10);
        System.out.println(parsed + " with " + order + "  ->  " + parsed.interpret(order));
    }
}

/*
 * Try it:
 *  1. Add a Divide expression and a "/" case in parsePostfix.
 *  2. Evaluate "a b + a b - *" with a=5, b=2. Check the answer by hand.
 */
