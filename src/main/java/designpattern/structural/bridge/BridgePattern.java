package designpattern.structural.bridge;


interface Device {
    boolean isEnabled();

    void enable();

    void disable();

    int getVolume();

    void setVolume(int percent);

    int getChannel();

    void setChannel(int channel);
}

class TV implements Device {
    private int volume;
    private int channel;
    private boolean isOn;

    public TV() {
        System.out.println("new TV device has created");
    }

    @Override
    public boolean isEnabled() {
        // System.out.println("TV is Enabled");
        return this.isOn;
    }

    @Override
    public void enable() {
        System.out.println("Tv has enabled");
        this.isOn=true;
    }

    @Override
    public void disable() {
        System.out.println("TV has disabled");
        this.isOn=false;
    }

    @Override
    public int getVolume() {
        System.out.println("TV volume is: " + this.volume);
        return this.volume;
    }

    @Override
    public void setVolume(int percent) {
        System.out.println("TV volume has changed");
        this.volume = percent;
    }

    @Override
    public int getChannel() {
        System.out.println("TV channel is: " + this.channel);
        return this.channel;
    }

    @Override
    public void setChannel(int channel) {
        System.out.println("TV channel has changed");
        this.channel = channel;
    }
}

class Radio implements Device {
    private int volume;
    private int channel;
    private boolean isOn;

    public Radio() {
        System.out.println("new Radio device has created");
    }

    @Override
    public boolean isEnabled() {
        // System.out.println("Radio is Enabled");
        return this.isOn;
    }

    @Override
    public void enable() {
        System.out.println("Radio has enabled");
        this.isOn=true;
    }

    @Override
    public void disable() {
        System.out.println("Radio has disabled");
        this.isOn=false;
    }

    @Override
    public int getVolume() {
        System.out.println("Radio volume is: " + this.volume);
        return this.volume;
    }

    @Override
    public void setVolume(int percent) {
        System.out.println("Radio volume has changed");
        this.volume = percent;
    }

    @Override
    public int getChannel() {
        System.out.println("Radio channel is: " + this.channel);
        return this.channel;
    }

    @Override
    public void setChannel(int channel) {
        System.out.println("Radio channel has changed");
        this.channel = channel;
    }
}

class RemoteControl {
    protected Device device;

    public RemoteControl() {
    }

    public RemoteControl(Device device) {
        this.device = device;
    }

    public void togglePower() {
        if (this.device.isEnabled()) {
            this.device.disable();
        } else {
            this.device.enable();
        }
    }

    public void volumeDown() {
        this.device.setVolume(this.device.getVolume() - 10);
    }

    public void volumeUp() {
        this.device.setVolume(this.device.getVolume() + 10);
    }

    public void channelDown() {
        this.device.setChannel(this.device.getChannel() - 1);
    }

    public void channelUp() {
        this.device.setChannel(this.device.getChannel() + 1);
    }
}

class AdvancedRemoteControl extends RemoteControl {
    public AdvancedRemoteControl(Device device) {
        super(device);
    }

    public void mute() {
        this.device.setVolume(0);
    }
}

public class BridgePattern {
    public static void demo() {
        TV tv = new TV();
        RemoteControl tv_remote = new RemoteControl(tv);
        tv_remote.togglePower();

        Radio radio = new Radio();
        RemoteControl radio_remote = new AdvancedRemoteControl(radio);
        radio_remote.togglePower();
        radio_remote.volumeUp();
        radio_remote.togglePower();
    }
}
