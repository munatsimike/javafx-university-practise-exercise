package views;

import javafx.scene.control.Alert;

public class ConfirmationAlert {
    Alert alert;

    public ConfirmationAlert(String contentTxt) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(contentTxt);
    }

    public Alert getAlert(){
        return alert;
    }
}
