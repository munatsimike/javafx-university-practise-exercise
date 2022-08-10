package views;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MyAlert {
    Alert alert;
    BooleanProperty isShowing;

    public MyAlert() {
        isShowing = new SimpleBooleanProperty(false);
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().clear();
        alert.setHeaderText(null);
        alertListener();
    }

    public Alert notificationAlert() {
        if (!alert.getTitle().equals("Confirmation"))
            alert.getButtonTypes().remove(1);
        return alert;
    }

    public Alert confirmationAlert(String contextText) {
        if (!alert.getTitle().equals("Confirm delete")) {
            alert.getButtonTypes().addAll(new ButtonType("Delete"), new ButtonType("Cancel"));
            alert.setContentText(contextText);
            alert.setTitle("Confirm delete");
        }

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