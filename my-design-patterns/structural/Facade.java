/*
 * FACADE  (Structural)
 *
 * Problem:  Doing one simple thing ("watch a movie") requires talking to
 *           many classes in the right order. Every caller has to know all
 *           of those details.
 *
 * Idea:     Add one class with a few simple methods that does the
 *           orchestration for you. The complex parts still exist and can be
 *           used directly - the facade is just a friendly front door.
 *
 * Analogy:  A restaurant waiter. You say "one pasta"; you don't talk to the
 *           chef, the dishwasher and the supplier yourself.
 *
 * Use when: A subsystem is complicated and most callers need only a few
 *           common workflows.
 *
 * Run:      java structural/Facade.java
 */
public class Facade {

    // A "complicated" subsystem: several classes with their own APIs.
    static class Lights {
        void dim(int percent) { System.out.println("  Lights dimmed to " + percent + "%"); }
        void on() { System.out.println("  Lights on"); }
    }

    static class Projector {
        void on() { System.out.println("  Projector on"); }
        void setInput(String input) { System.out.println("  Projector input -> " + input); }
        void off() { System.out.println("  Projector off"); }
    }

    static class SoundSystem {
        void on() { System.out.println("  Sound on"); }
        void setSurround(boolean surround) { System.out.println("  Surround sound " + (surround ? "enabled" : "disabled")); }
        void off() { System.out.println("  Sound off"); }
    }

    static class StreamingPlayer {
        void play(String movie) { System.out.println("  Playing \"" + movie + "\""); }
        void stop() { System.out.println("  Playback stopped"); }
    }

    // The facade: one simple API on top.
    static class HomeTheater {
        private final Lights lights = new Lights();
        private final Projector projector = new Projector();
        private final SoundSystem sound = new SoundSystem();
        private final StreamingPlayer player = new StreamingPlayer();

        void watchMovie(String movie) {
            System.out.println("Get ready to watch a movie...");
            lights.dim(10);
            projector.on();
            projector.setInput("HDMI 1");
            sound.on();
            sound.setSurround(true);
            player.play(movie);
        }

        void endMovie() {
            System.out.println("Shutting down...");
            player.stop();
            sound.off();
            projector.off();
            lights.on();
        }
    }

    public static void main(String[] args) {
        HomeTheater theater = new HomeTheater();
        theater.watchMovie("Inception");   // one call instead of six
        theater.endMovie();
    }
}

/*
 * Try it:
 *  1. Add a `listenToMusic(String album)` method that uses only lights + sound.
 */
