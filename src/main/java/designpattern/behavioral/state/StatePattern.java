package designpattern.behavioral.state;

class Button {
    public void onClick(Runnable handler) {
        handler.run();
    }
}

class UserInterface {
    Button lockButton;
    Button playButton;
    Button nextButton;
    Button prevButton;
}

class AudioPlayer {
    State state;
    UserInterface UI;
    int volume;
    String[] playlist;
    String currentSong;

    public AudioPlayer() {
        System.out.println("create new Audio Player");
        this.state = new ReadyState(this);
        UI = new UserInterface();
        UI.lockButton = new Button();
        UI.playButton = new Button();
        UI.nextButton = new Button();
        UI.prevButton = new Button();

        UI.lockButton.onClick(() -> {
            this.clickLock();
        });
        UI.playButton.onClick(() -> {
            this.clickPlay();
        });
        UI.nextButton.onClick(() -> {
            this.clickNext();
        });
        UI.prevButton.onClick(() -> {
            this.clickPrevious();
        });

    }

    public void changeState(State state) {
        System.out.println(String.format("state changed from: %s to: %s", this.state.getClass().getSimpleName(),
                state.getClass().getSimpleName()));
        this.state = state;

    }

    public void clickLock() {
        state.clickLock();
        // System.out.println("Lock Button clicked");
    }

    public void clickPlay() {
        state.clickPlay();
    }

    public void clickNext() {
        state.clickNext();
    }

    public void clickPrevious() {
        state.clickPrevious();
    }

    public void startPlayback() {
        System.out.println("start Playback");
    }

    public void stopPlayback() {
        System.out.println("stop Playback");
    }

    public void nextSong() {
        System.out.println("goto next song");
    }

    public void previousSong() {
        System.out.println("goto previous song");
    }

    public void fastForward(int time) {
        System.out.println(String.format("fast forward to next %d second", time));
    }

    public void rewind(int time) {
        System.out.println(String.format("rewind to previous %d time", time));
    }

}

class ClickEvent {
    boolean doubleclick;
}

abstract class State {
    protected AudioPlayer player;
    protected ClickEvent clkEvent;

    public State(AudioPlayer player) {
        this.player = player;
    }

    abstract void clickLock();

    abstract void clickPlay();

    abstract void clickNext();

    abstract void clickPrevious();
}

class LockedState extends State {
    public LockedState(AudioPlayer player) {
        super(player);
    }

    @Override
    void clickLock() {
        if (player.state instanceof PlayingState) {
            // if (player.playing) {
            player.changeState(new PlayingState(player));
        } else {
            player.changeState(new ReadyState(player));
        }
    }

    @Override
    void clickPlay() {
        // Locked, so do nothing
    }

    @Override
    void clickNext() {
        // Locked, so do nothing
    }

    @Override
    void clickPrevious() {
        // Locked, so do nothing
    }
}

class ReadyState extends State {
    public ReadyState(AudioPlayer player) {
        super(player);
    }

    @Override
    void clickLock() {
        player.changeState(new LockedState(player));
    }

    @Override
    void clickPlay() {
        player.startPlayback();
        player.changeState(new PlayingState(player));
    }

    @Override
    void clickNext() {
        player.nextSong();
    }

    @Override
    void clickPrevious() {
        player.previousSong();
    }
}

class PlayingState extends State {
    public PlayingState(AudioPlayer player) {
        super(player);
    }

    @Override
    void clickLock() {
        player.changeState(new LockedState(player));
    }

    @Override
    void clickPlay() {
        player.stopPlayback();
        player.changeState(new ReadyState(player));
    }

    @Override
    void clickNext() {
        if (clkEvent.doubleclick) {
            player.nextSong();
        } else {
            player.fastForward(5);
        }
    }

    @Override
    void clickPrevious() {
        if (clkEvent.doubleclick) {
            player.previousSong();
        } else {
            player.rewind(5);
        }
    }
}

public class StatePattern {
    public static void demo() {
        AudioPlayer player = new AudioPlayer();
        player.startPlayback();
        player.stopPlayback();
        player.nextSong();
        player.startPlayback();
    }
}
