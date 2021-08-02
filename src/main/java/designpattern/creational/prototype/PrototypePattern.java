package designpattern.creational.prototype;

import java.util.ArrayList;
import java.util.List;

abstract class Shape {
    int X;
    int Y;
    String color;

    public Shape() {
    }

    public Shape(Shape source) {
        this.X = source.X;
        this.Y = source.Y;
        this.color = source.color;
    }

    public abstract Shape clone();

    public void render(){
        System.out.println("I am a " + this.getClass().getSimpleName() + " shape with color: " + this.color);
    }

}

class Rectangle extends Shape {
    int width;
    int height;

    public Rectangle() {
    }

    public Rectangle(Rectangle source) {
        super(source);
        this.width = source.width;
        this.height = source.height;
    }

    @Override
    public Shape clone() {
        return new Rectangle(this);
    }

}

class Circle extends Shape {
    int radius;

    public Circle() {
    }

    public Circle(Circle source) {
        super(source);
        this.radius = source.radius;
    }

    @Override
    public Shape clone() {
        return new Circle(this);
    }
}

public class PrototypePattern {

    private static List<Shape> shapes; // = new ArrayList<Shape>();

    private static void businessLogic() {
        List<Shape> shapesCopy = new ArrayList<Shape>();
        for (Shape shape : shapes) {
            shapesCopy.add(shape.clone());
        }
        for (Shape shape : shapesCopy) {
            shape.render();
        }
    }

    public static void demo() throws CloneNotSupportedException {
        shapes = new ArrayList<Shape>();
        Circle circle = new Circle();
        circle.X = 10;
        circle.Y = 10;
        circle.radius = 20;
        circle.color="Blue";
        shapes.add(circle);

        Circle anotherCircle = (Circle) circle.clone();
        anotherCircle.color="Red";
        shapes.add(anotherCircle);

        Rectangle rectangle = new Rectangle();
        rectangle.width = 10;
        rectangle.height = 20;
        rectangle.color="Black";
        shapes.add(rectangle);

        businessLogic();
        
    }
}
