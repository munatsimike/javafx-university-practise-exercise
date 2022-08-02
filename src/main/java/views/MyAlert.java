package views;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;

public class MyAlert {
    private final Alert alert;
    private final BooleanProperty status;

    public MyAlert(Alert.AlertType type, String contentTxt) {
        status = new SimpleBooleanProperty(false);
        alert = new Alert(type);
        alert.setContentText(contentTxt);
        alert.setHeaderText(null);
        alert.getButtonTypes().remove(1);
        alertListener();
    }

    public void showAlert() {
        alert.showAndWait();
    }

    public BooleanProperty alertStatus() {
        return status;
    }

    private void alertListener() {
        alert.setOnHiding(dialogEvent -> {
            status.set(true);
            status.set(false);
        });
    }
}