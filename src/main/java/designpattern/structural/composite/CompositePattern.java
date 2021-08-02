package designpattern.structural.composite;

import java.util.ArrayList;
import java.util.List;

interface Graphic {
    void move(int x, int y);

    void draw();
}

class Dot implements Graphic {
    protected int x;
    protected int y;

    public Dot() {
    }

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void move(int x, int y) {
        System.out.println(String.format("moving Dot to X:%d and Y:%d", x, y));
        this.x += x;
        this.y += y;
    }

    @Override
    public void draw() {
        System.out.println(String.format("Drawing Dot at X:%d and Y:%d", this.x, this.y));
    }
}

class Circle extends Dot {
    private int radius;

    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out
                .println(String.format("Draw a circle at X:%d and Y:%d with radius R:%d", this.x, this.y, this.radius));
    }
}

class CompoundGraphic implements Graphic {
    List<Graphic> children;

    public CompoundGraphic() {
        children = new ArrayList<Graphic>();
    }

    public void add(Graphic child) {
        this.children.add(child);
        System.out.println(String.format("Add a %s to the array of children", child.getClass().getSimpleName()));
    }

    public void remove(Graphic child) {
        this.children.remove(child);
        System.out.println(String.format("Remove a %s from the array of children", child.getClass().getSimpleName()));
    }

    @Override
    public void move(int x, int y) {
        for (Graphic child : children) {
            child.move(x, y);
        }
    }

    @Override
    public void draw() {
        for (Graphic child : children) {
            child.draw();
        }
    }
}

class ImageEditor {
    private CompoundGraphic components;

    public ImageEditor() {
        System.out.println("new image editor started");
    }

    public void Load() {
        components = new CompoundGraphic();
        components.add(new Dot(1, 2));
        components.add(new Circle(5, 3, 10));
        components.draw();
    }
}

public class CompositePattern {
    public static void demo() {
        ImageEditor imgEditor = new ImageEditor();
        imgEditor.Load();
    }
}
