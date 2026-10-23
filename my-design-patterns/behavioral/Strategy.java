/*
 * STRATEGY  (Behavioral)
 *
 * Problem:  There are several ways to do one task (calculate shipping,
 *           sort, compress, pay) and the code picks one with a growing
 *           if/else. Adding a new way means editing that code.
 *
 * Idea:     Put each way (algorithm) in its own class behind a common
 *           interface. The code that uses it holds a reference to "some
 *           strategy" and can swap it at runtime.
 *
 * Analogy:  Getting to the airport: car, bus or bike. Same goal, different
 *           strategy - you pick one based on time, money and weather.
 *
 * Use when: You have interchangeable algorithms. In modern Java a strategy
 *           is often just a lambda: list.sort(Comparator.comparing(...))
 *           passes a sorting strategy.
 *
 * Run:      java behavioral/Strategy.java
 */
public class Strategy {

    // The strategy interface.
    interface ShippingStrategy {
        double cost(double weightKg);
    }

    static class StandardShipping implements ShippingStrategy {
        public double cost(double weightKg) { return 5 + weightKg * 1.0; }
    }

    static class ExpressShipping implements ShippingStrategy {
        public double cost(double weightKg) { return 15 + weightKg * 2.5; }
    }

    static class StorePickup implements ShippingStrategy {
        public double cost(double weightKg) { return 0; }
    }

    static class FreeOver50Shipping implements ShippingStrategy {
        public double cost(double weightKg) { return 0; }
    }

    // The context: uses whichever strategy it's given.
    static class Checkout {
        private ShippingStrategy shipping;

        Checkout(ShippingStrategy shipping) {
            this.shipping = shipping;
        }

        void setShipping(ShippingStrategy shipping) {
            this.shipping = shipping;
        }

        void printTotal(double itemsTotal, double weightKg) {
            double total = itemsTotal + shipping.cost(weightKg);
            System.out.printf("  %-18s total $%.2f%n", shipping.getClass().getSimpleName(), total);
        }
    }

    public static void main(String[] args) {
        double items = 55.00;
        double weight = 3.0;

        Checkout checkout = new Checkout(new StandardShipping());
        checkout.printTotal(items, weight);

        checkout.setShipping(new ExpressShipping());   // swap at runtime
        checkout.printTotal(items, weight);

        checkout.setShipping(new StorePickup());
        checkout.printTotal(items, weight);

        checkout.setShipping(new FreeOver50Shipping());
        checkout.printTotal(items, weight);

        // A one-off strategy as a lambda - no class needed.
        ShippingStrategy flatRate = kg -> 7.99;
        System.out.printf("  %-18s total $%.2f%n", "lambda flat rate", items + flatRate.cost(weight));
    }
}