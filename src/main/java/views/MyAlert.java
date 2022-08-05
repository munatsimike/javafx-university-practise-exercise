package views;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;

public class MyAlert {
    Alert alert;
    BooleanProperty isShowing;

    public MyAlert() {
        isShowing = new SimpleBooleanProperty(false);
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.getButtonTypes().remove(1);
        alertListener();
    }

    public Alert getAlert() {
       return alert;
    }

    public BooleanProperty isAlertShowing() {
        return isShowing;
    }

    private void alertListener() {
        alert.setOnHiding(dialogEvent -> {
            isShowing.set(true);
            isShowing.set(false);
        });
    }
}