package views;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window {
    Stage stage;

    public Window(Parent parent) {
        Scene scene = new Scene(parent);
        scene.getStylesheets().add("css/style.css");
        stage = new Stage();
        stage.setScene(scene);
    }

    public Stage getWindow() {
        return stage;
    }
}