/*
 * COMPOSITE  (Structural)
 *
 * Problem:  You have a tree: folders contain files AND other folders. Code
 *           full of `if (item is a folder) ... else ...` gets messy fast.
 *
 * Idea:     Give single items (leaves) and containers (composites) the same
 *           interface. A container implements each method by delegating to
 *           its children. Client code treats one file and a whole folder
 *           tree exactly the same way.
 *
 * Analogy:  An army: an order given to a division is passed to brigades,
 *           then platoons, then soldiers. The general doesn't care how deep.
 *
 * Use when: Your data is a part-whole hierarchy (files, UI widgets inside
 *           panels, menu items inside submenus, org charts).
 *
 * Run:      java structural/Composite.java
 */
import java.util.ArrayList;
import java.util.List;

public class Composite {

    // The common interface for leaves and containers.
    interface FileSystemItem {
        String name();
        long size();
        void print(String indent);
    }

    // Leaf: has no children.
    static class FileItem implements FileSystemItem {
        private final String name;
        private final long size;

        FileItem(String name, long size) {
            this.name = name;
            this.size = size;
        }

        public String name() { return name; }
        public long size() { return size; }

        public void print(String indent) {
            System.out.println(indent + "- " + name + " (" + size + " KB)");
        }
    }

    // Composite: holds children, which may be files or folders.
    static class Folder implements FileSystemItem {
        private final String name;
        private final List<FileSystemItem> children = new ArrayList<>();

        Folder(String name) {
            this.name = name;
        }

        Folder add(FileSystemItem item) {
            children.add(item);
            return this;
        }

        public String name() { return name; }

        // Delegates to children - recursion does the work for any depth.
        public long size() {
            long total = 0;
            for (FileSystemItem child : children) {
                total += child.size();
            }
            return total;
        }

        public void print(String indent) {
            System.out.println(indent + "+ " + name + "/ (" + size() + " KB)");
            for (FileSystemItem child : children) {
                child.print(indent + "    ");
            }
        }
    }

    public static void main(String[] args) {
        Folder photos = new Folder("photos")
                .add(new FileItem("beach.jpg", 2400))
                .add(new FileItem("mountain.jpg", 3100));

        Folder documents = new Folder("documents")
                .add(new FileItem("resume.pdf", 120))
                .add(new Folder("taxes").add(new FileItem("2025.pdf", 300)));

        Folder home = new Folder("home").add(photos).add(documents).add(new FileItem("notes.txt", 4));

        home.print("");

        // Same call on a leaf and on a whole tree:
        FileSystemItem[] items = { new FileItem("notes.txt", 4), home };
        for (FileSystemItem item : items) {
            System.out.println(item.name() + " -> " + item.size() + " KB");
        }
    }
}

/*
 * Try it:
 *  1. Add `int countFiles()` to the interface and implement it in both classes.
 */
