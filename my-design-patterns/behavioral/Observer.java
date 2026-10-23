/*
 * OBSERVER  (Behavioral)
 *
 * Problem:  When one object changes, others need to know - but the object
 *           shouldn't hard-code who they are. Polling ("has it changed yet?")
 *           is wasteful.
 *
 * Idea:     The subject keeps a list of subscribers. They register
 *           themselves; when something happens the subject loops over the
 *           list and notifies each one. Subscribers can come and go anytime.
 *
 * Analogy:  Subscribing to a YouTube channel: you get notified about new
 *           videos; unsubscribe and the notifications stop.
 *
 * Use when: One-to-many notifications: UI event listeners, stock tickers,
 *           "send email AND update dashboard when an order is placed".
 *
 * Run:      java behavioral/Observer.java
 */
import java.util.ArrayList;
import java.util.List;

public class Observer {

    // The observer interface. One method, so lambdas can be subscribers too.
    interface Subscriber {
        void onNewVideo(String channel, String title);
    }

    // The subject.
    static class Channel {
        private final String name;
        private final List<Subscriber> subscribers = new ArrayList<>();

        Channel(String name) {
            this.name = name;
        }

        void subscribe(Subscriber s) { subscribers.add(s); }
        void unsubscribe(Subscriber s) { subscribers.remove(s); }

        void upload(String title) {
            System.out.println(name + " uploaded \"" + title + "\"");
            if (title.startsWith("[private]")) {
                System.out.println("  (private - no notifications sent)");
                return;
            }
            for (Subscriber s : subscribers) {
                s.onNewVideo(name, title);
            }
        }
    }

    // A concrete observer as a class.
    static class EmailNotifier implements Subscriber {
        private final String email;

        EmailNotifier(String email) {
            this.email = email;
        }

        public void onNewVideo(String channel, String title) {
            System.out.println("  email to " + email + ": new video on " + channel + " - " + title);
        }
    }

   static class VideoCounter implements Subscriber {
        private int count = 0;
    
        public void onNewVideo(String channel, String title) {
            count++;
    }
    
    public int getCount() {
        return count;
    }

   }
    public static void main(String[] args) {
        Channel channel = new Channel("JavaCafe");

        Subscriber asha = new EmailNotifier("asha@example.com");
        Subscriber phonePush = (ch, title) -> System.out.println("  push notification: " + title);

        channel.subscribe(asha);
        channel.subscribe(phonePush);
        channel.upload("Design Patterns in 10 minutes");

        //channel.unsubscribe(asha);
        channel.upload("[private]Observer pattern deep dive");
    }
}

