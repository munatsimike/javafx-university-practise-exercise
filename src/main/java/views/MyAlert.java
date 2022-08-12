package views;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Objects;

public class MyAlert {
    Alert alert;
    BooleanProperty isShowing;

    public MyAlert() {
        isShowing = new SimpleBooleanProperty(false);
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().remove(1);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alertListener();
    }

    // an alert after a successful operation
    public Alert notificationAlert() {
        if (alert.getButtonTypes().size() == 2) {
            alert.setTitle("Confirmation");
            alert.getButtonTypes().remove(1);
            setButtonText(alert.getButtonTypes().get(0), "OK");
        }

        return alert;
    }

    private void alertListener() {
        alert.setOnHiding(dialogEvent -> {
            isShowing.set(true);
            isShowing.set(false);
        });
    }

    public BooleanProperty isAlertShowing() {
        return isShowing;
    }

    // an alert that requires user to confirm before execution for example delete and logout
    public Alert confirmationAlert(String firstBtnText, String contextText) {
        if (alert.getButtonTypes().size() == 1) {
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(new ButtonType(firstBtnText), new ButtonType("Cancel"));
            alert.setContentText(contextText);
            alert.setTitle("Confirm");
        } else if (alert.getButtonTypes().size() == 2 && !Objects.equals(alert.getContentText(), contextText)) {
            alert.setContentText(contextText);
            setButtonText(alert.getButtonTypes().get(0), firstBtnText);
        }
        return alert;
    }

    public void setButtonText(ButtonType btn, String text) {
        Button button = (Button) alert.getDialogPane().lookupButton(btn);
        button.setText(text);
    }
}
