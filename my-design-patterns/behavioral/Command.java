/*
 * COMMAND  (Behavioral)
 *
 * Problem:  You want to treat "an action" as a thing: store it, queue it,
 *           log it, or undo it. A plain method call disappears as soon as
 *           it runs.
 *
 * Idea:     Wrap each action in an object with `execute()` (and often
 *           `undo()`). The object remembers everything needed to run - and
 *           reverse - the action. Keep executed commands on a stack for undo.
 *
 * Analogy:  A restaurant order slip: the waiter writes it down, the kitchen
 *           queues it, and it can be cancelled before it's cooked.
 *
 * Use when: Undo/redo, job queues, macros, buttons/menu items that trigger
 *           actions. Java's Runnable is a command without undo.
 *
 * Run:      java behavioral/Command.java
 */
import java.util.ArrayDeque;
import java.util.Deque;

public class Command {

    // The receiver: the object that actually does the work.
    static class TextEditor {
        private final StringBuilder text = new StringBuilder();

        void insert(String s) { text.append(s); }
        void deleteLast(int count) { text.setLength(text.length() - count); }
        String text() { return text.toString(); }
    }

    interface EditorCommand {
        void execute();
        void undo();
    }

    static class TypeCommand implements EditorCommand {
        private final TextEditor editor;
        private final String typed;

        TypeCommand(TextEditor editor, String typed) {
            this.editor = editor;
            this.typed = typed;
        }

        public void execute() { editor.insert(typed); }
        public void undo() { editor.deleteLast(typed.length()); }
    }

    // The invoker: runs commands and keeps history. It knows nothing about text.
    static class History {
        private final Deque<EditorCommand> done = new ArrayDeque<>();

        void run(EditorCommand command) {
            command.execute();
            done.push(command);
        }

        void undo() {
            if (!done.isEmpty()) {
                done.pop().undo();
            }
        }
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        History history = new History();

        history.run(new TypeCommand(editor, "Hello"));
        history.run(new TypeCommand(editor, ", world"));
        history.run(new TypeCommand(editor, "!!!"));
        System.out.println("After typing:  \"" + editor.text() + "\"");

        history.undo();
        System.out.println("After 1 undo:  \"" + editor.text() + "\"");

        history.undo();
        System.out.println("After 2 undos: \"" + editor.text() + "\"");
    }
}

/*
 * Try it:
 *  1. Add redo: keep a second stack of undone commands.
 *  2. Add an UppercaseCommand. What does its undo() need to remember?
 */
