package designpattern.behavioral.visitor;

interface Shape {
    void move(int x, int y);

    void draw();

    void accept(Visitor v);
}

interface Visitor {
    void visitDot(Dot d);

    void visitCircle(Circle c);

    void visitRectangle(Rectangle r);

    void visitCompoundShape(CompoundShape cs);
}

class Dot implements Shape {
    @Override
    public void accept(Visitor v) {
        v.visitDot(this);
    }

    @Override
    public void draw() {
        System.out.println("Drawing Dot...");
    }

    @Override
    public void move(int x, int y) {
        System.out.println(String.format("moving Dot to x:%d, y:%d", x, y));
    }
}

class Circle implements Shape {
    @Override
    public void accept(Visitor v) {
        v.visitCircle(this);
    }

    @Override
    public void draw() {
        System.out.println("Drawing Circle...");
    }

    @Override
    public void move(int x, int y) {
        System.out.println(String.format("moving Circle to x:%d, y:%d", x, y));
    }
}

class Rectangle implements Shape {
    @Override
    public void accept(Visitor v) {
        v.visitRectangle(this);
    }

    @Override
    public void draw() {
        System.out.println("Drawing Rectangle...");
    }

    @Override
    public void move(int x, int y) {
        System.out.println(String.format("moving Rectangle to x:%d, y:%d", x, y));
    }
}

class CompoundShape implements Shape {
    @Override
    public void accept(Visitor v) {
        v.visitCompoundShape(this);
    }

    @Override
    public void draw() {
        System.out.println("Drawing CompoundShape...");
    }

    @Override
    public void move(int x, int y) {
        System.out.println(String.format("moving CompoundShape to x:%d, y:%d", x, y));
    }
}

class XMLExportVisitor implements Visitor {
    @Override
    public void visitDot(Dot d) {
        System.out.println("Export the dot's ID and center coordinates.");
    }

    @Override
    public void visitCircle(Circle c) {
        System.out.println("Export the circle's ID, center coordinates and radius");
    }

    @Override
    public void visitRectangle(Rectangle r) {
        System.out.println("Export the rectangle's ID, left-top coordinates, width and height.");
    }

    @Override
    public void visitCompoundShape(CompoundShape cs) {
        System.out.println("Export the shape's ID as well as the list of its children's IDs.");
    }

    public void Export(Dot d, Circle c, Rectangle r, CompoundShape cs) {
        d.accept(this);
        c.accept(this);
        r.accept(this);
        cs.accept(this);
    }
}

public class VisitorPattern {
    public static void demo() {
        XMLExportVisitor visitor = new XMLExportVisitor();
        Dot dot = new Dot();
        Circle circle = new Circle();
        Rectangle rectangle = new Rectangle();
        CompoundShape compoundShape = new CompoundShape();
        visitor.Export(dot, circle, rectangle, compoundShape);
    }
}
