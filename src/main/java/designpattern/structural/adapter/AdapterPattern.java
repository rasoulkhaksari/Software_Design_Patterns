package designpattern.structural.adapter;

class RoundHole {
    private double radius;

    public RoundHole(double radius) {
        this.radius = radius;
        System.out.println("Round Hole build with radius: " + radius);
    }

    public double getRadius() {
        return this.radius;
    }

    public boolean fits(RoundPeg peg) {
        return this.getRadius() >= peg.getRadius();
    }
}

class RoundPeg {
    private double radius;

    public RoundPeg() {
    }

    public RoundPeg(double radius) {
        this.radius = radius;
        System.out.println("Round Peg build with radius: " + radius);
    }

    public double getRadius() {
        return this.radius;
    }
}

class SquarePeg {
    private double width;

    public SquarePeg(double width) {
        this.width = width;
        System.out.println("Square Peg build with width: " + width);
    }

    public double getWidth() {
        return this.width;
    }
}

class SquarePegAdapter extends RoundPeg {
    private SquarePeg peg;

    public SquarePegAdapter(SquarePeg peg) {
        // super(peg.getWidth());
        this.peg = peg;
    }

    public double getRadius() {
        return Math.round(peg.getWidth() * Math.sqrt(2) / 2);
    }
}

public class AdapterPattern {
    public static void demo() {
        RoundHole hole = new RoundHole(5);
        RoundPeg rpeg = new RoundPeg(5);
        System.out
                .println("hole " + hole.getRadius() + " and peg " + rpeg.getRadius() + " are fits?" + hole.fits(rpeg));

        SquarePeg small_sqpeg = new SquarePeg(5);
        SquarePeg large_sqpeg = new SquarePeg(10);
        // System.out.println("hole and peg are fits?" + hole.fits(small_sqpeg));
        // ERROR: incompatible types

        RoundPeg small_sqpeg_adapter = new SquarePegAdapter(small_sqpeg);
        RoundPeg large_sqpeg_adapter = new SquarePegAdapter(large_sqpeg);
        System.out.println("hole " + hole.getRadius() + " and peg " + small_sqpeg_adapter.getRadius() + " are fits?"
                + hole.fits(small_sqpeg_adapter));
        System.out.println("hole " + hole.getRadius() + " and peg " + large_sqpeg_adapter.getRadius() + " are fits?"
                + hole.fits(large_sqpeg_adapter));

    }
}
