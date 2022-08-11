package views;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class MySearchForm {
    Label label;
    TextField searchTextField;
    GridPane textFieldLabelGridPane;

    public MySearchForm() {
        searchTextField = new TextField();
        searchTextField.setPromptText("Id, Firstname, Lastname");
        label = new Label("Search");
        initGridPane();
        focusChangeListener();
    }

    private void initGridPane() {
        textFieldLabelGridPane = new GridPane();
        textFieldLabelGridPane.setHgap(8);
        textFieldLabelGridPane.addRow(0, label, searchTextField);
    }

    public GridPane getSearchFrom() {
        return textFieldLabelGridPane;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }

    private void focusChangeListener(){
        searchTextField.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(!t1){
                searchTextField.setText("");
            }
        });
    }
}
