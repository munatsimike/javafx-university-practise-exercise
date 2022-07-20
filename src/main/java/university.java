import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class university extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new BorderPane(new Text("Michael")));
        stage.setScene(scene);
        stage.show();
    }
}
