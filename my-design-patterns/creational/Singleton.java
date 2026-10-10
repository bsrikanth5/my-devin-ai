/*
 * SINGLETON  (Creational)
 *
 * Problem:  Some things should exist exactly once in a program: app settings,
 *           a logger, a connection pool. If every class calls `new Config()`
 *           you end up with many copies that disagree with each other.
 *
 * Idea:     Make the constructor private so nobody else can call `new`, and
 *           hand out one shared instance through a static method.
 *
 * Analogy:  A country has one president at a time. You don't create one -
 *           you ask "who is the president?" and everyone gets the same answer.
 *
 * Use when: Exactly one instance must exist and many places need it.
 * Careful:  A singleton is global state. Overusing it makes code hard to test;
 *           passing objects in (dependency injection) is often better.
 *
 * Run:      java creational/Singleton.java
 */
import java.util.HashMap;
import java.util.Map;

public class Singleton {

    static final class AppConfig {
        // 1. The one and only instance, created the first time someone asks.
        private static AppConfig instance;

        private final Map<String, String> settings = new HashMap<>();

        // 2. Private constructor: other classes cannot call `new AppConfig()`.
        private AppConfig() {
            System.out.println("  (AppConfig created - this prints only once)");
            settings.put("theme", "light");
        }

        // 3. Global access point. `synchronized` keeps it safe if two threads
        //    call it at the same moment.
        static synchronized AppConfig getInstance() {
            if (instance == null) {
                instance = new AppConfig();
            }
            return instance;
        }

        String get(String key) {
            return settings.get(key);
        }

        void set(String key, String value) {
            settings.put(key, value);
        }
    }

    // The shortest thread-safe singleton in Java: an enum with a single value.
    enum Logger {
        INSTANCE;

        private int count = 0;

        void log(String message) {
            count++;
            System.out.println("  [log #" + count + "] " + message);
        }
    }

    public static void main(String[] args) {
        System.out.println("Asking for the config twice:");
        AppConfig a = AppConfig.getInstance();
        AppConfig b = AppConfig.getInstance();
        System.out.println("  a == b ? " + (a == b));

        a.set("theme", "dark");
        System.out.println("  changed theme through a; b sees: " + b.get("theme"));

        System.out.println("\nEnum singleton:");
        Logger.INSTANCE.log("app started");
        Logger.INSTANCE.log("user logged in");
    }
}

/*
 * Try it:
 *  1. Add a `language` setting and read it from a third getInstance() call.
 *  2. Delete `synchronized`. Why could two threads now create two instances?
 */
