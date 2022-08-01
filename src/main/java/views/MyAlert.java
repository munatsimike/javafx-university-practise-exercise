package views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import model.AlertStatus;

public class MyAlert {
    private final Alert alert;
    private final StringProperty status;

    public MyAlert(Alert.AlertType type, String contentTxt) {
        status = new SimpleStringProperty("");
        alert = new Alert(type);
        alert.setContentText(contentTxt);
        alert.setHeaderText(null);
        alert.getButtonTypes().remove(1);
        alertListener();
    }

    public void showAlert() {
        alert.showAndWait();
    }

    public StringProperty alertStatus() {
        return status;
    }

    private void alertListener() {
        alert.setOnHiding(dialogEvent -> status.set(AlertStatus.CLOSED.toString()));
    }
}
