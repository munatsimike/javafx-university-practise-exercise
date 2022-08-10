package views;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class MySearchForm {
    Label label;
    TextField textField;
    GridPane gridPane;

    public MySearchForm() {
        textField = new TextField();
        textField.setPromptText("Enter name");
        label = new Label("Search");
        initGridPane();
        focusChangeListener();
    }

    private void initGridPane() {
        gridPane = new GridPane();
        gridPane.setHgap(8);
        gridPane.addRow(0, label, textField);
        gridPane.setVisible(false);
    }

    public GridPane getSearchFrom() {
        return gridPane;
    }

    public TextField getTextField() {
        return textField;
    }

    public void setFromVisibility(Boolean visibility) {
        gridPane.setVisible(visibility);
    }

    private void focusChangeListener(){
        textField.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(!t1){
                textField.setText("");
            }
        });
    }
}
