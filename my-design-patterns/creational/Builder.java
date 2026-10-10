/*
 * BUILDER  (Creational)
 *
 * Problem:  An object has many optional parts. Constructors get out of hand:
 *               new Pizza("large", true, false, true, false, true)
 *           Which boolean is olives? Nobody remembers.
 *
 * Idea:     A separate Builder object collects the parts step by step with
 *           readable, chainable methods, then `build()` creates the final
 *           (often immutable) object in one go.
 *
 * Analogy:  Ordering at a sandwich counter: "large, add cheese, add
 *           mushrooms... that's it" - then they hand you the sandwich.
 *
 * Use when: Many optional parameters, or construction needs validation.
 *           You've used this already: StringBuilder, HttpRequest.newBuilder().
 *
 * Run:      java creational/Builder.java
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Builder {

    static final class Pizza {
        private final String size;           // required
        private final String crust;          // optional, has a default
        private final List<String> toppings; // optional

        // Private: the only way to make a Pizza is through Pizza.builder(...).build().
        private Pizza(PizzaBuilder b) {
            this.size = b.size;
            this.crust = b.crust;
            this.toppings = Collections.unmodifiableList(new ArrayList<>(b.toppings));
        }

        static PizzaBuilder builder(String size) {
            return new PizzaBuilder(size);
        }

        @Override
        public String toString() {
            return size + " pizza, " + crust + " crust, toppings=" + toppings;
        }
    }

    static final class PizzaBuilder {
        private final String size;
        private String crust = "regular";
        private final List<String> toppings = new ArrayList<>();

        private PizzaBuilder(String size) {
            this.size = size;
        }

        // Each step returns `this`, which is what makes chaining possible.
        PizzaBuilder thinCrust() {
            this.crust = "thin";
            return this;
        }

        PizzaBuilder topping(String topping) {
            this.toppings.add(topping);
            return this;
        }

        Pizza build() {
            if (toppings.size() > 5) {
                throw new IllegalStateException("Max 5 toppings");
            }
            return new Pizza(this);
        }
    }

    public static void main(String[] args) {
        Pizza plain = Pizza.builder("small").build();

        Pizza loaded = Pizza.builder("large")
                .thinCrust()
                .topping("cheese")
                .topping("mushrooms")
                .topping("olives")
                .build();

        System.out.println(plain);
        System.out.println(loaded);

        try {
            PizzaBuilder tooMany = Pizza.builder("medium");
            for (int i = 1; i <= 6; i++) {
                tooMany.topping("topping" + i);
            }
            tooMany.build();
        } catch (IllegalStateException e) {
            System.out.println("Validation in build(): " + e.getMessage());
        }
    }
}

/*
 * Try it:
 *  1. Add an optional `extraCheese()` step.
 *  2. Make build() reject an empty size string.
 */
