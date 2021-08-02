package designpattern.behavioral.chain_of_responsibility;

import java.util.ArrayList;
import java.util.List;

interface ComponentWithContextualHelp {
    void showHelp();
}

abstract class Component implements ComponentWithContextualHelp {
    String tooltipText;
    protected Container container;

    @Override
    public void showHelp() {
        if (this.tooltipText != null) {
            System.out.println(String.format("tooltip: %s", this.tooltipText));
        } else {
            container.showHelp();
        }
    }
}

abstract class Container extends Component {
    protected List<Component> children = new ArrayList<Component>();

    public void add(Component child) {
        this.children.add(child);
        child.container = this;
    }
}

class Button extends Component {
    int x, y, width, height;
    String caption;

    public Button(int x, int y, int width, int height, String caption) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.caption = caption;
    }

}

class Panel extends Container {
    String modalHelpText;
    int x, y, width, height;

    public Panel(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void showHelp() {
        if (this.modalHelpText != null) {
            System.out.println("Show a modal window with the help text");
        } else {
            super.showHelp();
        }
    }
}

class Dialog extends Container {
    String wikiPageURL;
    String title;

    public Dialog(String title) {
        this.title = title;
    }

    @Override
    public void showHelp() {
        if (this.wikiPageURL != null) {
            System.out.println("Open the wiki help page");
        } else {
            super.showHelp();
        }
    }
}

public class ChainOfResponsibilityPattern {
    static Dialog dialog = new Dialog("Budget Reports");

    static Component getComponentAtMouseCoords() {
        return dialog.children.get(0);
    }

    static void createUI() {
        dialog.wikiPageURL = "http://...";
        Panel panel = new Panel(0, 0, 400, 800);
        panel.modalHelpText = "This panel does...";
        Button ok = new Button(250, 760, 50, 20, "OK");
        ok.tooltipText = "This is an OK button that...";
        Button cancel = new Button(320, 760, 50, 20, "Cancel");
        panel.add(ok);
        panel.add(cancel);
        dialog.add(panel);
    }

    static void onF1KeyPress() {
        Component component = getComponentAtMouseCoords();
        component.showHelp();
    }

    public static void demo() {
        createUI();
        onF1KeyPress();
    }
}
