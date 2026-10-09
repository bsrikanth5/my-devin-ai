/*
 * DECORATOR  (Structural)
 *
 * Problem:  You want to add features to an object in many combinations.
 *           Subclasses for every combo (CoffeeWithMilk, CoffeeWithMilkAndSugar,
 *           CoffeeWithSugarAndWhip...) explode in number.
 *
 * Idea:     Wrap the object in another object with the SAME interface. The
 *           wrapper (decorator) adds its bit, then delegates to the wrapped
 *           object. Wrappers can be stacked in any order and any number.
 *
 * Analogy:  Clothes: you're still you, but add a sweater, then a jacket,
 *           then a raincoat. Each layer adds something; take any off.
 *
 * Use when: You need to add behaviour at runtime, in combinations, without
 *           changing the original class. Java's own example:
 *           new BufferedReader(new InputStreamReader(System.in))
 *
 * Run:      java structural/Decorator.java
 */
public class Decorator {

    interface Coffee {
        String description();
        double cost();
    }

    // The base object being decorated.
    static class Espresso implements Coffee {
        public String description() { return "Espresso"; }
        public double cost() { return 2.00; }
    }

    // Base decorator: holds the wrapped coffee and delegates to it.
    abstract static class CoffeeDecorator implements Coffee {
        protected final Coffee inner;

        CoffeeDecorator(Coffee inner) {
            this.inner = inner;
        }
    }

    static class Milk extends CoffeeDecorator {
        Milk(Coffee inner) { super(inner); }
        public String description() { return inner.description() + " + milk"; }
        public double cost() { return inner.cost() + 0.50; }
    }

    static class Sugar extends CoffeeDecorator {
        Sugar(Coffee inner) { super(inner); }
        public String description() { return inner.description() + " + sugar"; }
        public double cost() { return inner.cost() + 0.20; }
    }

    static class WhippedCream extends CoffeeDecorator {
        WhippedCream(Coffee inner) { super(inner); }
        public String description() { return inner.description() + " + whipped cream"; }
        public double cost() { return inner.cost() + 0.70; }
    }

    static void printOrder(Coffee coffee) {
        System.out.printf("  %-45s $%.2f%n", coffee.description(), coffee.cost());
    }

    public static void main(String[] args) {
        printOrder(new Espresso());
        printOrder(new Milk(new Espresso()));
        printOrder(new Sugar(new Milk(new Espresso())));
        printOrder(new WhippedCream(new Milk(new Milk(new Espresso()))));  // double milk!
    }
}

/*
 * Try it:
 *  1. Add a Caramel decorator ($0.60) and order "espresso + caramel + sugar".
 *  2. How many subclasses would you need WITHOUT decorators for 4 add-ons?
 */
