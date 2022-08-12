package views;

import database.Database;
import exception.UsernameNotFoundException;
import exception.WrongPasswordException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Student;

import java.util.ArrayList;
import java.util.List;

public class LoginScreen {
    Button loginBtn;
    Button cancelBtn;
    Stage stage;
    Label errorLabel;
    TextField usernameTextField;
    PasswordField passwordField;
    Database database;
    int wrongPassAttempts;
    List<String> blockedAccounts;

    public LoginScreen(Stage stage) {
        this.stage = stage;
        blockedAccounts = new ArrayList<>();
        database = new Database();
        initErrorLabel();
        initUserNameTextField();
        initPasswordField();
        initLoginBtn();
        initCancelBtn();
        usernameTextFieldFocusChangeListener();
        passwordFieldFieldFocusChangeListener();
        oncloseListener();
        initWrongPassAttempts();
    }

    private void initWrongPassAttempts() {
        wrongPassAttempts = 4;
    }

    public Stage getLoginScreen() {
        Scene scene = new Scene(vBox());
        scene.getStylesheets().add("css/style.css");
        stage.setTitle("Login");
        stage.setWidth(512);
        stage.setHeight(400);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);

        return stage;
    }

    private void initErrorLabel() {
        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label-text-color");
    }

    private void initUserNameTextField() {
        usernameTextField = new TextField();
        usernameTextField.setPromptText("Username");
        usernameTextField.setFocusTraversable(false);
    }

    private void initPasswordField() {
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setFocusTraversable(false);
    }

    private void initLoginBtn() {
        loginBtn = new Button("Login");
        loginBtn.getStyleClass().add("login-btn");
        loginBtnEventHandler();
    }

    private void initCancelBtn() {
        cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("cancel-btn");
        cancelBtnEventHandler();
    }

    private VBox vBox() {
        VBox vBox = new VBox(20);
        vBox.setPadding(new Insets(40));
        vBox.getChildren().addAll(errorLabel, usernameTextField, passwordField, loginBtn, cancelBtn);
        vBox.setAlignment(Pos.CENTER);
        Separator separator = new Separator();
        return new VBox(title(), separator, vBox);
    }

    private HBox title() {
        int spacing = 15;
        HBox hBox = new HBox(spacing);
        hBox.setPadding(new Insets(spacing, spacing, spacing, spacing));
        Label label = new Label("University Project");
        label.getStyleClass().add("heading");
        hBox.getChildren().addAll(logo(), label);
        hBox.getStyleClass().add("sky-blue-background");
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private ImageView logo() {
        int withHeight = 60;
        Image image = new Image("img/university.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(withHeight);
        imageView.setFitWidth(withHeight);
        return imageView;
    }

    private void loginBtnEventHandler() {
        loginBtn.setOnMouseClicked(e -> {
            if (usernameTextField.getStyleClass().contains("green-border") && passwordField.getStyleClass().contains("green-border")) {

                if (wrongPassAttempts == 0) {
                    blockedAccounts.add(usernameTextField.getText());
                    initWrongPassAttempts();
                }

                if (blockedAccounts.contains(usernameTextField.getText())) {
                    errorLabel.setText("Account blocked contact admin");
                    return;
                }

                try {
                    database.isUserRegister(new Student(usernameTextField.getText(), passwordField.getText()));
                    MainWindow mainWindow = new MainWindow(this, usernameTextField.getText());
                    mainWindow.getMainScreen().show();
                    stage.hide();
                } catch (UsernameNotFoundException msg) {
                    errorLabel.setText(msg.toString());
                } catch (WrongPasswordException msg) {
                    if (wrongPassAttempts != 0) {
                        errorLabel.setText(msg + " " + wrongPassAttempts-- + " attempts left");
                    } else {
                        errorLabel.setText("To many attempts account blocked");
                    }
                }
            }
        });
    }

    private void cancelBtnEventHandler() {
        cancelBtn.setOnMouseClicked(e -> stage.close());
    }

    private void usernameTextFieldFocusChangeListener() {
        validateField(usernameTextField);
    }

    private void passwordFieldFieldFocusChangeListener() {
        validateField(passwordField);
    }

    private void validateField(TextField textField) {
        textField.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                if (textField.getPromptText().equals("Username") && !isValidUsername(usernameTextField.getText())) {
                    // set error message
                    errorLabel.setText(textField.getPromptText() + " is too short, " + textField.getPromptText().toLowerCase() + " should be at least  characters long");
                    setBorderErrorLabelVisibility(textField);

                } else if (textField.getPromptText().equals("Password") && !isValidPassword(passwordField.getText())) {
                    // set error message
                    errorLabel.setText(textField.getPromptText() + " field cannot be empty");
                    setBorderErrorLabelVisibility(textField);

                } else {
                    // check if a field contains green border
                    if (!textField.getStyleClass().contains("green-border")) {
                        // set green border
                        textField.getStyleClass().add("green-border");
                        clearErrorLabel();
                    }
                }
            } else {
                clearField(textField);
            }
        });
    }

    // set red border on invalid text field input and show error
    private void setBorderErrorLabelVisibility(TextField textField) {
        // remove green border if present
        textField.getStyleClass().remove("green-border");

        // set red border on a text field
        if (!textField.getStyleClass().contains("red-border")) {
            textField.getStyleClass().add("red-border");
        }
    }

    // clear form on hiding the login window
    private void oncloseListener() {
        stage.setOnHiding(windowEvent -> {
            clearField(usernameTextField);
            clearField(passwordField);
            clearErrorLabel();
        });
    }

    // clear username and password: remove green, red border and clear text
    private void clearField(TextField textField) {
        textField.getStyleClass().remove("red-border");
        textField.getStyleClass().remove("green-border");
        textField.setText("");
    }

    // clear label text and hide error label
    private void clearErrorLabel() {
        errorLabel.setText("");
    }

    // can only contain letters, numbers and length 8-20 characters
    public boolean isValidUsername(String userName) {
        return userName.matches("^[a-zA-Z0-9_.]{8,20}$");
    }

    /// digit + lowercase char + uppercase char + punctuation + symbol
    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
    }
}