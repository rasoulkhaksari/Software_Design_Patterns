package designpattern.creational.builder;

import java.util.Scanner;

interface Engine {
    String model();
}

class SportEngine implements Engine {
    private String model;

    public SportEngine() {
        this.model = "Sport Engine";
    }

    @Override
    public String model() {
        return this.model;
    }
}

class SUVEngine implements Engine {
    private String model;

    public SUVEngine() {
        this.model = "SUV Engine";
    }

    @Override
    public String model() {
        return this.model;
    }
}

class Car {
    public String title = "Car";
}

class Manual {
    public String title = "Car manual";
}

interface Builder {
    void reset();

    void setTitle(String title);

    void setSeats(int count);

    void setEngine(Engine engine);

    void setTripComputer(boolean include);

    void setGPS(boolean include);
}

class CarBuilder implements Builder {
    private Car car;

    public CarBuilder() {
        this.reset();
    }

    @Override
    public void setTitle(String title) {
        this.car.title = title;
    }

    @Override
    public void reset() {
        this.car = new Car();
    }

    @Override
    public void setSeats(int count) {
        System.out.println("Set the " + count + " number of seats in the car.");
    }

    @Override
    public void setEngine(Engine engine) {
        System.out.println("Install a given engine: " + engine.model());
    }

    @Override
    public void setTripComputer(boolean include) {
        if (include) {
            System.out.println("Install a trip computer");
        } else {
            System.out.println("without a trip computer");
        }
    }

    @Override
    public void setGPS(boolean include) {
        if (include) {
            System.out.println("Install a global positioning system");
        } else {
            System.out.println("Without a GPS");
        }
    }

    public Car getProduct() {
        Car product = this.car;
        this.reset();
        return product;
    }
}

class CarManualBuilder implements Builder {
    private Manual manual;

    public CarManualBuilder() {
        this.reset();
    }

    @Override
    public void setTitle(String title) {
        this.manual.title = title + " Manual";
    }

    @Override
    public void reset() {
        this.manual = new Manual();
    }

    @Override
    public void setSeats(int count) {
        System.out.println("Document car seat features:" + count);
    }

    @Override
    public void setEngine(Engine engine) {
        System.out.println("Add engine instructions: " + engine.model());
    }

    @Override
    public void setTripComputer(boolean include) {
        if (include) {
            System.out.println("Add trip computer instructions");
        } else {
            System.out.println("Without a trip computer");
        }
    }

    @Override
    public void setGPS(boolean include) {
        if (include) {
            System.out.println("Add GPS instructions");
        } else {
            System.out.println("Without a GPS");
        }
    }

    public Manual getProduct() {
        Manual product = this.manual;
        this.reset();
        return product;
    }
}

class Director {
    private Builder builder;

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public void constructSportsCar(Builder builder) {
        builder.reset();
        builder.setTitle("Sport Car");
        builder.setSeats(2);
        builder.setEngine(new SportEngine());
        builder.setTripComputer(true);
        builder.setGPS(true);
    }

    public void constructSUV(Builder builder) {
        builder.reset();
        builder.setTitle("SUV Car");
        builder.setSeats(4);
        builder.setEngine(new SUVEngine());
        builder.setTripComputer(true);
        builder.setGPS(false);
    }
}

public class BuilderPattern {
    public static void makecar() {
        Director director = new Director();
        CarBuilder cbuilder = new CarBuilder();
        Scanner inputDevice = new Scanner(System.in);
        System.out.print("Which car do you want to build?(sport/suv):");
        if (inputDevice.next().equals("sport")) {
            director.constructSportsCar(cbuilder);
            Car car = cbuilder.getProduct();
            System.out.println(car.title + " has built.");

            CarManualBuilder mbuilder = new CarManualBuilder();
            director.constructSportsCar(mbuilder);
            Manual manual = mbuilder.getProduct();
            System.out.println(manual.title + " has built");
        } else {
            director.constructSUV(cbuilder);
            Car car = cbuilder.getProduct();
            System.out.println(car.title + " has built.");

            CarManualBuilder mbuilder = new CarManualBuilder();
            director.constructSUV(mbuilder);
            Manual manual = mbuilder.getProduct();
            System.out.println(manual.title + " has built");
        }
        inputDevice.close();
    }

    public static void demo() {
        makecar();
    }
}
