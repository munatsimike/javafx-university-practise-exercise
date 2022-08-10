package views;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MyAlert {
    Alert alert;
    BooleanProperty isShowing;
    ButtonType deleteBtn;

    public MyAlert() {
        isShowing = new SimpleBooleanProperty(false);
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().clear();
        alert.setTitle("");
        alert.setHeaderText(null);
        alertListener();
    }

    public Alert notificationAlert() {
        if (!alert.getTitle().equals("Confirmation")) {
            alert.getButtonTypes().add(ButtonType.OK);
            alert.setTitle("Confirmation");
            OkBtnHandler();
        }
        return alert;
    }

    public Alert confirmationAlert(String contextText) {
        if (!alert.getTitle().equals("Confirm delete")) {
            deleteBtn = new ButtonType("Delete");
            alert.getButtonTypes().addAll(deleteBtn, new ButtonType("Cancel"));
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

    private void OkBtnHandler(){
        Button button =(Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        button.setOnAction(actionEvent -> {
        });
    }
}