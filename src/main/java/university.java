import javafx.application.Application;
import javafx.stage.Stage;
import views.LoginScreen;

public class university extends Application {
    @Override
    public void start(Stage stage) {
        LoginScreen loginScreen = new LoginScreen(stage);
        loginScreen.getLoginScreen().show();
    }
}
