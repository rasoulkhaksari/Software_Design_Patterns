package designpattern.behavioral.observer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

class EventManager {
    private HashMap<String, EventListener> listeners;

    public EventManager() {
        this.listeners = new HashMap<>();
    }

    public void subscribe(String eventType, EventListener listener) {
        listeners.put(eventType, listener);
    }

    public void unsubscribe(String eventType, EventListener listener) {
        listeners.remove(eventType, listener);
    }

    public void notify(String eventType, String data) {
        listeners.forEach((evtType, listener) -> {
            if (evtType.equals(eventType)) {
                listener.update(data);
            }
        });
    }
}

class Editor {
    public EventManager events;
    private File file;

    public Editor() {
        events = new EventManager();
    }

    public void openFile(String path) {
        this.file = new File(path);
        events.notify("open", file.getName());
    }

    public void saveFile() {
        try {
            FileWriter writer = new FileWriter(this.file);
            writer.write("sample text");
            writer.close();
            events.notify("save", file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

interface EventListener {
    public void update(String filename);
}

class LoggingListener implements EventListener {
    private File log;
    private String message;

    public LoggingListener(String log_filename, String message) {
        this.log = new File(log_filename);
        this.message = message;
    }

    @Override
    public void update(String filename) {
        try {
            FileWriter writer = new FileWriter(this.log);
            String data = message.replace("%s", filename);
            System.out.println(String.format("LOG: %s", data));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class EmailAlertListener implements EventListener {
    private String email;
    private String message;

    public EmailAlertListener(String email, String message) {
        this.email = email;
        this.message = message;
    }

    @Override
    public void update(String filename) {
        System.out.println(
                String.format("Email Notification for: %s, message: %s", this.email, message.replace("%s", filename)));
    }
}

public class ObserverPattern {
    public static void demo() {
        Editor editor = new Editor();
        EventListener logger = new LoggingListener("log.txt", "Someone has opened the file: %s");
        editor.events.subscribe("open", logger);

        EventListener emailAlerts = new EmailAlertListener("admin@example.com", "Someone has changed the file: %s");
        editor.events.subscribe("save", emailAlerts);

        editor.openFile("data.csv");
        editor.saveFile();
    }
}
