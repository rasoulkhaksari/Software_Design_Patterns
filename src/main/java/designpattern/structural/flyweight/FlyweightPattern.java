package designpattern.structural.flyweight;

import java.util.ArrayList;
import java.util.List;

class TreeType {
    private String name;
    private String color;
    private String texture;

    public TreeType(String name, String color, String texture) {
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    public void draw(Object convas, int X, int Y) {
        System.out.println("1. Create a bitmap of a given type, color & texture.");
        System.out.println(String.format("2. Draw the bitmap on the canvas at X:%d and Y:%d coords.", X, Y));
    }

    public boolean equals(String name, String color, String texture) {
        return this.name == name && this.color == color && this.texture == texture;
    }
}

class TreeFactory {
    static List<TreeType> treeTypes=new ArrayList<TreeType>();

    static TreeType getTreeType(String name, String color, String texture) {
        TreeType type = treeTypes.stream().filter(t -> t.equals(name, color, texture)).findFirst().orElseGet(() -> {
            TreeType t = new TreeType(name, color, texture);
            treeTypes.add(t);
            return t;
        });
        return type;
    }
}

class Tree {
    int X, Y;
    TreeType type;

    public Tree(int x, int y, TreeType type) {
        this.X = x;
        this.Y = y;
        this.type = type;
    }

    public void draw(Object convas) {
        this.type.draw(convas, X, Y);
    }
}

class Forest {
    List<Tree> trees;

    public Forest() {
        this.trees = new ArrayList<Tree>();
    }

    public void plantTree(int x, int y, String name, String color, String texture) {
        TreeType type = TreeFactory.getTreeType(name, color, texture);
        Tree tree = new Tree(x, y, type);
        this.trees.add(tree);
    }

    public void draw(Object convas) {
        for (Tree tree : trees) {
            tree.draw(convas);
        }
    }
}

public class FlyweightPattern {
    public static void demo() {
        Forest forest = new Forest();
        forest.plantTree(1, 2, "Afra", "Green", "Horizontal");
        forest.plantTree(12, 24, "Beed", "Blue", "Diagonal");
        forest.draw(new Object());
    }
}
