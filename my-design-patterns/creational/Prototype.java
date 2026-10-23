/*
 * PROTOTYPE  (Creational)
 *
 * Problem:  Creating an object from scratch is expensive or fiddly (lots of
 *           setup), and you need many similar ones.
 *
 * Idea:     Keep a ready-made "prototype" and create new objects by copying
 *           it, then tweak the copy. The object knows how to copy itself.
 *
 * Analogy:  Photocopying a filled-in form and changing just the name,
 *           instead of filling in a blank form every time.
 *
 * Use when: Objects are costly to set up, or you want many variations of
 *           a preconfigured template.
 * Careful:  Shallow vs deep copy. If the copy shares a List with the
 *           original, changing one changes both. Copy mutable fields too.
 *
 * Run:      java creational/Prototype.java
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Prototype {

    static class Enemy {
        String type;
        int health;
        List<String> abilities;

        Enemy(String type, int health, List<String> abilities) {
            this.type = type;
            this.health = health;
            this.abilities = abilities;
        }

        // Copy constructor style "clone": a DEEP copy of the abilities list.
        Enemy copy() {
            return new Enemy(type, health, new ArrayList<>(abilities));
        }

        @Override
        public String toString() {
            return type + "(hp=" + health + ", abilities=" + abilities + ")";
        }
    }

    // A registry of ready-made prototypes, looked up by name.
    static class EnemyRegistry {
        private final Map<String, Enemy> prototypes = new HashMap<>();

        void register(String name, Enemy prototype) {
            prototypes.put(name, prototype);
        }

        Enemy spawn(String name) {
            return prototypes.get(name).copy();
        }
    }

    public static void main(String[] args) {
        EnemyRegistry registry = new EnemyRegistry();
        registry.register("goblin", new Enemy("Goblin", 30, new ArrayList<>(List.of("stab"))));
        registry.register("dragon", new Enemy("Dragon", 500, new ArrayList<>(List.of("fly", "fire"))));

        Enemy g1 = registry.spawn("goblin");
        Enemy g2 = registry.spawn("goblin");
        g2.health = 45;
        g2.abilities.add("throw rock");   // only affects g2 because copy() is deep

        System.out.println("g1:    " + g1);
        System.out.println("g2:    " + g2);
        System.out.println("fresh: " + registry.spawn("goblin"));
        System.out.println("boss:  " + registry.spawn("dragon"));
    }
}

/*
 * Try it:
 *  1. Change copy() to pass `abilities` instead of `new ArrayList<>(abilities)`
 *     (a shallow copy). Run again: what happens to g1 and "fresh"?
 */
