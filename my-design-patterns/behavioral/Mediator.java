/*
 * MEDIATOR  (Behavioral)
 *
 * Problem:  Many objects talk to each other directly. With 5 users each
 *           holding references to the other 4, every change ripples
 *           everywhere - a tangled web of dependencies.
 *
 * Idea:     Introduce a middle object that everyone talks to instead. Objects
 *           only know the mediator; the mediator knows who should hear what.
 *
 * Analogy:  An airport control tower: pilots don't radio every other plane,
 *           they talk to the tower, and the tower coordinates.
 *
 * Use when: Lots of objects interact in complex ways and you want to change
 *           those rules in one place (chat rooms, form fields that enable/
 *           disable each other, game lobbies).
 *
 * Run:      java behavioral/Mediator.java
 */
import java.util.ArrayList;
import java.util.List;

public class Mediator {

    // The mediator.
    static class ChatRoom {
        private final List<User> users = new ArrayList<>();

        void join(User user) {
            broadcast("system", user.name + " joined");
            users.add(user);
        }

        void send(User from, String message) {
            broadcast(from.name, message);
        }

        void sendPrivate(User from, String toName, String message) {
            for (User user : users) {
                if (user.name.equals(toName)) {
                    user.receive(from.name + " (private)", message);
                }
            }
        }

        private void broadcast(String fromName, String message) {
            for (User user : users) {
                if (!user.name.equals(fromName)) {
                    user.receive(fromName, message);
                }
            }
        }
    }

    // Colleagues: they only know the chat room, never each other.
    static class User {
        final String name;
        private final ChatRoom room;

        User(String name, ChatRoom room) {
            this.name = name;
            this.room = room;
            room.join(this);
        }

        void say(String message) { room.send(this, message); }
        void whisper(String to, String message) { room.sendPrivate(this, to, message); }

        void receive(String from, String message) {
            System.out.println("  [" + name + "'s screen] " + from + ": " + message);
        }
    }

    public static void main(String[] args) {
        ChatRoom room = new ChatRoom();
        User asha = new User("Asha", room);
        User ben = new User("Ben", room);
        new User("Chen", room);

        asha.say("Hi everyone!");
        ben.whisper("Asha", "Lunch at 1?");
    }
}

/*
 * Try it:
 *  1. Add a `mute(String name)` rule to ChatRoom. Users don't change at all.
 */
