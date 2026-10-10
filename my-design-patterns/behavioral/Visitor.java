/*
 * VISITOR  (Behavioral)
 *
 * Problem:  You have a stable set of classes (Circle, Rectangle, ...) and
 *           keep adding new operations on them: area, export to JSON, draw,
 *           print... Adding each as a method to every shape bloats them.
 *
 * Idea:     Move each operation into its own "visitor" class with one
 *           visit-method per element type. Each element has a single
 *           `accept(visitor)` method that calls `visitor.visit(this)` - which
 *           lets Java pick the right overload for the real type ("double
 *           dispatch").
 *
 * Analogy:  An insurance agent visiting a street: the same agent offers a
 *           different policy at a house, a shop or a factory.
 *
 * Use when: The element classes rarely change, but new operations are added
 *           often (compilers walking syntax trees are the classic case).
 * Trade-off: adding a new element type means updating every visitor.
 *
 * Run:      java behavioral/Visitor.java
 */
import java.util.List;

public class Visitor {

    // The visitor: one method per element type. Generic so visitors can return anything.
    interface ShapeVisitor<R> {
        R visitCircle(Circle c);
        R visitRectangle(Rectangle r);
    }

    interface Shape {
        <R> R accept(ShapeVisitor<R> visitor);
    }

    static class Circle implements Shape {
        final double radius;
        Circle(double radius) { this.radius = radius; }
        public <R> R accept(ShapeVisitor<R> v) { return v.visitCircle(this); }
    }

    static class Rectangle implements Shape {
        final double width;
        final double height;
        Rectangle(double width, double height) { this.width = width; this.height = height; }
        public <R> R accept(ShapeVisitor<R> v) { return v.visitRectangle(this); }
    }

    // Operation 1: area.
    static class AreaVisitor implements ShapeVisitor<Double> {
        public Double visitCircle(Circle c) { return Math.PI * c.radius * c.radius; }
        public Double visitRectangle(Rectangle r) { return r.width * r.height; }
    }

    // Operation 2: JSON export. Added without touching Circle or Rectangle.
    static class JsonVisitor implements ShapeVisitor<String> {
        public String visitCircle(Circle c) {
            return "{\"type\":\"circle\",\"radius\":" + c.radius + "}";
        }

        public String visitRectangle(Rectangle r) {
            return "{\"type\":\"rectangle\",\"width\":" + r.width + ",\"height\":" + r.height + "}";
        }
    }

    public static void main(String[] args) {
        List<Shape> shapes = List.of(new Circle(1), new Rectangle(3, 4), new Circle(2.5));

        AreaVisitor area = new AreaVisitor();
        JsonVisitor json = new JsonVisitor();

        for (Shape shape : shapes) {
            System.out.printf("  area=%6.2f  json=%s%n", shape.accept(area), shape.accept(json));
        }
    }
}

/*
 * Try it:
 *  1. Add a PerimeterVisitor - no shape classes change.
 *  2. Add a Triangle shape - now every visitor must change. That's the trade-off.
 */
