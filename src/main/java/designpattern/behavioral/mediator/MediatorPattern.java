package designpattern.behavioral.mediator;

interface Mediator {
    void notify(Component sender, String event);
}

class AuthenticationDialog implements Mediator {
    private String title;
    private Checkbox loginOrRegisterChkBx;
    private Textbox loginUsername, loginPassword, registrationUsername, registrationPassword, registrationEmail;
    private Button okBtn, cancelBtn;

    public AuthenticationDialog(String title, Checkbox loginOrRegisterChkBx, Textbox loginUsername,
            Textbox loginPassword, Textbox registrationUsername, Textbox registrationPassword,
            Textbox registrationEmail, Button okBtn, Button cancelBtn) {

        loginOrRegisterChkBx.setDialog(this);
        loginUsername.setDialog(this);
        loginPassword.setDialog(this);
        registrationUsername.setDialog(this);
        registrationPassword.setDialog(this);
        registrationEmail.setDialog(this);
        okBtn.setDialog(this);
        cancelBtn.setDialog(this);

        this.title = title;
        this.loginOrRegisterChkBx = loginOrRegisterChkBx;
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
        this.registrationUsername = registrationUsername;
        this.registrationPassword = registrationPassword;
        this.registrationEmail = registrationEmail;
        this.okBtn = okBtn;
        this.cancelBtn = cancelBtn;

    }

    @Override
    public void notify(Component sender, String event) {
        if (sender.equals(loginOrRegisterChkBx) && event == "check") {
            if (loginOrRegisterChkBx.checked()) {
                title = "Log in";
                System.out.println("Show login form\nHide registration form");
            } else {
                title = "Register";
                System.out.println("Show registration form\nHide login form");
            }
        }
        if (sender.equals(okBtn) && event == "click") {
            if (loginOrRegisterChkBx.checked()) {
                System.out.println("Try to find a user using login credentials. and not found, show error message");
            } else {
                System.out.println("Create a user account using data from the registration fields and then login");
            }
        }
    }
}

class Component {
    Mediator dialog;

    public Component() {
    }

    public Component(Mediator dialog) {
        this.dialog = dialog;
    }

    public void setDialog(Mediator dialog) {
        this.dialog = dialog;
    }

    public void click() {
        dialog.notify(this, "click");
    }

    public void keypress() {
        dialog.notify(this, "keypress");
    }
}

class Button extends Component {

}

class Textbox extends Component {

}

class Checkbox extends Component {
    private boolean checked;

    public void check() {
        dialog.notify(this, "check");
    }

    public boolean checked() {
        return this.checked;
    }
}

public class MediatorPattern {
    public static void demo() {
        Checkbox loginOrRegisterChkBx = new Checkbox();
        Textbox loginUsername = new Textbox();
        Textbox loginPassword = new Textbox();
        Textbox registrationUsername = new Textbox();
        Textbox registrationPassword = new Textbox();
        Textbox registrationEmail = new Textbox();
        Button okBtn = new Button();
        Button cancelBtn = new Button();
        AuthenticationDialog dialog = new AuthenticationDialog("Login/Register Dialog", loginOrRegisterChkBx,
                loginUsername, loginPassword, registrationUsername, registrationPassword, registrationEmail, okBtn,
                cancelBtn);
        okBtn.click();
    }
}
