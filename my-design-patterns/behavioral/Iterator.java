/*
 * ITERATOR  (Behavioral)
 *
 * Problem:  Code that loops over a collection shouldn't need to know how
 *           it's stored inside (array, linked list, tree, database pages).
 *
 * Idea:     The collection hands out an iterator object with two methods:
 *           hasNext() and next(). Callers just ask for "the next one" until
 *           there are no more. You can offer several iterators over the same
 *           data (forward, reverse, filtered...).
 *
 * Analogy:  A TV remote's "next channel" button: you don't need to know how
 *           channels are stored, you just press next.
 *
 * Use when: You build your own collection type. In Java, implementing
 *           Iterable<T> is what makes `for (T x : collection)` work.
 *
 * Run:      java behavioral/Iterator.java
 */
import java.util.NoSuchElementException;

public class Iterator {

    // A custom collection that stores songs in a plain array.
    static class Playlist implements Iterable<String> {
        private final String[] songs;

        Playlist(String... songs) {
            this.songs = songs;
        }

        // Default iterator: front to back. Required by Iterable.
        @Override
        public java.util.Iterator<String> iterator() {
            return new java.util.Iterator<String>() {
                private int index = 0;

                public boolean hasNext() { return index < songs.length; }

                public String next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    return songs[index++];
                }
            };
        }

        // A second way to walk the same data.
        Iterable<String> reversed() {
            return () -> new java.util.Iterator<String>() {
                private int index = songs.length - 1;

                public boolean hasNext() { return index >= 0; }

                public String next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    return songs[index--];
                }
            };
        }
    }

    public static void main(String[] args) {
        Playlist playlist = new Playlist("Intro", "Verse", "Chorus", "Outro");

        System.out.println("for-each (uses iterator() behind the scenes):");
        for (String song : playlist) {
            System.out.println("  " + song);
        }

        System.out.println("Reversed:");
        for (String song : playlist.reversed()) {
            System.out.println("  " + song);
        }

        System.out.println("Driving the iterator by hand:");
        java.util.Iterator<String> it = playlist.iterator();
        while (it.hasNext()) {
            System.out.println("  next() -> " + it.next());
        }
    }
}

/*
 * Note: this lesson's class is named Iterator, so java.util.Iterator is
 * written out in full to avoid a name clash.
 *
 * Try it:
 *  1. Add `Iterable<String> everyOther()` that skips every second song.
 */
