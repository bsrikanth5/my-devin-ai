/*
 * MEMENTO  (Behavioral)
 *
 * Problem:  You want "save points" so you can restore an object to an
 *           earlier state - but without exposing all its private fields to
 *           the outside world.
 *
 * Idea:     The object (originator) creates a snapshot object (memento) of
 *           its own state and can later restore from it. Someone else
 *           (caretaker) stores the mementos but never looks inside.
 *
 * Analogy:  Saving a video game. The save file holds your exact state; the
 *           console's "load" menu just keeps the list of saves.
 *
 * Use when: Undo, checkpoints, "cancel changes" in forms.
 * vs. Command: Command undoes by running the reverse action; Memento undoes
 *           by restoring a full snapshot.
 *
 * Run:      java behavioral/Memento.java
 */
import java.util.ArrayDeque;
import java.util.Deque;

public class Memento {

    // Originator: owns the state and knows how to snapshot/restore it.
    static class Document {
        private String text = "";
        private int cursor = 0;

        void type(String words) {
            text = text.substring(0, cursor) + words + text.substring(cursor);
            cursor += words.length();
        }

        Snapshot save() {
            return new Snapshot(text, cursor);
        }

        void restore(Snapshot snapshot) {
            this.text = snapshot.text;
            this.cursor = snapshot.cursor;
        }

        @Override
        public String toString() {
            return "\"" + text + "\" (cursor at " + cursor + ")";
        }
    }

    // Memento: an immutable snapshot with private fields. In a real project it
    // would be visible only to Document, so other classes could hold it but
    // not peek inside. (Here every class is nested in one file, so Java would
    // technically allow access - the caretaker just never does.)
    static final class Snapshot {
        private final String text;
        private final int cursor;

        private Snapshot(String text, int cursor) {
            this.text = text;
            this.cursor = cursor;
        }
    }

    // Caretaker: keeps snapshots, never inspects them.
    static class SaveHistory {
        private final Deque<Snapshot> saves = new ArrayDeque<>();

        void push(Snapshot s) { saves.push(s); }
        Snapshot pop() { return saves.pop(); }
    }

    public static void main(String[] args) {
        Document doc = new Document();
        SaveHistory history = new SaveHistory();

        doc.type("Dear Sam,");
        history.push(doc.save());
        System.out.println("Saved:    " + doc);

        doc.type(" I quit!");
        System.out.println("Oops:     " + doc);

        doc.restore(history.pop());
        System.out.println("Restored: " + doc);

        doc.type(" thanks for everything.");
        System.out.println("Final:    " + doc);
    }
}

/*
 * Try it:
 *  1. Save after every type() call, then restore twice in a row.
 */
