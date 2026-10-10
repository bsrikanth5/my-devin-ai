/*
 * STATE  (Behavioral)
 *
 * Problem:  An object behaves differently depending on its current state,
 *           and every method turns into a big switch:
 *               if (status == NEW) ... else if (status == PAID) ... else ...
 *           Adding a state means editing every method.
 *
 * Idea:     Make each state its own class with the same interface. The
 *           object (context) holds a reference to its current state object
 *           and delegates to it. States decide when to switch to another
 *           state.
 *
 * Analogy:  A phone's buttons: the same button does different things when
 *           the phone is locked, unlocked, or in a call.
 *
 * Use when: Behaviour depends on state and the transition rules are getting
 *           complicated (orders, documents, media players, game characters).
 * vs. Strategy: same structure; in State the objects swap THEMSELVES as
 *           things happen, in Strategy the caller picks one.
 *
 * Run:      java behavioral/State.java
 */
public class State {

    interface OrderState {
        void pay(Order order);
        void ship(Order order);
        void cancel(Order order);
        String name();
    }

    // The context: delegates every action to its current state.
    static class Order {
        private OrderState state = new NewState();

        void setState(OrderState next) {
            System.out.println("    state: " + state.name() + " -> " + next.name());
            this.state = next;
        }

        void pay()    { System.out.println("  pay()");    state.pay(this); }
        void ship()   { System.out.println("  ship()");   state.ship(this); }
        void cancel() { System.out.println("  cancel()"); state.cancel(this); }
    }

    static class NewState implements OrderState {
        public String name() { return "NEW"; }
        public void pay(Order o) { o.setState(new PaidState()); }
        public void ship(Order o) { System.out.println("    can't ship: not paid yet"); }
        public void cancel(Order o) { o.setState(new CancelledState()); }
    }

    static class PaidState implements OrderState {
        public String name() { return "PAID"; }
        public void pay(Order o) { System.out.println("    already paid"); }
        public void ship(Order o) { o.setState(new ShippedState()); }
        public void cancel(Order o) {
            System.out.println("    refund issued");
            o.setState(new CancelledState());
        }
    }

    static class ShippedState implements OrderState {
        public String name() { return "SHIPPED"; }
        public void pay(Order o) { System.out.println("    already paid"); }
        public void ship(Order o) { System.out.println("    already shipped"); }
        public void cancel(Order o) { System.out.println("    too late to cancel - it's on the way"); }
    }

    static class CancelledState implements OrderState {
        public String name() { return "CANCELLED"; }
        public void pay(Order o) { System.out.println("    order is cancelled"); }
        public void ship(Order o) { System.out.println("    order is cancelled"); }
        public void cancel(Order o) { System.out.println("    already cancelled"); }
    }

    public static void main(String[] args) {
        System.out.println("Order 1:");
        Order first = new Order();
        first.ship();     // rejected
        first.pay();
        first.ship();
        first.cancel();   // rejected

        System.out.println("Order 2:");
        Order second = new Order();
        second.pay();
        second.cancel();  // refund
        second.ship();    // rejected
    }
}

/*
 * Try it:
 *  1. Add a DELIVERED state reached by a new deliver() action from SHIPPED.
 *     Notice which classes you had to touch.
 */
