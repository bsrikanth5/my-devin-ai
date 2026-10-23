/*
 * TEMPLATE METHOD  (Behavioral)
 *
 * Problem:  Two classes follow the same steps in the same order, with only
 *           a few steps different. Copy-pasting the whole process means
 *           fixing bugs twice.
 *
 * Idea:     A base class defines the overall algorithm in one `final`
 *           method (the template). The steps that vary are abstract methods
 *           subclasses fill in. Optional "hook" methods have a default that
 *           subclasses may override.
 *
 * Analogy:  A recipe card with blanks: "Boil water. Brew ____. Pour into cup.
 *           Add ____ (optional)."
 *
 * Use when: Several variants share a fixed skeleton (data importers, game
 *           turns, test setup/teardown, report generators).
 * vs. Strategy: Template Method varies steps via INHERITANCE; Strategy
 *           swaps the whole algorithm via COMPOSITION.
 *
 * Run:      java behavioral/TemplateMethod.java
 */
public class TemplateMethod {

    abstract static class HotDrink {
        // The template method. `final` so subclasses can't change the order.
        final void prepare() {
            System.out.println("Making " + name() + ":");
            boilWater();
            brew();
            pourIntoCup();
            if (wantsExtras()) {
                addExtras();
            }
        }

        // Steps that are the same for everyone.
        private void boilWater() { System.out.println("  boil water"); }
        private void pourIntoCup() { System.out.println("  pour into cup"); }

        // Steps subclasses MUST provide.
        abstract String name();
        abstract void brew();
        abstract void addExtras();

        // Hook: subclasses MAY override. Default is yes.
        boolean wantsExtras() { return true; }
    }

    static class Tea extends HotDrink {
        String name() { return "tea"; }
        void brew() { System.out.println("  steep the tea bag"); }
        void addExtras() { System.out.println("  add lemon"); }
    }

    static class Coffee extends HotDrink {
        String name() { return "coffee"; }
        void brew() { System.out.println("  drip coffee through filter"); }
        void addExtras() { System.out.println("  add milk and sugar"); }
    }

    static class BlackCoffee extends Coffee {
        String name() { return "black coffee"; }
        @Override
        boolean wantsExtras() { return false; }   // uses the hook
    }

    public static void main(String[] args) {
        new Tea().prepare();
        new Coffee().prepare();
        new BlackCoffee().prepare();
    }
}

/*
 * Try it:
 *  1. Add HotChocolate. Which methods are you forced to write?
 */
