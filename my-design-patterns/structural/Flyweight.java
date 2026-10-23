/*
 * FLYWEIGHT  (Structural)
 *
 * Problem:  You need a huge number of similar objects (trees in a game
 *           forest, characters in a document) and each one stores the same
 *           heavy data again and again. Memory runs out.
 *
 * Idea:     Split the data in two:
 *             - intrinsic: shared, never changes (tree species, colour, texture)
 *             - extrinsic: unique per object (x, y position)
 *           Store intrinsic data once in shared "flyweight" objects, handed
 *           out by a factory that reuses existing ones.
 *
 * Analogy:  A font: the letter "e" shape is stored once; the document just
 *           records where each "e" appears.
 *
 * Use when: Many objects, mostly identical data, and memory matters.
 *           Java does this for you with Integer.valueOf(-128..127) and
 *           string literals.
 *
 * Run:      java structural/Flyweight.java
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Flyweight {

    // Flyweight: the shared, immutable part.
    static final class TreeType {
        final String species;
        final String color;
        final byte[] texture;   // pretend this is a big image

        TreeType(String species, String color) {
            this.species = species;
            this.color = color;
            this.texture = new byte[10_000];
        }
    }

    // Factory that guarantees each TreeType is created only once.
    static class TreeTypeFactory {
        private static final Map<String, TreeType> cache = new HashMap<>();

        static TreeType get(String species, String color) {
            String key = species + "/" + color;
            return cache.computeIfAbsent(key, k -> new TreeType(species, color));
        }

        static int count() {
            return cache.size();
        }
    }

    // Context object: the cheap, unique part + a reference to the shared part.
    static final class Tree {
        final int x;
        final int y;
        final TreeType type;

        Tree(int x, int y, TreeType type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }

    public static void main(String[] args) {
        String[][] kinds = { {"Oak", "green"}, {"Pine", "dark green"}, {"Birch", "white"} };
        Random random = new Random(42);
        List<Tree> forest = new ArrayList<>();

        for (int i = 0; i < 100_000; i++) {
            String[] kind = kinds[random.nextInt(kinds.length)];
            forest.add(new Tree(random.nextInt(1000), random.nextInt(1000),
                    TreeTypeFactory.get(kind[0], kind[1])));
        }

        System.out.println("Trees planted:      " + forest.size());
        System.out.println("TreeType objects:   " + TreeTypeFactory.count());
        System.out.println("Texture memory now: ~" + TreeTypeFactory.count() * 10 + " KB");
        System.out.println("Without flyweight:  ~" + forest.size() * 10 / 1000 + " MB");
        System.out.println("First tree: " + forest.get(0).type.species + " at (" + forest.get(0).x + ", " + forest.get(0).y + ")");
    }
}

/*
 * Try it:
 *  1. Add a fourth species. How many TreeType objects exist now?
 */
