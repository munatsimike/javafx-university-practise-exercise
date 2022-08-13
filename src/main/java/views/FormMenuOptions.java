package views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import model.ButtonText;
import model.MenuOption;

public class FormMenuOptions implements EventHandler<ActionEvent> {

    Button addButton;
    Button editButton;
    Button deleteButton;
    StringProperty pressBtnIndicator;
    HBox editDeleteAddBtnHbox;

    public FormMenuOptions() {
        addButton = new Button();
        editButton = new Button();
        deleteButton = new Button();
        pressBtnIndicator = new SimpleStringProperty("");
        setEditDeleteAddBtnhbox();
        setBtnBackgroundColor();
        formOptionsBtnHandler();
    }

    private void setBtnBackgroundColor() {
        editButton.getStyleClass().add("edit-btn");
        addButton.getStyleClass().add("add-btn");
        deleteButton.getStyleClass().add("delete-btn");
    }

    // add delete, edit and add button to hBox
    private void setEditDeleteAddBtnhbox() {
        editDeleteAddBtnHbox = new HBox();
        editDeleteAddBtnHbox.setSpacing(25);
        editDeleteAddBtnHbox.getChildren().addAll(addButton, editButton, deleteButton);
        setVisibility(false);
    }

    private void clearPressedBtnIndicator() {
        pressBtnIndicator.set("");
    }

    // delete add and edit button click handler
    private void formOptionsBtnHandler() {
        editButton.setOnAction(this);
        deleteButton.setOnAction(this);
        addButton.setOnAction(this);
    }

    public HBox getFromOptions() {
        return editDeleteAddBtnHbox;
    }

    // set add , delete and edit button text
    public void setBtnTex(MenuOption selectedBtn) {
        if (selectedBtn.equals(MenuOption.STUDENTS)) {
            addButton.setText(ButtonText.ADD_STUDENTS.toString());
            editButton.setText(ButtonText.EDIT_STUDENTS.toString());
            deleteButton.setText(ButtonText.DELETE_STUDENTS.toString());
        } else if (selectedBtn.equals(MenuOption.TEACHERS)) {
            addButton.setText(ButtonText.ADD_TEACHERS.toString());
            editButton.setText(ButtonText.EDIT_TEACHERS.toString());
            deleteButton.setText(ButtonText.DELETE_TEACHERS.toString());
        } else {
            setVisibility(false);
        }
    }

    // return selected button: delete add and edit
    public StringProperty getPressedBtn() {
        return pressBtnIndicator;
    }

    // show or hide add, edit and delete buttons
    public void setVisibility(Boolean visibility) {
        if (visibility && !editDeleteAddBtnHbox.isVisible()) {
            editDeleteAddBtnHbox.setVisible(true);
            clearPressedBtnIndicator();
        } else if (!visibility && editDeleteAddBtnHbox.isVisible()) {
            editDeleteAddBtnHbox.setVisible(false);
        }
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        if (button.getText().equals(pressBtnIndicator.getValue()))
            clearPressedBtnIndicator();
        pressBtnIndicator.set(button.getText());
    }
}
