package designpattern.creational.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
// import java.util.function.Function;
// import java.util.function.Supplier;


// interface Animal {
//     void speak();
//     void preferredAction();
// }
 
// class Dog implements Animal {
//     public void speak() {
//         System.out.println("Dog says: Bow-Wow.");        
//     }
//     public void preferredAction() {
//         System.out.println("Dogs prefer barking...");        
//     }
// }

// class Tiger implements Animal{
//     public void speak() {
//         System.out.println("Tiger says: Halum.");
//     }
//     public void preferredAction() {
//         System.out.println("Tigers prefer hunting...");
//     }
// }

// abstract class AnimalFactory{
//     public abstract Animal createAnimal();
// }

// class DogFactory extends AnimalFactory{
//     public Animal createAnimal(){
//         return new Dog();
//     }
// }

// class TigerFactory extends AnimalFactory{
//     public Animal createAnimal(){
//         return new Tiger();
//     }
// }

interface Button{
    // public static String title="";
    String getTitle();
    void render();
    // void onClick(Function<Button,Void> handler);
    void onClick(Consumer<Button> handler);
}

class WindowsButton implements Button{
    String title;
    @Override
    public String getTitle() {
        return this.title;
    }
    public WindowsButton(){
        this.title="Windows Button";
    }

    @Override
    public void render() {
        System.out.println("Render a button in Windows style.");
    }
    @Override
    public void onClick(Consumer<Button> handler) {
        // public void onClick(Function<Button, Void> handler) {
        // handler.apply(this);
        handler.accept(this);
    }
}

class HTMLButton implements Button{
    String title;
    @Override
    public String getTitle() {
        return this.title;
    }
    public HTMLButton(){
        this.title="HTML Button";
    }
    @Override
    public void render() {
        System.out.println("Render an HTML representation of a button.");
    }
    @Override
    public void onClick(Consumer<Button> handler) {
        handler.accept(this);
    }
}

abstract class Dialog{
    abstract Button createButton();
    void show(){
        System.out.println("Dialog Opened.");
        Button okButton=this.createButton();
        okButton.render(); 
        Consumer<Button> closeHandler = btn -> System.out.println("Dialog closed by: "+btn.getTitle()); 
        okButton.onClick(closeHandler);
    }
}

class WindowsDialog extends Dialog{
    @Override
    Button createButton() {
        return new WindowsButton();
    }
}

class WebDialog extends Dialog{
    @Override
    Button createButton() {
        return new HTMLButton();
    }
}


class Application {
    private Dialog dialog;

    public Application(Dialog dialog) {
        this.dialog = dialog;
    }

    public void showDialog() {
        this.dialog.show();
    }

}

public class FactoryPattern{
    // public static void demo(){
    //     System.out.println(String.format("\n%s  Factory Pattern Demo %s", "𑁋".repeat(7), "𑁋".repeat(7)));
    //     AnimalFactory tigerFactory=new TigerFactory();
    //     Animal aTiger =tigerFactory.createAnimal();
    //     aTiger.speak();
    //     aTiger.preferredAction();
    //     System.out.println();
    //     AnimalFactory dogFactory=new DogFactory();
    //     Animal aDog = dogFactory.createAnimal();
    //     aDog.speak();
    //     aDog.preferredAction(); 
    //     System.out.println(String.format("%s  Factory Pattern Demo %s\n", "𑁋".repeat(7), "𑁋".repeat(7)));
    // }

    private static Map<String, String> readApplicationConfigFile() {
        Map<String, String> config = new HashMap<String, String>();
        Scanner inputDevice = new Scanner(System.in);
        System.out.print("Which Environment do you have?(win/web):");
        config.put("ENV", inputDevice.next());
        inputDevice.close();
        return config;
    }

    public static void demo() {
        System.out.println("Create Windows or Web DialogBox");
        Map<String, String> config = readApplicationConfigFile();
        Dialog dialog = config.get("ENV").equals("win") ? new WindowsDialog() : new WebDialog();
        Application app = new Application(dialog);
        app.showDialog();
    }

}


