package designpattern.creational.abstractfactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

interface Button {
    void paint();
}

class WinButton implements Button {
    @Override
    public void paint() {
        System.out.println("Render a button in Windows style.");
    }
}

class MacButton implements Button {
    @Override
    public void paint() {
        System.out.println("Render a button in MacOS style.");
    }
}

interface Checkbox {
    void paint();
}

class WinCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Render a checkbox in Windows style.");
    }
}

class MacCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Render a checkbox in MacOS style.");
    }
}

interface GUIFactory {
    Button createButton();

    Checkbox createCheckbox();
}

class WinFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WinButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WinCheckbox();
    }

}

class MacFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}

class Application {
    private Button button;
    private GUIFactory factory;

    public Application(GUIFactory factory) {
        this.factory = factory;
    }

    public void createUI() {
        this.button = this.factory.createButton();
    }

    public void paint() {
        this.button.paint();
    }

}

public class AbstractFactoryPattern {

    private static Map<String, String> readApplicationConfigFile() {
        Map<String, String> config = new HashMap<String, String>();
        Scanner inputDevice = new Scanner(System.in);
        System.out.print("Which OS do you have?(win/mac):");
        config.put("OS", inputDevice.next());
        inputDevice.close();
        return config;
    }

    public static void demo() {
        System.out.println("Create Application User Interface based on Operating System Type");
        Map<String, String> config = readApplicationConfigFile();
        GUIFactory factory = config.get("OS").equals("win") ? new WinFactory() : new MacFactory();
        Application app = new Application(factory);
        app.createUI();
        app.paint(); 
    }
}
