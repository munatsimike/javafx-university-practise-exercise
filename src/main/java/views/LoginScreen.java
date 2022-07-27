package views;

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

public class LoginScreen {
    Button loginBtn;
    Button cancelBtn;
    Stage stage;
    MainWindow mainWindow;

    public LoginScreen(Stage stage) {
        this.stage = stage;
        mainWindow = new MainWindow(this);
    }

    public Stage getLoginScreen() {
        Scene scene = new Scene(vBox());
        scene.getStylesheets().add("css/style.css");
        stage.setTitle("Login");
        stage.setWidth(512);
        stage.setHeight(350);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        return stage;
    }

    private TextField userNameTextField() {
        TextField usernameTextField = new TextField();
        usernameTextField.setPromptText("Username");
        return usernameTextField;
    }

    private PasswordField setPasswordField() {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        return passwordField;
    }

    private Button loginBtn() {
        loginBtn = new Button("Login");
        loginBtn.getStyleClass().add("btn");
        loginBtnEventHandler();
        return loginBtn;
    }

    private Button cancelBtn() {
        cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #aba7a7");
        cancelBtn.setMaxWidth(130);
        cancelBtnEventHandler();
        return cancelBtn;
    }

    private VBox vBox() {
        VBox vBox = new VBox(20);
        vBox.setPadding(new Insets(40));
        vBox.getChildren().addAll(userNameTextField(), setPasswordField(), loginBtn(), cancelBtn());
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
        hBox.getStyleClass().add("bleu-background");
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
            stage.hide();
            mainWindow.getMainScreen().show();
        });
    }

    private void cancelBtnEventHandler() {
        cancelBtn.setOnMouseClicked(e -> {
            stage.close();
        });
    }
}
