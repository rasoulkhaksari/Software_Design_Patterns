package designpattern.behavioral.startegy;

import java.util.Scanner;

interface Strategy {
    int execute(int a, int b);
}

class ConcreteStrategyAdd implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a + b;
    }
}

class ConcreteStrategySubtract implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a - b;
    }
}

class ConcreteStrategyMultiply implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a * b;
    }
}

class Context {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int a, int b) {
        return strategy.execute(a, b);
    }
}

public class StrategyPattern {
    public static void demo() {
        Context context = new Context();
        Scanner inputDevice = new Scanner(System.in);
        System.out.println("Enter first number: ");
        String a = inputDevice.next();
        System.out.println("Enter second number: ");
        String b = inputDevice.next();
        System.out.println("Enter action(sum/subtract/multiply): ");
        String action = inputDevice.next();
        if (action.equals("sum")) {
            context.setStrategy(new ConcreteStrategyAdd());
        } else if (action.equals("subtract")) {
            context.setStrategy(new ConcreteStrategySubtract());
        } else {
            context.setStrategy(new ConcreteStrategyMultiply());
        }
        int Result = context.executeStrategy(Integer.parseInt(a), Integer.parseInt(b));
        System.out.println("Result=" + Result);
        inputDevice.close();
    }
}
