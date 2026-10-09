/*
 * BRIDGE  (Structural)
 *
 * Problem:  A class varies in two independent ways. Remotes (basic,
 *           advanced) x devices (TV, radio) as subclasses gives
 *           BasicTvRemote, AdvancedTvRemote, BasicRadioRemote... and the
 *           count multiplies with every new remote or device.
 *
 * Idea:     Split the two dimensions into two hierarchies and connect them
 *           with a field (the "bridge"): a Remote HAS-A Device. Each side can
 *           grow on its own: 2 remotes + 2 devices = 4 classes, not 4 combos
 *           that become 9, 16, ...
 *
 * Analogy:  Any universal remote works with any TV brand, because they meet
 *           at an agreed set of signals.
 *
 * Use when: You see a class explosion from combining two "kinds of" things.
 *
 * Run:      java structural/Bridge.java
 */
public class Bridge {

    // Implementation side: the devices.
    interface Device {
        String name();
        boolean isOn();
        void setPower(boolean on);
        int volume();
        void setVolume(int volume);
    }

    static class Tv implements Device {
        private boolean on;
        private int volume = 20;
        public String name() { return "TV"; }
        public boolean isOn() { return on; }
        public void setPower(boolean on) { this.on = on; }
        public int volume() { return volume; }
        public void setVolume(int volume) { this.volume = volume; }
    }

    static class Radio implements Device {
        private boolean on;
        private int volume = 50;
        public String name() { return "Radio"; }
        public boolean isOn() { return on; }
        public void setPower(boolean on) { this.on = on; }
        public int volume() { return volume; }
        public void setVolume(int volume) { this.volume = volume; }
    }

    // Abstraction side: the remotes. The `device` field IS the bridge.
    static class Remote {
        protected final Device device;

        Remote(Device device) {
            this.device = device;
        }

        void togglePower() {
            device.setPower(!device.isOn());
            System.out.println("  " + device.name() + " is now " + (device.isOn() ? "ON" : "OFF"));
        }

        void volumeUp() {
            device.setVolume(Math.min(100, device.volume() + 10));
            System.out.println("  " + device.name() + " volume " + device.volume());
        }
    }

    // Extending the abstraction doesn't touch any device class.
    static class AdvancedRemote extends Remote {
        AdvancedRemote(Device device) {
            super(device);
        }

        void mute() {
            device.setVolume(0);
            System.out.println("  " + device.name() + " muted");
        }
    }

    public static void main(String[] args) {
        System.out.println("Basic remote + TV:");
        Remote basic = new Remote(new Tv());
        basic.togglePower();
        basic.volumeUp();

        System.out.println("Advanced remote + Radio:");
        AdvancedRemote advanced = new AdvancedRemote(new Radio());
        advanced.togglePower();
        advanced.volumeUp();
        advanced.mute();
    }
}

/*
 * Try it:
 *  1. Add a Speaker device. It works with BOTH remotes immediately.
 *  2. Add a VoiceRemote with `say(String command)`. It works with every device.
 */
