/*
 * FACTORY METHOD  (Creational)
 *
 * Problem:  A class needs to create objects, but it shouldn't hard-code
 *           *which* concrete class to create. `new Truck()` inside the
 *           delivery logic means adding ships later requires editing it.
 *
 * Idea:     Put the creation in a method (the "factory method") that
 *           subclasses override. The parent class works with the general
 *           type (Transport); each subclass decides the concrete type.
 *
 * Analogy:  A franchise HQ writes the recipe for "serve a burger"; each
 *           local branch decides which bakery supplies the bun.
 *
 * Use when: You know the steps of a process but the objects involved vary.
 *
 * Run:      java creational/FactoryMethod.java
 */
public class FactoryMethod {

    // The product: what every factory method returns.
    interface Transport {
        String deliver(String cargo);
    }

    static class Truck implements Transport {
        public String deliver(String cargo) {
            return "Truck drives " + cargo + " by road";
        }
    }

    static class Ship implements Transport {
        public String deliver(String cargo) {
            return "Ship carries " + cargo + " by sea";
        }
    }

    // The creator: shared logic + an abstract factory method.
    abstract static class Logistics {
        // The factory method. Subclasses choose what to create.
        abstract Transport createTransport();

        // Shared business logic that doesn't care which Transport it gets.
        void planDelivery(String cargo) {
            Transport transport = createTransport();
            System.out.println("  " + transport.deliver(cargo));
        }
    }

    static class RoadLogistics extends Logistics {
        Transport createTransport() {
            return new Truck();
        }
    }

    static class SeaLogistics extends Logistics {
        Transport createTransport() {
            return new Ship();
        }
    }

    public static void main(String[] args) {
        Logistics[] companies = { new RoadLogistics(), new SeaLogistics() };
        for (Logistics company : companies) {
            System.out.println(company.getClass().getSimpleName() + ":");
            company.planDelivery("100 boxes");
        }
    }
}

/*
 * A close cousin you'll see often is the "simple factory": a static method
 * with a switch, e.g. `Transport.of("ship")`. It's not the GoF pattern, but
 * it solves the same "hide the `new`" problem when you don't need subclasses.
 *
 * Try it:
 *  1. Add an Airplane transport and an AirLogistics creator.
 *     Notice that planDelivery() didn't need to change.
 */
