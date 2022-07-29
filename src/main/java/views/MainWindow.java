package views;

import database.Database;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindow {
    LoginScreen screen;
    Stage stage;
    MenuBuilder menuBuilder;
    Database database;
    Button label;

    public MainWindow(LoginScreen screen) {
        label = new Button("Michael");
        this.screen = screen;
        menuBuilder = new MenuBuilder();
        this.database = new Database();
        selectedBtn();
    }

    public Stage getMainScreen() {
        Window window = new Window(new HBox(menuBuilder.getMenu(), new VBox(label)));
        stage = window.getWindow();
        stage.setHeight(800);
        stage.setWidth(1024);
        stage.initStyle(StageStyle.UNDECORATED);
        showLoginScreen();
        return stage;
    }

    private void showLoginScreen() {
        stage.setOnCloseRequest(e -> screen.stage.show());
    }

    private void selectedBtn() {
        menuBuilder.selectedBtn().addListener((observableValue, s, t1) -> label.setText(t1));
    }
}