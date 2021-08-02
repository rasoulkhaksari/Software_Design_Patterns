package designpattern;

import java.util.Scanner;

import designpattern.creational.abstractfactory.AbstractFactoryPattern;
import designpattern.creational.factory.FactoryPattern;
import designpattern.creational.prototype.PrototypePattern;
import designpattern.creational.builder.BuilderPattern;
import designpattern.creational.singleton.SingletonPattern;
import designpattern.structural.adapter.AdapterPattern;
import designpattern.structural.bridge.BridgePattern;
import designpattern.structural.composite.CompositePattern;
import designpattern.structural.decorator.DecoratorPattern;
import designpattern.structural.facade.FacadePattern;
import designpattern.structural.flyweight.FlyweightPattern;
import designpattern.structural.proxy.ProxyPattern;
import designpattern.behavioral.chain_of_responsibility.ChainOfResponsibilityPattern;
import designpattern.behavioral.command.CommandPattern;
import designpattern.behavioral.iterator.IteratorPattern;
import designpattern.behavioral.mediator.MediatorPattern;
import designpattern.behavioral.memento.MementoPattern;
import designpattern.behavioral.observer.ObserverPattern;
import designpattern.behavioral.state.StatePattern;
import designpattern.behavioral.startegy.StrategyPattern;
import designpattern.behavioral.template.TemplatePattern;
import designpattern.behavioral.visitor.VisitorPattern;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner inputDevice = new Scanner(System.in);
        System.out.println(String.format("%s Design Pattern Category %s", "=".repeat(7), "=".repeat(7)));
        System.out.println("Creational");
        System.out.println("  1. Factory");
        System.out.println("  2. Abstract Factory");
        System.out.println("  3. Builder");
        System.out.println("  4. Prototype");
        System.out.println("  5. Singleton");
        System.out.println("Structural");
        System.out.println("  6. Adapter");
        System.out.println("  7. Bridge");
        System.out.println("  8. Composite");
        System.out.println("  9. Decorator");
        System.out.println("  10. Facade");
        System.out.println("  11. Flyweight");
        System.out.println("  12. Proxy");
        System.out.println("Behavioral");
        System.out.println("  13. Chain of Responsibility");
        System.out.println("  14. Command");
        System.out.println("  15. Iterator");
        System.out.println("  16. Mediator");
        System.out.println("  17. Memento");
        System.out.println("  18. Observer");
        System.out.println("  19. State");
        System.out.println("  20. Strategy");
        System.out.println("  21. Template Method");
        System.out.println("  22. Visitor");
        System.out.println(String.format("%s Design Pattern Category %s", "=".repeat(7), "=".repeat(7)));
        System.out.print("Select Category: ");
        int Category = inputDevice.nextInt();
        switch (Category) {
            case 1:
                FactoryPattern.demo();
                break;
            case 2:
                AbstractFactoryPattern.demo();
                break;
            case 3:
                BuilderPattern.demo();
                break;
            case 4:
                PrototypePattern.demo();
                break;
            case 5:
                SingletonPattern.demo();
                break;
            case 6:
                AdapterPattern.demo();
                break;
            case 7:
                BridgePattern.demo();
                break;
            case 8:
                CompositePattern.demo();
                break;
            case 9:
                DecoratorPattern.demo();
                break;
            case 10:
                FacadePattern.demo();
                break;
            case 11:
                FlyweightPattern.demo();
                break;
            case 12:
                ProxyPattern.demo();
                break;
            case 13:
                ChainOfResponsibilityPattern.demo();
                break;
            case 14:
                CommandPattern.demo();
                break;
            case 15:
                IteratorPattern.demo();
                break;
            case 16:
                MediatorPattern.demo();
                break;
            case 17:
                MementoPattern.demo();
                break;
            case 18:
                ObserverPattern.demo();
                break;
            case 19:
                StatePattern.demo();
                break;
            case 20:
                StrategyPattern.demo();
                break;
            case 21:
                TemplatePattern.demo();
                break;
            case 22:
                VisitorPattern.demo();
                break;
        }
        inputDevice.close();
    }
}
