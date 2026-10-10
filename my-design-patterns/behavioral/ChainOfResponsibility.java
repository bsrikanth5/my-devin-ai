/*
 * CHAIN OF RESPONSIBILITY  (Behavioral)
 *
 * Problem:  A request might be handled by one of several objects, and the
 *           sender shouldn't need a big if/else deciding which one.
 *
 * Idea:     Link handlers into a chain. Each handler either handles the
 *           request or passes it to the next one. The sender only talks to
 *           the first link.
 *
 * Analogy:  Expense approval: team lead can approve up to $1,000, manager up
 *           to $5,000, director up to $20,000. Anything bigger is rejected.
 *
 * Use when: Several objects may handle a request and the order/set of
 *           handlers should be easy to change. (Web frameworks' middleware
 *           and servlet filters work this way.)
 *
 * Run:      java behavioral/ChainOfResponsibility.java
 */
public class ChainOfResponsibility {

    abstract static class Approver {
        private Approver next;

        // Returns the next handler so calls can be chained when building.
        Approver setNext(Approver next) {
            this.next = next;
            return next;
        }

        void approve(String item, double amount) {
            if (canApprove(amount)) {
                System.out.printf("  %-8s $%,9.2f approved by %s%n", item, amount, getClass().getSimpleName());
            } else if (next != null) {
                next.approve(item, amount);        // pass it along
            } else {
                System.out.printf("  %-8s $%,9.2f REJECTED - nobody can approve it%n", item, amount);
            }
        }

        abstract boolean canApprove(double amount);
    }

    static class TeamLead extends Approver {
        boolean canApprove(double amount) { return amount <= 1_000; }
    }

    static class Manager extends Approver {
        boolean canApprove(double amount) { return amount <= 5_000; }
    }

    static class Director extends Approver {
        boolean canApprove(double amount) { return amount <= 20_000; }
    }

    public static void main(String[] args) {
        Approver chain = new TeamLead();
        chain.setNext(new Manager()).setNext(new Director());

        chain.approve("Mouse", 25);
        chain.approve("Laptop", 2_400);
        chain.approve("Servers", 18_000);
        chain.approve("Building", 2_000_000);
    }
}

/*
 * Try it:
 *  1. Add a CEO who approves up to $1,000,000 at the end of the chain.
 *  2. Remove the Manager from the chain. Which request changes approver?
 */
