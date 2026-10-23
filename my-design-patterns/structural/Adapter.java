/*
 * ADAPTER  (Structural)
 *
 * Problem:  Your code expects one interface, but a class you need (often a
 *           third-party library you can't edit) has a different one.
 *
 * Idea:     Write a small wrapper - the adapter - that implements the
 *           interface your code expects and translates each call into the
 *           other class's API.
 *
 * Analogy:  A travel plug adapter: your laptop charger doesn't change, the
 *           wall socket doesn't change, the adapter sits in between.
 *
 * Use when: Integrating legacy code or an external library without
 *           rewriting either side.
 *
 * Run:      java structural/Adapter.java
 */
public class Adapter {

    // What OUR app expects: amounts in dollars.
    interface PaymentProcessor {
        void pay(double dollars);
    }

    // Our existing implementation, already fits the interface.
    static class CardProcessor implements PaymentProcessor {
        public void pay(double dollars) {
            System.out.printf("  Card charged $%.2f%n", dollars);
        }
    }

    // A third-party class we can't change: different method name, takes cents.
    static class LegacyPayGateway {
        void makePayment(long cents, String currency) {
            System.out.println("  LegacyPayGateway: sent " + cents + " " + currency + " cents");
        }
    }

    // The adapter: looks like a PaymentProcessor, delegates to the gateway.
    static class LegacyPayAdapter implements PaymentProcessor {
        private final LegacyPayGateway gateway;

        LegacyPayAdapter(LegacyPayGateway gateway) {
            this.gateway = gateway;
        }

        public void pay(double dollars) {
            long cents = Math.round(dollars * 100);   // translate the data...
            gateway.makePayment(cents, "USD");        // ...and the method call
        }
    }

    // Client code only knows PaymentProcessor.
    static void checkout(PaymentProcessor processor, double total) {
        System.out.println("Checkout with " + processor.getClass().getSimpleName() + ":");
        processor.pay(total);
    }

    public static void main(String[] args) {
        checkout(new CardProcessor(), 19.99);
        checkout(new LegacyPayAdapter(new LegacyPayGateway()), 19.99);
    }
}

/*
 * Try it:
 *  1. Add a CryptoWallet class with `send(String amountAsText)` and write an
 *     adapter for it. checkout() shouldn't change.
 */
