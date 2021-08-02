package designpattern.behavioral.memento;

class Editor {
    private int curX;
    private int curY;
    private String text;
    private int selectionWidth;

    public Editor(){
        System.out.println("New Editor created.");
    }

    public void setText(String text) {
        this.text = text;
        System.out.println(String.format("set new text on editor: '%s'", text));
    }

    public void setCursor(int x, int y) {
        this.curX = x;
        this.curY = y;
        System.out.println(String.format("set new cursor on editor X:%d , Y:%d", x,y));
    }

    public void setSelectionWidth(int width) {
        this.selectionWidth = width;
    }

    public Snapshot createSnapshot() {
        return new Snapshot(this, text, curX, curY, selectionWidth);
    }
}

class Snapshot {
    private Editor editor;
    private int curX;
    private int curY;
    private String text;
    private int selectionWidth;

    public Snapshot(Editor editor, String text, int curX, int curY, int selectionWidth) {
        this.editor = editor;
        this.text = text;
        this.curX = curX;
        this.curY = curY;
        this.selectionWidth = selectionWidth;
    }

    public void restore() {
        editor.setText(text);
        editor.setCursor(curX, curY);
        editor.setSelectionWidth(selectionWidth);
    }
}

class Command {
    private Editor editor;
    private Snapshot backup;

    public Command(Editor editor) {
        this.editor = editor;
    }

    public void makeBackup() {
        backup = this.editor.createSnapshot();
        System.out.println("make a backup for editor!");
    }

    public void undo() {
        if (backup != null) {
            backup.restore();
            System.out.println("restore editor backup!");
        }
    }
}

public class MementoPattern {
    public static void demo(){
        Editor editor = new Editor();
        Command cmd=new Command(editor);
        editor.setText("sample text");
        editor.setCursor(10, 23);
        cmd.makeBackup();
        cmd.undo();
    }
}
