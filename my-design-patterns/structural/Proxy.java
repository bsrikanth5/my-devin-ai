/*
 * PROXY  (Structural)
 *
 * Problem:  You want to control access to an object: create it only when
 *           really needed (it's slow/heavy), check permissions, cache
 *           results, or log calls - without changing the object itself.
 *
 * Idea:     Put a stand-in object in front of the real one. The proxy has
 *           the same interface, so callers can't tell the difference; it
 *           does its extra work and then forwards to the real object.
 *
 * Analogy:  A credit card is a proxy for your bank account: same "pay"
 *           action, plus checks, and the cash isn't moved until needed.
 *
 * Common kinds:
 *   - virtual proxy:    delay creating an expensive object (shown below)
 *   - protection proxy: check permissions before forwarding (shown below)
 *   - caching proxy:    remember results of slow calls
 * vs. Decorator: same structure, different intent. A decorator ADDS features;
 *   a proxy CONTROLS ACCESS to the same features.
 *
 * Run:      java structural/Proxy.java
 */
public class Proxy {

    interface Image {
        void display();
    }

    // The real, expensive object.
    static class HighResImage implements Image {
        private final String file;

        HighResImage(String file) {
            this.file = file;
            System.out.println("  Loading " + file + " from disk... (slow)");
        }

        public void display() {
            System.out.println("  Displaying " + file);
        }
    }

    // Virtual proxy: only creates the real image the first time it's shown.
    static class LazyImage implements Image {
        private final String file;
        private HighResImage real;

        LazyImage(String file) {
            this.file = file;
        }

        public void display() {
            if (real == null) {
                real = new HighResImage(file);
            }
            real.display();
        }
    }

    // Protection proxy: checks the user before forwarding.
    static class ProtectedImage implements Image {
        private final Image inner;
        private final String user;

        ProtectedImage(Image inner, String user) {
            this.inner = inner;
            this.user = user;
        }

        public void display() {
            if (!user.equals("admin")) {
                System.out.println("  Access denied for " + user);
                return;
            }
            inner.display();
        }
    }

    public static void main(String[] args) {
        System.out.println("Creating a gallery of 3 lazy images (nothing loads yet):");
        Image[] gallery = { new LazyImage("a.png"), new LazyImage("b.png"), new LazyImage("c.png") };

        System.out.println("User opens only the first image, twice:");
        gallery[0].display();
        gallery[0].display();   // already loaded, no second "Loading"

        System.out.println("Protected image:");
        new ProtectedImage(new LazyImage("secret.png"), "guest").display();
        new ProtectedImage(new LazyImage("secret.png"), "admin").display();
    }
}

/*
 * Try it:
 *  1. Write a LoggingImage proxy that prints the time before each display().
 */
