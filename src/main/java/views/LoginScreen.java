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

public class LoginScreen {
    Button loginBtn;
    Button cancelBtn;
    Stage stage;
    Label errorLabel;
    TextField usernameTextField;
    PasswordField passwordField;
    Database database;
    int wrongPassAttempts;

    public LoginScreen(Stage stage) {
        this.stage = stage;
        wrongPassAttempts = 4;
        database = new Database();
        initErrorLabel();
        initUserNameTextField();
        initPasswordField();
        initLoginBtn();
        initCancelBtn();
        usernameTextFieldFocusChangeListener();
        passwordFieldFieldFocusChangeListener();
        oncloseListener();
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
        validateField(usernameTextField, 3);
    }

    private void passwordFieldFieldFocusChangeListener() {
        validateField(passwordField, 8);
    }

    private void validateField(TextField textField, int textLength) {
        textField.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                if (textField.getText().length() < textLength) {
                    // set error message
                    errorLabel.setText(textField.getPromptText() + " is too short, " + textField.getPromptText().toLowerCase() + " should be at least " + textLength + " characters long");
                    setBorderErrorLabelVisibility(textField);
                } else if (textField.getText().isEmpty()) {
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
}