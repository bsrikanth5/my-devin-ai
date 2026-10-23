/*
 * ABSTRACT FACTORY  (Creational)
 *
 * Problem:  You create *families* of related objects that must match. A dark
 *           theme needs a dark button AND a dark checkbox; mixing a dark
 *           button with a light checkbox looks broken.
 *
 * Idea:     One factory interface with a create-method per product
 *           (createButton, createCheckbox). Each concrete factory builds
 *           one consistent family. The app only talks to the interfaces.
 *
 * Analogy:  An IKEA collection: pick "Modern" and the chair, table and lamp
 *           all come in the same style.
 *
 * Use when: Products come in families and must be used together.
 * vs. Factory Method: Factory Method creates ONE product via subclassing;
 *           Abstract Factory creates a FAMILY of products via one object.
 *
 * Run:      java creational/AbstractFactory.java
 */
public class AbstractFactory {

    // The products.
    interface Button {
        String render();
    }

    interface Checkbox {
        String render();
    }

    // The abstract factory.
    interface ThemeFactory {
        Button createButton();
        Checkbox createCheckbox();
    }

    // Light family.
    static class LightButton implements Button {
        public String render() { return "[ OK ] (white background)"; }
    }

    static class LightCheckbox implements Checkbox {
        public String render() { return "[x] Remember me (black text)"; }
    }

    static class LightThemeFactory implements ThemeFactory {
        public Button createButton() { return new LightButton(); }
        public Checkbox createCheckbox() { return new LightCheckbox(); }
    }

    // Dark family.
    static class DarkButton implements Button {
        public String render() { return "[ OK ] (black background)"; }
    }

    static class DarkCheckbox implements Checkbox {
        public String render() { return "[x] Remember me (white text)"; }
    }

    static class DarkThemeFactory implements ThemeFactory {
        public Button createButton() { return new DarkButton(); }
        public Checkbox createCheckbox() { return new DarkCheckbox(); }
    }

    // Client code: knows only ThemeFactory, Button and Checkbox.
    static void drawLoginForm(ThemeFactory factory) {
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        System.out.println("  " + checkbox.render());
        System.out.println("  " + button.render());
    }

    public static void main(String[] args) {
        System.out.println("Light theme:");
        drawLoginForm(new LightThemeFactory());

        System.out.println("Dark theme:");
        drawLoginForm(new DarkThemeFactory());
    }
}

/*
 * Try it:
 *  1. Add a HighContrast family. Only new classes - drawLoginForm() stays the same.
 *  2. Add a third product, TextField. What has to change? (Every factory - that's
 *     the trade-off of this pattern.)
 */
