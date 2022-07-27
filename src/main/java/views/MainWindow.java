package views;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindow {
    LoginScreen screen;
    Stage stage;
    MenuBuilder menuBuilder;

    public MainWindow(LoginScreen screen) {
        this.screen = screen;
        menuBuilder = new MenuBuilder();
    }

    public Stage getMainScreen() {
        Window window = new Window(new HBox(menuBuilder.getMenu()));
        stage = window.getWindow();
        stage.setHeight(800);
        stage.setWidth(1024);
        stage.initStyle(StageStyle.UNDECORATED);
        showLoginScreen();
        return stage;
    }

    private void showLoginScreen() {
        stage.setOnCloseRequest(e -> {
            screen.stage.show();
        });
    }
}