package designpattern.behavioral.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

abstract class Command {
    protected Application app;
    protected Editor editor;
    protected String backup;

    public Command() {
    }

    public Command(Application app, Editor editor) {
        this.app = app;
        this.editor = editor;
    }

    public void saveBackup() {
        this.backup = this.editor.text;
    }

    public void undo() {
        this.editor.text = this.backup;
    }

    abstract boolean execute();
}

class CopyCommand extends Command {
    public CopyCommand(Application app, Editor editor) {
        super(app, editor);
    }

    @Override
    boolean execute() {
        this.app.clipboard = this.editor.getSelection();
        return false;
    }
}

class CutCommand extends Command {
    public CutCommand(Application app, Editor editor) {
        super(app, editor);
    }
    @Override
    boolean execute() {
        saveBackup();
        app.clipboard = editor.getSelection();
        editor.deleteSelection();
        return true;
    }
}

class PasteCommand extends Command {
    public PasteCommand(Application app, Editor editor) {
        super(app, editor);
    }

    @Override
    boolean execute() {
        saveBackup();
        editor.replaceSelection(app.clipboard);
        return true;
    }
}

class UndoCommand extends Command {
    public UndoCommand(Application app, Editor editor) {
        super(app, editor);
    }

    @Override
    boolean execute() {
        app.undo();
        return false;
    }
}

class CommandHistory {
    Stack<Command> history;

    public CommandHistory() {
        this.history = new Stack<Command>();
    }

    public void push(Command c) {
        history.push(c);
        System.out.println("Push the command to the end of the history array");
    }

    public Command pop() {
        return history.pop();
    }
}

class Editor {
    String text;

    public String getSelection() {
        return "selected text returned";
    }

    public void deleteSelection() {
        System.out.println("selected text deleted");
    }

    public void replaceSelection(String text) {
        System.out.println("Insert the clipboard's contents at the current position");
    }
}

class Button {
    public void setCommand(Runnable handler) {
        handler.run();
    }
}

abstract class shortcuts {
    public static void onKeyPress(String shortkey, Runnable handler,String command) {
        System.out.println(String.format("Binding shortkey: %s to handler: %s", shortkey, command));
    }
}

class Application {
    String clipboard;
    List<Editor> editors = new ArrayList<Editor>();
    Editor activeEditor;
    CommandHistory history;

    public Application(){
        System.out.println("Create Application");
        this.activeEditor=new Editor();
        this.history=new CommandHistory();
    }

    public void createUI() {
        System.out.println("Create Application User Interface");
        Runnable copy = () -> {
            executeCommand(new CopyCommand(this, activeEditor));
        };
        Button copyButton = new Button();
        copyButton.setCommand(copy);
        shortcuts.onKeyPress("Ctrl+C", copy,"CopyCommand");

        Runnable cut = () -> {
            executeCommand(new CutCommand(this, activeEditor));
        };
        Button cutButton = new Button();
        cutButton.setCommand(cut);
        shortcuts.onKeyPress("Ctrl+X", cut,"CutCommand");

        Runnable paste = () -> {
            executeCommand(new PasteCommand(this, activeEditor));
        };
        Button pasteButton = new Button();
        pasteButton.setCommand(paste);
        shortcuts.onKeyPress("Ctrl+V", paste,"PasteCommand");

        Runnable undo = () -> {
            executeCommand(new UndoCommand(this, activeEditor));
        };
        Button undoButton = new Button();
        undoButton.setCommand(cut);
        shortcuts.onKeyPress("Ctrl+Z", undo,"UndoCommand");

    }

    public void executeCommand(Command command) {
        if (command.execute()) {
            history.push(command);
        }
    }

    public void undo() {
        Command command = history.pop();
        if (command != null) {
            command.undo();
        }
    }

}

public class CommandPattern {
    public static void demo(){
        Application app = new Application();
        app.createUI();
    }
}
